package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.BlockItem;

public class AlderLeaves extends ItemWrapper {
  public AlderLeaves() {
    name = "alder_leaves";
    item = new AlderLeavesItem();
  }

  public static class AlderLeavesItem extends BlockItem {
    public AlderLeavesItem() {
      super(RegisteredBlocks.ALDER_LEAVES, DEFAULT);
    }
  }
}
