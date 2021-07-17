package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GinsengFlower extends BlockWrapper {
  public GinsengFlower() {
    name = "ginseng_flower";
    block = new GinsengFlowerBlock();
  }

  public static class GinsengFlowerBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    private static final VoxelShape[] AGE_TO_SHAPE =
        new VoxelShape[] {
          Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D),
          Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D),
          Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)
        };

    public GinsengFlowerBlock() {
      super(PLANT.ticksRandomly());
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return floor.isOf(Blocks.GRASS_BLOCK);
    }

    public int getMaxAge() {
      return MAX_AGE;
    }

    public VoxelShape getOutlineShape(
        BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public ItemConvertible getSeedsItem() {
      return RegisteredItems.GINSENG_ROOT;
    }

    public boolean isFertilizable(
        BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return false;
    }
  }
}
