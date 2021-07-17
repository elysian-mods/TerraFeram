package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public abstract class ItemWrapper {
  public String name;
  public Item item;

  public static FabricItemSettings DEFAULT = new FabricItemSettings().group(TerraFeram.GENERAL);

  public static FoodComponent.Builder FOOD = new FoodComponent.Builder();

  public Item register() {
    return Registry.register(Registry.ITEM, new TerraFeram.Identifier(name), item);
  }
}
