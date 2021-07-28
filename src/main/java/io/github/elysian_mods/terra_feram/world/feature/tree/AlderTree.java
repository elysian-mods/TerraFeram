package io.github.elysian_mods.terra_feram.world.feature.tree;

import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Arrays;

public class AlderTree extends TreeType {
  public AlderTree() {
    super("alder");

    biomes =
        Arrays.asList(
            BiomeKeys.FOREST,
            BiomeKeys.FLOWER_FOREST,
            BiomeKeys.BIRCH_FOREST,
            BiomeKeys.BIRCH_FOREST_HILLS,
            BiomeKeys.WOODED_HILLS);
    maxWaterDepth = 1;

    trunkPlacer = new StraightTrunkPlacer(4, 1, 1);
    foliagePlacer =
        new SpruceFoliagePlacer(
            UniformIntProvider.create(2, 2),
            UniformIntProvider.create(1, 1),
            UniformIntProvider.create(1, 2));
    size = new TwoLayersFeatureSize(2, 0, 2);

    create();
  }
}
