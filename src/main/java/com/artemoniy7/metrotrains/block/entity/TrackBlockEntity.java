package com.artemoniy7.metrotrains.block.entity;

import com.artemoniy7.metrotrains.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TrackBlockEntity extends BlockEntity {
    public TrackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.METRO_TRACK.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1.0D, 0.0D, 0.0D);
    }
}
