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
  public String name;

  public Predicate<BiomeSelectionContext> biomes;
  public FC config;
  public ConfiguredDecorator<?> decorator;
  public GenerationStep.Feature step;

  public Predicate<BiomeSelectionContext> includeBiomes(List<String> biomes) {
    ArrayList<RegistryKey<Biome>> keys = new ArrayList<>();
    for (String biome : biomes) {
      String[] path = biome.split(":");
      keys.add(RegistryKey.of(Registry.BIOME_KEY, new Identifier(path[0], path[1])));
    }
    return BiomeSelectors.includeByKey(keys);
  }

  public ConfiguredFeature<?, ?> register() {
    ConfiguredFeature<?, ?> configuration = feature.configure(config).decorate(decorator);
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new TerraFeram.Identifier(name));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), configuration);
    BiomeModifications.addFeature(biomes, step, key);
    return configuration;
  }
}
