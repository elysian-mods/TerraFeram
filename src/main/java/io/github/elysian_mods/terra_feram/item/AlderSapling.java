package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.BlockItem;

public class AlderSapling extends ItemWrapper {
  public AlderSapling() {
    name = "alder_sapling";
    item = new AlderSaplingItem();
  }

  public static class AlderSaplingItem extends BlockItem {
    public AlderSaplingItem() {
      super(RegisteredBlocks.ALDER_SAPLING, DEFAULT);
    }
  }
}
