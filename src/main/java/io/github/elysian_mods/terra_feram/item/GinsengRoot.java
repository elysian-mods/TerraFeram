package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.generatedItem;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.DEFAULT_SETTINGS;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.FOOD_SETTINGS;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    super("ginseng_root");
    translation = "Ginseng Root";

    item = new Item();

    itemModel = generatedItem(itemId);
  }

  public static class Item extends net.minecraft.item.AliasedBlockItem {
    public Item() {
      super(
          RegisteredBlocks.GINSENG_FLOWER,
          DEFAULT_SETTINGS.food(FOOD_SETTINGS.hunger(2).build()));
    }
  }
}
