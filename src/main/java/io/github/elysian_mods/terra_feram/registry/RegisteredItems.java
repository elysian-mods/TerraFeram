package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.item.GinsengRoot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class RegisteredItems extends Items {
  public static final Item ALDER_LEAVES = Registry.ITEM.get(TerraFeram.identifier("alder_leaves"));
  public static final Item ALDER_LOG = Registry.ITEM.get(TerraFeram.identifier("alder_log"));
  public static final Item ALDER_SAPLING =
      Registry.ITEM.get(TerraFeram.identifier("alder_sapling"));

  public static final Item ALDER_BUTTON = Registry.ITEM.get(TerraFeram.identifier("alder_button"));
  public static final Item ALDER_DOOR = Registry.ITEM.get(TerraFeram.identifier("alder_door"));
  public static final Item ALDER_FENCE = Registry.ITEM.get(TerraFeram.identifier("alder_fence"));
  public static final Item ALDER_FENCE_GATE =
      Registry.ITEM.get(TerraFeram.identifier("alder_fence_gate"));
  public static final Item ALDER_PLANKS = Registry.ITEM.get(TerraFeram.identifier("alder_planks"));
  public static final Item ALDER_PRESSURE_PLATE =
      Registry.ITEM.get(TerraFeram.identifier("alder_pressure_plate"));
  public static final Item ALDER_SIGN = Registry.ITEM.get(TerraFeram.identifier("alder_sign"));
  public static final Item ALDER_SLAB = Registry.ITEM.get(TerraFeram.identifier("alder_slab"));
  public static final Item ALDER_STAIRS = Registry.ITEM.get(TerraFeram.identifier("alder_stairs"));
  public static final Item STRIPPED_ALDER_LOG =
      Registry.ITEM.get(TerraFeram.identifier("stripped_alder_log"));
  public static final Item STRIPPED_ALDER_WOOD =
      Registry.ITEM.get(TerraFeram.identifier("stripped_alder_wood"));
  public static final Item ALDER_TRAPDOOR =
      Registry.ITEM.get(TerraFeram.identifier("alder_trapdoor"));
  public static final Item ALDER_WOOD = Registry.ITEM.get(TerraFeram.identifier("alder_wood"));

  public static final Item GINSENG_ROOT = new GinsengRoot().register();

  public static void register() {}
}
