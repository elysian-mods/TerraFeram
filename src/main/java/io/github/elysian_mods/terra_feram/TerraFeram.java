package io.github.elysian_mods.terra_feram;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredFeatures;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import io.github.elysian_mods.terra_feram.registry.RegisteredStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraFeram implements ModInitializer {
  public static final String MOD_ID = "terra_feram";

  public static class Identifier extends net.minecraft.util.Identifier {
    public Identifier(String name) {
      super(MOD_ID, name);
    }
  }

  public static final ItemGroup GENERAL =
      FabricItemGroupBuilder.build(
          new Identifier("general"), () -> new ItemStack(RegisteredBlocks.AZALEA));

  @Override
  public void onInitialize() {
    RegisteredBlocks.register();
    RegisteredItems.register();

    RegisteredFeatures.register();
    RegisteredStructures.register();
  }
}
