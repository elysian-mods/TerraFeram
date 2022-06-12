package io.github.elysian_mods.terra_feram.world.feature.tree;

import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class AshTree extends TreeType {
  public AshTree() {
    super("ash");
    translation = "Ash";

    trunkPlacer = new StraightTrunkPlacer(4, 1, 1);
    foliagePlacer =
        new SpruceFoliagePlacer(
            UniformIntProvider.create(2, 2),
            UniformIntProvider.create(1, 1),
            UniformIntProvider.create(1, 2));
    size = new TwoLayersFeatureSize(2, 0, 2);

    config = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(log.block.getDefaultState()),
            trunkPlacer,
            BlockStateProvider.of(leaves.block.getDefaultState()),
            foliagePlacer,
            size)
            .ignoreVines()
            .forceDirt()
            .build();
  }
}
