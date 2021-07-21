package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Collection;

@SuppressWarnings("deprecation")
public abstract class ConfiguredFeatureWrapper<FC extends FeatureConfig> {
  public Feature<FC> feature;
  public ConfiguredFeature<?, ?> configuration;
  public String name;

  public Collection<RegistryKey<Biome>> biomes;
  public int chance = 1;
  public FC config;
  public ConfiguredDecorator<?> decorator;
  public GenerationStep.Feature step;

  public void configure() {
    configuration = feature.configure(config).spreadHorizontally().applyChance(chance);
    if (decorator != null) configuration = configuration.decorate(decorator);
  }

  public ConfiguredFeature<?, ?> register() {
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new TerraFeram.Identifier(name));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), configuration);
    BiomeModifications.addFeature(BiomeSelectors.includeByKey(biomes), step, key);
    return configuration;
  }
}
