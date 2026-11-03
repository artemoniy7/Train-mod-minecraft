package com.artemoniy7.metrotrains;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Main entry point for the Metro Trains mod. */
@Mod(MetroTrains.MOD_ID)
public final class MetroTrains {
    public static final String MOD_ID = "metrotrains";

    private static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MOD_ID);

    private static final DeferredHolder<Item, Item> METRO_TICKET =
            ITEMS.register("metro_ticket", () -> new Item(new Item.Properties()));

    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> METRO_TRAINS_TAB =
            CREATIVE_MODE_TABS.register("metro_trains", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.metrotrains"))
                    .icon(() -> new ItemStack(METRO_TICKET.get()))
                    .displayItems((parameters, output) -> output.accept(METRO_TICKET.get()))
                    .build());

    public MetroTrains() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
