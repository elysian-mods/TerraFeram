package io.github.elysian_mods.terra_feram.item;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.generatedItem;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.DEFAULT_SETTINGS;

public class OakBark extends ItemWrapper {

    public OakBark() {
        super("oak_bark");
        translation = "Oak Bark";

        item = new Item();

        itemModel = generatedItem(itemId);
    }

    public static class Item extends net.minecraft.item.Item {
        public Item() {
            super(DEFAULT_SETTINGS);
        }
    }
}
