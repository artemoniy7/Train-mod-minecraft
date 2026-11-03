package com.artemoniy7.metrotrains.registry;

import com.artemoniy7.metrotrains.MetroTrains;
import com.artemoniy7.metrotrains.block.TrackBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MetroTrains.MOD_ID);

    public static final DeferredBlock<TrackBlock> METRO_TRACK = BLOCKS.register(
            "metro_track",
            () -> new TrackBlock(BlockBehaviour.Properties.of().strength(2.0F).noOcclusion())
    );

    private ModBlocks() {}
}
