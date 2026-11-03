package com.artemoniy7.metrotrains.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Small OBJ reader for the metro track asset.
 *
 * The OBJ keeps its authored dimensions and UVs. MTL texture maps are intentionally
 * not loaded because the supplied PNG files are not part of the asset; material
 * groups are rendered with suitable vanilla block textures instead.
 */
final class TrackObjModel {
    private static final ResourceLocation MODEL_LOCATION =
            ResourceLocation.fromNamespaceAndPath("metrotrains", "models/obj/metro_rails.obj");
    private static final ResourceLocation RAIL_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/block/iron_block.png");
    private static final ResourceLocation CONCRETE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/block/light_gray_concrete.png");
    private static final ResourceLocation TIE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/block/stone.png");

    private static TrackObjModel instance;

    private final List<Vector3f> positions = new ArrayList<>();
    private final List<float[]> uvs = new ArrayList<>();
    private final List<Vector3f> normals = new ArrayList<>();
    private final Map<String, List<Triangle>> trianglesByMaterial = new HashMap<>();

    private TrackObjModel() {}

    static void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        TrackObjModel model = get();
        if (model == null) {
            return;
        }

        PoseStack.Pose pose = poseStack.last();
        for (Map.Entry<String, List<Triangle>> entry : model.trianglesByMaterial.entrySet()) {
            VertexConsumer consumer = buffers.getBuffer(renderTypeFor(entry.getKey()));
            for (Triangle triangle : entry.getValue()) {
                model.emitVertex(consumer, pose, triangle.a, packedLight, packedOverlay, triangle.faceNormal);
                model.emitVertex(consumer, pose, triangle.b, packedLight, packedOverlay, triangle.faceNormal);
                model.emitVertex(consumer, pose, triangle.c, packedLight, packedOverlay, triangle.faceNormal);
            }
        }
    }

    private static RenderType renderTypeFor(String material) {
        if ("Prototype_Texture".equals(material)) {
            return RenderType.entityCutoutNoCull(RAIL_TEXTURE);
        }
        if ("tie".equals(material)) {
            return RenderType.entityCutoutNoCull(TIE_TEXTURE);
        }
        return RenderType.entityCutoutNoCull(CONCRETE_TEXTURE);
    }

    private void emitVertex(VertexConsumer consumer, PoseStack.Pose pose, FaceVertex vertex,
                            int packedLight, int packedOverlay, Vector3f faceNormal) {
        Vector3f position = positions.get(vertex.positionIndex);
        Vector3f normal = vertex.normalIndex >= 0 && vertex.normalIndex < normals.size()
                ? normals.get(vertex.normalIndex) : faceNormal;
        float u = vertex.uvIndex >= 0 && vertex.uvIndex < uvs.size() ? uvs.get(vertex.uvIndex)[0] : 0.0F;
        float v = vertex.uvIndex >= 0 && vertex.uvIndex < uvs.size() ? 1.0F - uvs.get(vertex.uvIndex)[1] : 0.0F;

        consumer.addVertex(pose, position.x, position.y, position.z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(pose, normal.x, normal.y, normal.z);
    }

    private static TrackObjModel get() {
        if (instance != null) {
            return instance;
        }
        try {
            TrackObjModel loaded = new TrackObjModel();
            loaded.load();
            instance = loaded;
            return instance;
        } catch (IOException | RuntimeException exception) {
            org.slf4j.LoggerFactory.getLogger("MetroTrains").error("Could not load Metro Trains OBJ model: {}", MODEL_LOCATION, exception);
            return null;
        }
    }

    private void load() throws IOException {
        var resource = Minecraft.getInstance().getResourceManager().getResource(MODEL_LOCATION)
                .orElseThrow(() -> new IOException("Missing resource " + MODEL_LOCATION));

        String currentMaterial = "darkconcrete";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                switch (parts[0]) {
                    case "v" -> positions.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    case "vt" -> uvs.add(new float[]{
                            Float.parseFloat(parts[1]), Float.parseFloat(parts[2])});
                    case "vn" -> normals.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    case "usemtl" -> currentMaterial = parts[1];
                    case "f" -> addFace(parts, currentMaterial);
                    default -> {
                        // Object names, mtllib and smoothing declarations need no runtime action.
                    }
                }
            }
        }

        if (positions.isEmpty() || trianglesByMaterial.isEmpty()) {
            throw new IOException("OBJ contains no renderable geometry");
        }
    }

    private void addFace(String[] parts, String material) throws IOException {
        if (parts.length < 4) {
            return;
        }

        List<FaceVertex> face = new ArrayList<>(parts.length - 1);
        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/", -1);
            int positionIndex = resolveIndex(indices[0], positions.size());
            int uvIndex = indices.length > 1 && !indices[1].isEmpty()
                    ? resolveIndex(indices[1], uvs.size()) : -1;
            int normalIndex = indices.length > 2 && !indices[2].isEmpty()
                    ? resolveIndex(indices[2], normals.size()) : -1;
            face.add(new FaceVertex(positionIndex, uvIndex, normalIndex));
        }

        Vector3f faceNormal = calculateFaceNormal(face.get(0), face.get(1), face.get(2));
        List<Triangle> triangles = trianglesByMaterial.computeIfAbsent(material, ignored -> new ArrayList<>());
        for (int i = 1; i < face.size() - 1; i++) {
            triangles.add(new Triangle(face.get(0), face.get(i), face.get(i + 1), faceNormal));
        }
    }

    private int resolveIndex(String value, int size) throws IOException {
        int index = Integer.parseInt(value);
        int resolved = index > 0 ? index - 1 : size + index;
        if (resolved < 0 || resolved >= size) {
            throw new IOException("OBJ index out of range: " + value);
        }
        return resolved;
    }

    private Vector3f calculateFaceNormal(FaceVertex a, FaceVertex b, FaceVertex c) {
        Vector3f p0 = positions.get(a.positionIndex);
        Vector3f p1 = positions.get(b.positionIndex);
        Vector3f p2 = positions.get(c.positionIndex);
        Vector3f edge1 = new Vector3f(p1).sub(p0);
        Vector3f edge2 = new Vector3f(p2).sub(p0);
        return edge1.cross(edge2).normalize();
    }

    private record FaceVertex(int positionIndex, int uvIndex, int normalIndex) {}
    private record Triangle(FaceVertex a, FaceVertex b, FaceVertex c, Vector3f faceNormal) {}
}
