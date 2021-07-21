package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Arrays;

import static net.minecraft.block.Block.NOTIFY_ALL;

public class GinsengFlower extends ConfiguredFeatureWrapper<DefaultFeatureConfig> {
  public GinsengFlower() {
    name = "ginseng_flower";
    feature =
        Registry.register(
            Registry.FEATURE, new TerraFeram.Identifier(name), new GinsengFlowerFeature());

    config = new DefaultFeatureConfig();

    biomes =
        Arrays.asList(
            BiomeKeys.FOREST,
            BiomeKeys.FLOWER_FOREST,
            BiomeKeys.BIRCH_FOREST,
            BiomeKeys.BIRCH_FOREST_HILLS,
            BiomeKeys.WOODED_HILLS);
    chance = 1;
    depth = 0;
    heightmap = Heightmap.Type.WORLD_SURFACE_WG;
    step = GenerationStep.Feature.VEGETAL_DECORATION;

    configure();
  }

  public static class GinsengFlowerFeature extends Feature<DefaultFeatureConfig> {
    public GinsengFlowerFeature() {
      super(DefaultFeatureConfig.CODEC);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
      context
          .getWorld()
          .setBlockState(
              context.getOrigin(), RegisteredBlocks.GINSENG_FLOWER.getDefaultState(), NOTIFY_ALL);
      return true;
    }
  }
}
