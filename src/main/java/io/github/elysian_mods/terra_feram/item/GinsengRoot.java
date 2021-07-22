package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.AliasedBlockItem;

import io.github.elysian_mods.terra_feram.util.Item;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    name = "ginseng_root";
    item = new GinsengRootItem();
  }

  public static class GinsengRootItem extends AliasedBlockItem {
    public GinsengRootItem() {
      super(RegisteredBlocks.GINSENG_FLOWER, Item.DEFAULT_SETTINGS.food(Item.FOOD_SETTINGS.hunger(2).build()));
    }
  }
}
