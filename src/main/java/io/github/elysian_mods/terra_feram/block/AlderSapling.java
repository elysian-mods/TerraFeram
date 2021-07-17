package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredFeatures;
import io.github.elysian_mods.terra_feram.worldgen.feature.tree.SaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.SaplingBlock;

public class AlderSapling extends BlockWrapper {
  public AlderSapling() {
    name = "alder_sapling";
    block = new AlderSaplingBlock();
  }

  public static class AlderSaplingBlock extends SaplingBlock {
    public AlderSaplingBlock() {
      super(
          new SaplingGenerator(RegisteredFeatures.ALDER),
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }
}
