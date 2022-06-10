package io.github.elysian_mods.terra_feram.item;

import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;

public abstract class ItemWrapper {
  public String name;
  public Item item;
  public String translation;

  public Identifier nameId;
  public Identifier itemId;

  protected JModel itemModel;

  protected Map<Identifier, JRecipe> recipes;

  public ItemWrapper(String name) {
    this.name = name;
    nameId = nameId(name);
    itemId = itemId(name);
  }

  public Item register() {
    addModel(itemId, itemModel);

    if (recipes != null) {
      addRecipes(recipes);
    }

    lang.item(nameId, translation);

    return Registry.register(Registry.ITEM, nameId, item);
  }
}
