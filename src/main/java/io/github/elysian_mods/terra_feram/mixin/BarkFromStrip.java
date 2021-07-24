package io.github.elysian_mods.terra_feram.mixin;

import io.github.elysian_mods.terra_feram.util.LogsToBark;
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
public class BarkFromStrip {
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
    ItemStack bark = LogsToBark.get(blockState.getBlock());
    ItemScatterer.spawn(world, blockPos, DefaultedList.ofSize(1, bark));
  }
}
