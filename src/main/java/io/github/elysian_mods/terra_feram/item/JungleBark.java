package io.github.elysian_mods.terra_feram.item;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.generatedItem;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.DEFAULT_SETTINGS;

public class JungleBark extends ItemWrapper {

    public JungleBark() {
        super("jungle_bark");
        translation = "Jungle Bark";

        item = new Item();

        itemModel = generatedItem(itemId);
    }

    public static class Item extends net.minecraft.item.Item {
        public Item() {
            super(DEFAULT_SETTINGS);
        }
    }
}
