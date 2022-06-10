package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public abstract class FeatureWrapper<FC extends FeatureConfig> {
  public String name;
  public Feature<FC> feature;

  protected FC config;
  protected RegistryEntry<ConfiguredFeature<FC, ?>> configured;

  public FeatureWrapper(String name) {
    this.name = name;
  }

  public RegistryEntry<ConfiguredFeature<FC, ?>> register() {
    configured = ConfiguredFeatures.register(TerraFeram.identifier(name).toString(), feature, config);
    return configured;
  }
}
