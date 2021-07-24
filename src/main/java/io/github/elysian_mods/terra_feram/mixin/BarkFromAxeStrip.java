package io.github.elysian_mods.terra_feram.mixin;

import com.google.common.collect.ImmutableMap;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AxeItem.class)
public class BarkFromAxeStrip {
  @Inject(
      method = "useOnBlock",
      at = @At(value = "CONSTANT", ordinal = 0, args = "floatValue=1.0F"),
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void yieldBark(
      ItemUsageContext context,
      CallbackInfoReturnable<ActionResult> cir,
      World world,
      BlockPos blockPos,
      PlayerEntity playerEntity,
      BlockState blockState) {
    ItemStack bark =
        (new ImmutableMap.Builder<Block, ItemStack>())
            .put(RegisteredBlocks.OAK_LOG, new ItemStack(RegisteredItems.OAK_BARK, 4))
            .put(RegisteredBlocks.OAK_WOOD, new ItemStack(RegisteredItems.OAK_BARK, 6))
            .put(RegisteredBlocks.SPRUCE_LOG, new ItemStack(RegisteredItems.SPRUCE_BARK, 4))
            .put(RegisteredBlocks.SPRUCE_WOOD, new ItemStack(RegisteredItems.SPRUCE_BARK, 6))
            .put(RegisteredBlocks.BIRCH_LOG, new ItemStack(RegisteredItems.BIRCH_BARK, 4))
            .put(RegisteredBlocks.BIRCH_WOOD, new ItemStack(RegisteredItems.BIRCH_BARK, 6))
            .put(RegisteredBlocks.JUNGLE_LOG, new ItemStack(RegisteredItems.JUNGLE_BARK, 4))
            .put(RegisteredBlocks.JUNGLE_WOOD, new ItemStack(RegisteredItems.JUNGLE_BARK, 6))
            .put(RegisteredBlocks.ACACIA_LOG, new ItemStack(RegisteredItems.ACACIA_BARK, 4))
            .put(RegisteredBlocks.ACACIA_WOOD, new ItemStack(RegisteredItems.ACACIA_BARK, 6))
            .put(RegisteredBlocks.DARK_OAK_LOG, new ItemStack(RegisteredItems.DARK_OAK_BARK, 4))
            .put(RegisteredBlocks.DARK_OAK_WOOD, new ItemStack(RegisteredItems.DARK_OAK_BARK, 6))
            .put(RegisteredBlocks.ALDER_LOG, new ItemStack(RegisteredItems.ALDER_BARK, 4))
            .put(RegisteredBlocks.ALDER_WOOD, new ItemStack(RegisteredItems.ALDER_BARK, 6))
            .build()
            .get(blockState.getBlock());

    ItemScatterer.spawn(world, blockPos, DefaultedList.ofSize(1, bark));
  }
}
