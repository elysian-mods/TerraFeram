package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.item.GinsengRoot;
import io.github.elysian_mods.terra_feram.util.ItemUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class RegisteredItems extends Items {
  public static final Item OAK_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("oak_bark"), new Item(ItemUtil.VANILLA_MISC));
  public static final Item SPRUCE_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("spruce_bark"), new Item(ItemUtil.VANILLA_MISC));
  public static final Item BIRCH_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("birch_bark"), new Item(ItemUtil.VANILLA_MISC));
  public static final Item JUNGLE_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("jungle_bark"), new Item(ItemUtil.VANILLA_MISC));
  public static final Item ACACIA_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("acacia_bark"), new Item(ItemUtil.VANILLA_MISC));
  public static final Item DARK_OAK_BARK =
      Registry.register(
          Registry.ITEM, TerraFeram.identifier("dark_oak_bark"), new Item(ItemUtil.VANILLA_MISC));

  public static final Item ASH_LEAVES = Registry.ITEM.get(TerraFeram.identifier("ash_leaves"));
  public static final Item ASH_LOG = Registry.ITEM.get(TerraFeram.identifier("ash_log"));
  public static final Item ASH_SAPLING =
      Registry.ITEM.get(TerraFeram.identifier("ash_sapling"));

  public static final Item ASH_BUTTON = Registry.ITEM.get(TerraFeram.identifier("ash_button"));
  public static final Item ASH_DOOR = Registry.ITEM.get(TerraFeram.identifier("ash_door"));
  public static final Item ASH_FENCE = Registry.ITEM.get(TerraFeram.identifier("ash_fence"));
  public static final Item ASH_FENCE_GATE =
      Registry.ITEM.get(TerraFeram.identifier("ash_fence_gate"));
  public static final Item ASH_PLANKS = Registry.ITEM.get(TerraFeram.identifier("ash_planks"));
  public static final Item ASH_PRESSURE_PLATE =
      Registry.ITEM.get(TerraFeram.identifier("ash_pressure_plate"));
  public static final Item ASH_SIGN = Registry.ITEM.get(TerraFeram.identifier("ash_sign"));
  public static final Item ASH_SLAB = Registry.ITEM.get(TerraFeram.identifier("ash_slab"));
  public static final Item ASH_STAIRS = Registry.ITEM.get(TerraFeram.identifier("ash_stairs"));
  public static final Item STRIPPED_ASH_LOG =
      Registry.ITEM.get(TerraFeram.identifier("stripped_ash_log"));
  public static final Item STRIPPED_ASH_WOOD =
      Registry.ITEM.get(TerraFeram.identifier("stripped_ash_wood"));
  public static final Item ASH_TRAPDOOR =
      Registry.ITEM.get(TerraFeram.identifier("ash_trapdoor"));
  public static final Item ASH_WOOD = Registry.ITEM.get(TerraFeram.identifier("ash_wood"));
  public static final Item ASH_BARK = Registry.ITEM.get(TerraFeram.identifier("ash_bark"));

  public static final Item GINSENG_ROOT = new GinsengRoot().register();

  public static void register() {}
}
