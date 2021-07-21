package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.world.feature.ConfiguredFeatureWrapper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Arrays;

public class Alder extends ConfiguredFeatureWrapper<TreeFeatureConfig> {
  public Alder() {
    name = "alder";
    feature = Feature.TREE;

    config =
        new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(RegisteredBlocks.NETHERITE_BLOCK.getDefaultState()),
                new StraightTrunkPlacer(8, 3, 0),
                new SimpleBlockStateProvider(RegisteredBlocks.DIAMOND_BLOCK.getDefaultState()),
                new SimpleBlockStateProvider(RegisteredBlocks.ALDER_SAPLING.getDefaultState()),
                new BlobFoliagePlacer(
                    ConstantIntProvider.create(5), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
            .build();
    decorator =
        Decorator.HEIGHTMAP.configure(
            new HeightmapDecoratorConfig(Heightmap.Type.WORLD_SURFACE_WG));

    biomes = Arrays.asList(BiomeKeys.PLAINS, BiomeKeys.BEACH);
    chance = 1;
    feature = Feature.TREE;
    step = GenerationStep.Feature.VEGETAL_DECORATION;

    configure();
  }
}
