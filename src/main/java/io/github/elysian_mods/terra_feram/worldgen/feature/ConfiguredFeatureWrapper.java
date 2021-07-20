package io.github.elysian_mods.terra_feram.worldgen.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ConfiguredFeatureWrapper<FC extends FeatureConfig> {
  public Feature<FC> feature;
  public ConfiguredFeature<?, ?> configuration;
  public String name;

  public Predicate<BiomeSelectionContext> biomes;
  public int chance = 1;
  public FC config;
  public ConfiguredDecorator<?> decorator;
  public GenerationStep.Feature step;

  public void configure() {
    configuration = feature.configure(config).spreadHorizontally().applyChance(chance);
    if (decorator != null) configuration = configuration.decorate(decorator);
  }

  public Predicate<BiomeSelectionContext> includeBiomes(List<String> biomes) {
    ArrayList<RegistryKey<Biome>> keys = new ArrayList<>();
    for (String biome : biomes) {
      keys.add(RegistryKey.of(Registry.BIOME_KEY, new Identifier(biome)));
    }
    return BiomeSelectors.includeByKey(keys);
  }

  public ConfiguredFeature<?, ?> register() {
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new TerraFeram.Identifier(name));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), configuration);
    BiomeModifications.addFeature(biomes, step, key);
    return configuration;
  }
}
