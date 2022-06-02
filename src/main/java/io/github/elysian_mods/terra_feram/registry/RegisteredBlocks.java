package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.block.GinsengFlower;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class RegisteredBlocks extends Blocks {
  public static final Block ASH_LEAVES =
      Registry.BLOCK.get(TerraFeram.identifier("ash_leaves"));
  public static final Block ASH_LOG = Registry.BLOCK.get(TerraFeram.identifier("ash_log"));
  public static final Block ASH_SAPLING =
      Registry.BLOCK.get(TerraFeram.identifier("ash_sapling"));

  public static final Block ASH_BUTTON =
      Registry.BLOCK.get(TerraFeram.identifier("ash_button"));
  public static final Block ASH_DOOR = Registry.BLOCK.get(TerraFeram.identifier("ash_door"));
  public static final Block ASH_FENCE = Registry.BLOCK.get(TerraFeram.identifier("ash_fence"));
  public static final Block ASH_FENCE_GATE =
      Registry.BLOCK.get(TerraFeram.identifier("ash_fence_gate"));
  public static final Block ASH_PLANKS =
      Registry.BLOCK.get(TerraFeram.identifier("ash_planks"));
  public static final Block ASH_PRESSURE_PLATE =
      Registry.BLOCK.get(TerraFeram.identifier("ash_pressure_plate"));
  public static final Block ASH_SIGN = Registry.BLOCK.get(TerraFeram.identifier("ash_sign"));
  public static final Block ASH_SLAB = Registry.BLOCK.get(TerraFeram.identifier("ash_slab"));
  public static final Block ASH_STAIRS =
      Registry.BLOCK.get(TerraFeram.identifier("ash_stairs"));
  public static final Block STRIPPED_ASH_LOG =
      Registry.BLOCK.get(TerraFeram.identifier("stripped_ash_log"));
  public static final Block STRIPPED_ASH_WOOD =
      Registry.BLOCK.get(TerraFeram.identifier("stripped_ash_wood"));
  public static final Block ASH_TRAPDOOR =
      Registry.BLOCK.get(TerraFeram.identifier("ash_trapdoor"));
  public static final Block ASH_WOOD = Registry.BLOCK.get(TerraFeram.identifier("ash_wood"));

  public static final Block GINSENG_FLOWER = new GinsengFlower().register();

  public static void register() {}
}
