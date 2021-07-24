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

  public static final Block ALDER_BUTTON =
      Registry.BLOCK.get(TerraFeram.identifier("alder_button"));
  public static final Block ALDER_DOOR = Registry.BLOCK.get(TerraFeram.identifier("alder_door"));
  public static final Block ALDER_FENCE = Registry.BLOCK.get(TerraFeram.identifier("alder_fence"));
  public static final Block ALDER_FENCE_GATE =
      Registry.BLOCK.get(TerraFeram.identifier("alder_fence_gate"));
  public static final Block ALDER_PLANKS =
      Registry.BLOCK.get(TerraFeram.identifier("alder_planks"));
  public static final Block ALDER_PRESSURE_PLATE =
      Registry.BLOCK.get(TerraFeram.identifier("alder_pressure_plate"));
  public static final Block ALDER_SIGN = Registry.BLOCK.get(TerraFeram.identifier("alder_sign"));
  public static final Block ALDER_SLAB = Registry.BLOCK.get(TerraFeram.identifier("alder_slab"));
  public static final Block ALDER_STAIRS =
      Registry.BLOCK.get(TerraFeram.identifier("alder_stairs"));
  public static final Block STRIPPED_ALDER_LOG =
      Registry.BLOCK.get(TerraFeram.identifier("stripped_alder_log"));
  public static final Block STRIPPED_ALDER_WOOD =
      Registry.BLOCK.get(TerraFeram.identifier("stripped_alder_wood"));
  public static final Block ALDER_TRAPDOOR =
      Registry.BLOCK.get(TerraFeram.identifier("alder_trapdoor"));
  public static final Block ALDER_WOOD = Registry.BLOCK.get(TerraFeram.identifier("alder_wood"));

  public static final Block GINSENG_FLOWER = new GinsengFlower().register();

  public static void register() {}
}
