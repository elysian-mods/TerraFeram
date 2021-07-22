package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.world.feature.ConfiguredFeatureWrapper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Arrays;

public class AlderTree extends ConfiguredFeatureWrapper<TreeFeatureConfig> {
  public AlderTree() {
    name = "alder";
    feature = Feature.TREE;

    config =
        new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(RegisteredBlocks.ALDER_LOG.getDefaultState()),
                new StraightTrunkPlacer(3, 2, 0),
                new SimpleBlockStateProvider(RegisteredBlocks.ALDER_LEAVES.getDefaultState()),
                new SimpleBlockStateProvider(RegisteredBlocks.ALDER_SAPLING.getDefaultState()),
                new BlobFoliagePlacer(
                    ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
            .build();

    biomes = Arrays.asList(
            BiomeKeys.FOREST,
            BiomeKeys.FLOWER_FOREST,
            BiomeKeys.BIRCH_FOREST,
            BiomeKeys.BIRCH_FOREST_HILLS,
            BiomeKeys.WOODED_HILLS);
    chance = 1;
    depth = 1;
    heightmap = Heightmap.Type.WORLD_SURFACE_WG;
    step = GenerationStep.Feature.VEGETAL_DECORATION;

    configure();
  }
}
