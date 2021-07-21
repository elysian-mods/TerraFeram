package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

public abstract class BlockWrapper {
  public String name;
  public Block block;

  public static FabricBlockSettings DEFAULT = FabricBlockSettings.copyOf(RegisteredBlocks.STONE);

  public Block register() {
    return Registry.register(Registry.BLOCK, new TerraFeram.Identifier(name), block);
  }
}
