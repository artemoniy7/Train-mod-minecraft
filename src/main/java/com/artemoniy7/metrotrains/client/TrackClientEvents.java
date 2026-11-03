package com.artemoniy7.metrotrains.client;

import com.artemoniy7.metrotrains.MetroTrains;
import com.artemoniy7.metrotrains.client.renderer.TrackBlockEntityRenderer;
import com.artemoniy7.metrotrains.registry.ModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MetroTrains.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TrackClientEvents {
    private TrackClientEvents() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.METRO_TRACK.get(), TrackBlockEntityRenderer::new);
    }
}
