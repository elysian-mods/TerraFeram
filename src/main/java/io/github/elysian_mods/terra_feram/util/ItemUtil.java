package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;

public class ItemUtil {
  public static final FabricItemSettings VANILLA_BLOCKS =
      new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS);
  public static final FabricItemSettings VANILLA_DECOR =
      new FabricItemSettings().group(ItemGroup.DECORATIONS);
  public static final FabricItemSettings VANILLA_REDSTONE =
      new FabricItemSettings().group(ItemGroup.REDSTONE);
  public static final FabricItemSettings VANILLA_TRANSPORTATION =
      new FabricItemSettings().group(ItemGroup.TRANSPORTATION);
  public static final FabricItemSettings VANILLA_MISC =
      new FabricItemSettings().group(ItemGroup.MISC);
  public static final FabricItemSettings VANILLA_FOOD =
      new FabricItemSettings().group(ItemGroup.FOOD);
  public static final FabricItemSettings VANILLA_TOOLS =
      new FabricItemSettings().group(ItemGroup.TOOLS);
  public static final FabricItemSettings VANILLA_WEAPONS =
      new FabricItemSettings().group(ItemGroup.COMBAT);
  public static final FabricItemSettings VANILLA_BREWING =
      new FabricItemSettings().group(ItemGroup.BREWING);

  public static final FabricItemSettings DEFAULT_SETTINGS =
      new FabricItemSettings().group(TerraFeram.GENERAL);
  public static FoodComponent.Builder FOOD_SETTINGS = new FoodComponent.Builder();
}
