package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.AliasedBlockItem;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    name = "ginseng_root";
    item = new GinsengRootItem();
  }

  public static class GinsengRootItem extends AliasedBlockItem {
    public GinsengRootItem() {
      super(RegisteredBlocks.GINSENG_FLOWER, DEFAULT.food(FOOD.hunger(2).build()));
    }
  }
}
