package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredFeatures;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AlderSapling extends BlockWrapper {
  public AlderSapling() {
    name = "alder_sapling";
    block = new AlderSaplingBlock();
  }

  public static class AlderSaplingBlock extends SaplingBlock {
    public AlderSaplingBlock() {
      super(
              new AlderSaplingGenerator(),
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }

  public static class AlderSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
      return (ConfiguredFeature<TreeFeatureConfig, ?>) RegisteredFeatures.ALDER;
    }
  }
}
