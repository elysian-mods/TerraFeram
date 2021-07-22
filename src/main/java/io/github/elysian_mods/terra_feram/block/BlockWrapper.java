package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public abstract class BlockWrapper {
  public String name;
  public Block block;

  public Block register() {
    return Registry.register(Registry.BLOCK, TerraFeram.identifier(name), block);
  }
}
