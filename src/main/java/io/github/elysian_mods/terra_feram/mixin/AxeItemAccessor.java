package io.github.elysian_mods.terra_feram.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {
  @Mutable
  @Accessor("STRIPPED_BLOCKS")
  static Map<Block, Block> getStrippedBlocks() {
    throw new AssertionError();
  }

  @Mutable
  @Accessor("STRIPPED_BLOCKS")
  static void setStrippedBlocks(Map<Block, Block> strippedBlocks) {
    throw new AssertionError();
  }
}
