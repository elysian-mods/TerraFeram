package io.github.elysian_mods.terra_feram;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredFeatures;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import io.github.elysian_mods.terra_feram.registry.RegisteredStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TerraFeram implements ModInitializer {
  public static final String MOD_ID = "terra_feram";

  public static final ItemGroup GENERAL =
      FabricItemGroupBuilder.build(
          identifier("general"), () -> new ItemStack(RegisteredBlocks.AZALEA));

  public static Identifier identifier(String name) {
    return new Identifier(MOD_ID, name);
  }

  @Override
  public void onInitialize() {
    RegisteredFeatures.register();
    RegisteredStructures.register();

    RegisteredBlocks.register();
    RegisteredItems.register();
  }
}
