package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.AliasedBlockItem;

import static io.github.elysian_mods.terra_feram.util.ItemUtil.*;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    name = "ginseng_root";
    item = new GinsengRootItem();
  }

  public static class GinsengRootItem extends AliasedBlockItem {
    public GinsengRootItem() {
      super(
          RegisteredBlocks.GINSENG_FLOWER,
          DEFAULT_SETTINGS.food(FOOD_SETTINGS.hunger(2).build()));
    }
  }
}
