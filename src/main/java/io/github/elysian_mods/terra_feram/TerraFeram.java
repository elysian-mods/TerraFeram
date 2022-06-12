package io.github.elysian_mods.terra_feram;

import io.github.elysian_mods.terra_feram.registry.*;
import io.github.elysian_mods.terra_feram.util.ARRPUtil;
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
          id("general"), () -> new ItemStack(RegisteredBlocks.AZALEA));

  public static Identifier id(String name) {
    return new Identifier(MOD_ID, name);
  }
  public static Identifier common_id(String name) { return new Identifier("c", name);}

  @Override
  public void onInitialize() {
    RegisteredFeatures.register();

    RegisteredBlocks.register();
    RegisteredItems.register();

    RegisteredEntities.register();

    RegisteredTags.register();

    RESOURCE_PACK.addLang(new Identifier("en_us"), ARRPUtil.lang);
    RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
  }
}
