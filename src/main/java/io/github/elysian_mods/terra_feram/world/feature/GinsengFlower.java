package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

import static net.minecraft.block.Block.NOTIFY_ALL;

public class GinsengFlower extends ConfiguredFeatureWrapper<DefaultFeatureConfig> {
  public GinsengFlower() {
    name = "ginseng_flower";
    feature =
        Registry.register(
            Registry.FEATURE, new TerraFeram.Identifier(name), new GinsengFlowerFeature());

    biomes =
        includeBiomes(
            List.of("minecraft:forest", "minecraft:flower_forest", "minecraft:birch_forest"));
    config = new DefaultFeatureConfig();
    chance = 1;
    step = GenerationStep.Feature.VEGETAL_DECORATION;

    configure();
  }

  public static class GinsengFlowerFeature extends Feature<DefaultFeatureConfig> {
    public GinsengFlowerFeature() {
      super(DefaultFeatureConfig.CODEC);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
      BlockPos pos =
          context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, context.getOrigin());
      context
          .getWorld()
          .setBlockState(pos, RegisteredBlocks.GINSENG_FLOWER.getDefaultState(), NOTIFY_ALL);

      return true;
    }
  }
}
