package io.github.elysian_mods.terra_feram.world.feature;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static net.minecraft.block.Block.NOTIFY_ALL;

public class GinsengFlower extends FeatureWrapper<DefaultFeatureConfig> {
  public GinsengFlower() {
    super("ginseng_flower");
    feature = new GinsengFlowerFeature();

    config = new DefaultFeatureConfig();
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
