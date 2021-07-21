package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.item.AlderLeaves;
import io.github.elysian_mods.terra_feram.item.AlderLog;
import io.github.elysian_mods.terra_feram.item.AlderSapling;
import io.github.elysian_mods.terra_feram.item.GinsengRoot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class RegisteredItems extends Items {
  public static final Item ALDER_LEAVES = new AlderLeaves().register();
  public static final Item ALDER_LOG = new AlderLog().register();
  public static final Item ALDER_SAPLING = new AlderSapling().register();
  public static final Item GINSENG_ROOT = new GinsengRoot().register();

  public static void register() {}
}
