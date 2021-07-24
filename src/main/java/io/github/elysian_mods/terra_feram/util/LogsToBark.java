package io.github.elysian_mods.terra_feram.util;

import com.mojang.datafixers.util.Pair;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class LogsToBark {
  public static HashMap<Block, Pair<Item, Integer>> MAP =
      new HashMap<>() {
        {
          put(RegisteredBlocks.OAK_LOG, new Pair<>(RegisteredItems.OAK_BARK, 4));
          put(RegisteredBlocks.OAK_WOOD, new Pair<>(RegisteredItems.OAK_BARK, 6));
          put(RegisteredBlocks.SPRUCE_LOG, new Pair<>(RegisteredItems.SPRUCE_BARK, 4));
          put(RegisteredBlocks.SPRUCE_WOOD, new Pair<>(RegisteredItems.SPRUCE_BARK, 6));
          put(RegisteredBlocks.BIRCH_LOG, new Pair<>(RegisteredItems.BIRCH_BARK, 4));
          put(RegisteredBlocks.BIRCH_WOOD, new Pair<>(RegisteredItems.BIRCH_BARK, 6));
          put(RegisteredBlocks.JUNGLE_LOG, new Pair<>(RegisteredItems.JUNGLE_BARK, 4));
          put(RegisteredBlocks.JUNGLE_WOOD, new Pair<>(RegisteredItems.JUNGLE_BARK, 6));
          put(RegisteredBlocks.ACACIA_LOG, new Pair<>(RegisteredItems.ACACIA_BARK, 4));
          put(RegisteredBlocks.ACACIA_WOOD, new Pair<>(RegisteredItems.ACACIA_BARK, 6));
          put(RegisteredBlocks.DARK_OAK_LOG, new Pair<>(RegisteredItems.DARK_OAK_BARK, 4));
          put(RegisteredBlocks.DARK_OAK_WOOD, new Pair<>(RegisteredItems.DARK_OAK_BARK, 6));
        }
      };

  public static ItemStack get(Block block) {
    Pair<Item, Integer> pair = MAP.get(block);
    return new ItemStack(pair.getFirst(), pair.getSecond());
  }

  public static Pair<Item, Integer> put(Block block, Item bark, int count) {
    return MAP.put(block, new Pair<>(bark, count));
  }
}
