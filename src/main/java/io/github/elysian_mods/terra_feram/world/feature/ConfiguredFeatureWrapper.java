package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.decorator.WaterDepthThresholdDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Collection;

@SuppressWarnings("deprecation")
public abstract class ConfiguredFeatureWrapper<FC extends FeatureConfig> {
  public String name;
  public Feature<FC> feature;
  public ConfiguredDecorator<?> decorator;

  public FC config;
  public ConfiguredFeature<?, ?> configuration;
  public ConfiguredFeature<?, ?> decorated;

  public Collection<RegistryKey<Biome>> biomes;
  public int chance;
  public int depth;
  public Heightmap.Type heightmap;
  public GenerationStep.Feature step;

  public void configure() {
    configuration = feature.configure(config);
    decorated =
        configuration
            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(heightmap)))
            .decorate(Decorator.WATER_DEPTH_THRESHOLD.configure(new WaterDepthThresholdDecoratorConfig(depth)))
            .spreadHorizontally()
            .applyChance(chance);
    if (decorator != null) decorated = decorated.decorate(decorator);
  }

  public ConfiguredFeature<?, ?> register() {
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new TerraFeram.Identifier(name));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), decorated);
    BiomeModifications.addFeature(BiomeSelectors.includeByKey(biomes), step, key);
    return configuration;
  }
}
