package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.block.GinsengFlower;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class RegisteredBlocks extends Blocks {
  public static final Block ALDER_LEAVES =
      Registry.BLOCK.get(TerraFeram.identifier("alder_leaves"));
  public static final Block ALDER_LOG = Registry.BLOCK.get(TerraFeram.identifier("alder_log"));
  public static final Block ALDER_SAPLING =
      Registry.BLOCK.get(TerraFeram.identifier("alder_sapling"));

  public static final Block GINSENG_FLOWER = new GinsengFlower().register();

  public static void register() {}
}
