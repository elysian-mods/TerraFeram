package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;

public class Item {
  public static final FabricItemSettings DEFAULT_SETTINGS =
      new FabricItemSettings().group(TerraFeram.GENERAL);
  public static FoodComponent.Builder FOOD_SETTINGS = new FoodComponent.Builder();
}
