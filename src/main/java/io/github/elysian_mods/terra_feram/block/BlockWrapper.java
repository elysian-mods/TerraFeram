package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public abstract class BlockWrapper {
  public String name;
  public Block block;

  public static FabricBlockSettings DEFAULT = FabricBlockSettings.of(Material.AIR);
  public static FabricBlockSettings PLANT =
      FabricBlockSettings.of(Material.PLANT)
          .noCollision()
          .nonOpaque()
          .breakInstantly()
          .sounds(BlockSoundGroup.GRASS);

  public Block register() {
    return Registry.register(Registry.BLOCK, new TerraFeram.Identifier(name), block);
  }
}
