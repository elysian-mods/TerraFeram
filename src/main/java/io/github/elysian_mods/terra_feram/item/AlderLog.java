package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.BlockItem;

public class AlderLog extends ItemWrapper {
  public AlderLog() {
    name = "alder_log";
    item = new AlderLogItem();
  }

  public static class AlderLogItem extends BlockItem {
    public AlderLogItem() {
      super(RegisteredBlocks.ALDER_LOG, DEFAULT);
    }
  }
}
