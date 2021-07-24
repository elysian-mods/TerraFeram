package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.util.ItemUtil;
import net.minecraft.item.AliasedBlockItem;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    name = "ginseng_root";
    item = new GinsengRootItem();
  }

  public static class GinsengRootItem extends AliasedBlockItem {
    public GinsengRootItem() {
      super(
          RegisteredBlocks.GINSENG_FLOWER,
          ItemUtil.DEFAULT_SETTINGS.food(ItemUtil.FOOD_SETTINGS.hunger(2).build()));
    }
  }
}
