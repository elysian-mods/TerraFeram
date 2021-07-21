package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.block.AlderLeaves;
import io.github.elysian_mods.terra_feram.block.AlderLog;
import io.github.elysian_mods.terra_feram.block.AlderSapling;
import io.github.elysian_mods.terra_feram.block.GinsengFlower;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class RegisteredBlocks extends Blocks {
  public static final Block ALDER_LEAVES = new AlderLeaves().register();
  public static final Block ALDER_LOG = new AlderLog().register();
  public static final Block ALDER_SAPLING = new AlderSapling().register();
  public static final Block GINSENG_FLOWER = new GinsengFlower().register();

  public static void register() {}
}
