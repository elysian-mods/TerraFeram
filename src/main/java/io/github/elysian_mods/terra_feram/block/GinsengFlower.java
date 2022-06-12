package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.client.RenderType;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import net.devtech.arrp.json.blockstate.JState;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;
import static net.devtech.arrp.json.blockstate.JState.*;

public class GinsengFlower extends BlockWrapper {
  public GinsengFlower() {
    super("ginseng_flower");
    translation = "Ginseng Flower";

    block = new Block();
    renderType = RenderType.TRANSPARENT;

    blockModels = blockModels(Map.of(blockId, "cross"), Map.of("cross", blockId));
    itemModel = generatedItem(blockId);

    blockState =
        state(
            variant()
                .put("age=0", JState.model(blockId))
                .put("age=1", JState.model(blockId))
                .put("age=2", JState.model(blockId)));
  }

  public static class Block extends net.minecraft.block.CropBlock {
    public static final int MAX_AGE = 2;
    private static final VoxelShape[] AGE_TO_SHAPE =
        new VoxelShape[] {
          Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D),
          Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D),
          Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)
        };

    public Block() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OXEYE_DAISY).ticksRandomly());
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
