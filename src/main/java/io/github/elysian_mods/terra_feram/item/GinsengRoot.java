package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.item.AliasedBlockItem;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.*;

public class GinsengRoot extends ItemWrapper {

  public GinsengRoot() {
    super("ginseng_root");
    item = new IGinsengRoot();

    itemModel = generatedItem(itemId);
  }

  public static class IGinsengRoot extends AliasedBlockItem {
    public IGinsengRoot() {
      super(
          RegisteredBlocks.GINSENG_FLOWER,
          DEFAULT_SETTINGS.food(FOOD_SETTINGS.hunger(2).build()));
    }
  }
}
