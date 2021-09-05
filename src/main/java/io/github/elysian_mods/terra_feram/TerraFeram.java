package io.github.elysian_mods.terra_feram;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredFeatures;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import io.github.elysian_mods.terra_feram.registry.RegisteredStructures;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TerraFeram implements ModInitializer {
  public static final String MOD_ID = "terra_feram";
  public static final RuntimeResourcePack RESOURCE_PACK =
      RuntimeResourcePack.create(MOD_ID + ":default");

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

    RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
  }
}
