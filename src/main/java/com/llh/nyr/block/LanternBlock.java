package com.llh.nyr.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LanternBlock extends DirectionalBlock {
    protected static final VoxelShape WIDE_SHAPE = Block.box(2.0D,2.0D,2.0D,14.0D,8.0D,14.0D);
    protected static final VoxelShape HEIGHT_SHAPE = Block.box(4.0D,3.0D,4.0D,12.0D,13.0D,12.0D);

    protected final boolean isWide;

    public LanternBlock(Properties properties, boolean isWide) {
        super(properties);
        this.isWide = isWide;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){
        return isWide ? WIDE_SHAPE : HEIGHT_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){
        return getShape(state, level, pos, context);
    }
}
