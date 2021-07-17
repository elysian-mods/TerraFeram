package io.github.elysian_mods.terra_feram.worldgen.feature.tree;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SaplingGenerator extends net.minecraft.block.sapling.SaplingGenerator {
  private final ConfiguredFeature<TreeFeatureConfig, ?> configuration;

  public SaplingGenerator(ConfiguredFeature<?, ?> configuration) {
    this.configuration = (ConfiguredFeature<TreeFeatureConfig, ?>) configuration;
  }

  @Nullable
  @Override
  protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
    return configuration;
  }
}
