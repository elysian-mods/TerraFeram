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

  public static final Item GINSENG_ROOT = new GinsengRoot().register();

  public static void register() {}
}
