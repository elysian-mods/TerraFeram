package io.github.elysian_mods.terra_feram.item;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public abstract class ItemWrapper {
  public String name;
  public Item item;

  public Item register() {
    return Registry.register(Registry.ITEM, TerraFeram.identifier(name), item);
  }
}
