package com.llh.nyr.item;

import com.llh.nyr.NYR;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NYR.MOD_ID);

    public static final RegistryObject<Item> RED_PAPER =
            ITEMS.register("red_paper", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YELLOW_PAPER =
            ITEMS.register("yellow_paper", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
