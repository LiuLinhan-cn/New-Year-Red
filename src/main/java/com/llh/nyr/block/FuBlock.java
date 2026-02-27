package com.llh.nyr.block;

import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FuBlock extends DirectionalBlock {
    public static final IntegerProperty LEFT = IntegerProperty.create("left", 0, 2);

    protected static final VoxelShape SHAPE_SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_WEST = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final Log log = LogFactory.getLog(FuBlock.class);

    public FuBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LEFT, 0));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LEFT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);

        if(state.getValue(LEFT) == 1){
            return switch (facing) {
                case WEST -> Block.box(16.0D, 0.0D, 3.0D, 32.0D, 16.0D, 4.0D);      // 正确 (16<32, 4<5)
                case EAST -> Block.box(-16.0D, 0.0D, 12.0D, 0.0D, 16.0D, 13.0D);    // 正确 (-16<0, 12<13)
                case NORTH -> Block.box(12.0D, 0.0D, 16.0D, 13.0D, 16.0D, 32.0D);   // 修正: 交换参数顺序
                default -> Block.box(3.0D, 0.0D, -16.0D, 4.0D, 16.0D, 0.0D);    // 修正: 重新排序
            };
        }
        if(state.getValue(LEFT) == 2){
            return switch (facing) {
                case WEST -> Block.box(16.0D, 0.0D, 12.0D, 32.0D, 16.0D, 13.0D);    // 正确 (16<32, 13<14)
                case EAST -> Block.box(-16.0D, 0.0D, 3.0D, 0.0D, 16.0D, 4.0D);      // 正确 (-16<0, 4<5)
                case NORTH -> Block.box(3.0D, 0.0D, 16.0D, 4.0D, 16.0D, 32.0D);     // 修正: 交换参数顺序
                default -> Block.box(12.0D, 0.0D, -16.0D, 13.0D, 16.0D, 0.0D);    // 修正: 重新排序
            };
        }

        return switch (facing) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @SubscribeEvent
    public void doorOpen(PlayerInteractEvent.RightClickBlock event){
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if(level.isClientSide()){
            return ;
        }

        if(state.getBlock() instanceof DoorBlock doorBlock){
            boolean isLeft = state.getValue(doorBlock.HINGE) == DoorHingeSide.LEFT;
            boolean isOpen = state.getValue(doorBlock.OPEN);

            if(state.getValue(doorBlock.HALF) == DoubleBlockHalf.LOWER){
                pos = pos.relative(Direction.UP);
            }

            pos = pos.relative(state.getValue(doorBlock.FACING), -1);

            log.info(isOpen);
            log.info(isLeft);

            state = level.getBlockState(pos);

            if(state.getBlock() instanceof FuBlock fuBlock){
                level.setBlock(pos, state.setValue(LEFT, !(isOpen) ? (isLeft ? 1 : 2) : 0).setValue(FACING, state.getValue(fuBlock.FACING)), 3);
            }
        }
    }
}
