package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.world.feature.GinsengFlower;
import io.github.elysian_mods.terra_feram.world.feature.tree.AshTree;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

public class RegisteredFeatures extends ConfiguredFeatures {
  public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ASH_TREE =
          new AshTree().register();

  public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> GINSENG_FLOWER =
          new GinsengFlower().register();

  public static void register() {}
}
