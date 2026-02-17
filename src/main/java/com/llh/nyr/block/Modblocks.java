package com.llh.nyr.block;

import com.llh.nyr.NYR;
import com.llh.nyr.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Modblocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NYR.MOD_ID);

    public static final RegistryObject<Block> HEIGHT_LANTERN =
            registerBlock("height_lantern", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(0.5F,0.5F)
                    .lightLevel((state) -> 15)
                    .noOcclusion()
                    .dynamicShape()
                    .isValidSpawn((state, level, pos, entityType) -> false)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
            ));

    public static final RegistryObject<Block> WIDE_LANTERN =
            registerBlock("wide_lantern", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(0.5F,0.5F)
                    .lightLevel((state) -> 15)
                    .noOcclusion()
                    .dynamicShape()
                    .isValidSpawn((state, level, pos, entityType) -> false)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
            ));

    public static final RegistryObject<Block> POSITIVE_FU =
        registerBlock("positive_fu", () -> new DirectionalBlock(BlockBehaviour.Properties.of()
                .strength(0.5F,0.5F)
                .noOcclusion()
                .noCollission()
                .dynamicShape()
                .isValidSpawn((state, level, pos, entityType) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
        ));

    public static final RegistryObject<Block> OPPOSITE_FU =
            registerBlock("opposite_fu", () -> new DirectionalBlock(BlockBehaviour.Properties.of()
                    .strength(0.5F,0.5F)
                    .noOcclusion()
                    .noCollission()
                    .dynamicShape()
                    .isValidSpawn((state, level, pos, entityType) -> false)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
            ));

    private static <T extends Block> void registerBlockItems(String name, RegistryObject<T> block){
        Moditems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
