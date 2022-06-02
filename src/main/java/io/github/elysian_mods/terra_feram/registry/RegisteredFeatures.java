package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.world.feature.GinsengFlower;
import io.github.elysian_mods.terra_feram.world.feature.tree.AshTree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class RegisteredFeatures extends ConfiguredFeatures {
  public static final ConfiguredFeature<?, ?> ASH_TREE = new AshTree().register();

  public static final ConfiguredFeature<?, ?> GINSENG_FLOWER = new GinsengFlower().register();

  public static void register() {}
}
