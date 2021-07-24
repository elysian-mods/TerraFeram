package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class Block {
  public static final FabricBlockSettings DEFAULT_SETTINGS =
      FabricBlockSettings.copyOf(RegisteredBlocks.STONE);
}
