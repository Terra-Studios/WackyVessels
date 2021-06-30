package dev.sebastianb.wackyvessels.block.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Wheel extends FacingBlock {

    public Wheel(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private VoxelShape getShape(BlockState state) {
        if (state.get(FACING) == Direction.UP)
            return VoxelShapes.cuboid(4.0 / 16, 0.0 / 16, 4.0 / 16, 12.0 / 16, 4.0 / 16, 12.0 / 16);
        else if (state.get(FACING) == Direction.DOWN)
            return VoxelShapes.cuboid(4.0 / 16, 12.0 / 16, 4.0 / 16, 12.0 / 16, 16.0 / 16, 12.0 / 16);
        else if (state.get(FACING) == Direction.NORTH)
            return VoxelShapes.cuboid(4.0 / 16, 4.0 / 16, 12.0 / 16, 12.0 / 16, 12.0 / 16, 16.0 / 16);
        else if (state.get(FACING) == Direction.SOUTH)
            return VoxelShapes.cuboid(4.0 / 16, 4.0 / 16, 0.0 / 16, 12.0 / 16, 12.0 / 16, 4.0 / 16);
        else if (state.get(FACING) == Direction.EAST)
            return VoxelShapes.cuboid(0.0 / 16, 4.0 / 16, 4.0 / 16, 4.0 / 16, 12.0 / 16, 12.0 / 16);
        else if (state.get(FACING) == Direction.WEST)
            return VoxelShapes.cuboid(12.0 / 16, 4.0 / 16, 4.0 / 16, 16.0 / 16, 12.0 / 16, 12.0 / 16);
        else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
