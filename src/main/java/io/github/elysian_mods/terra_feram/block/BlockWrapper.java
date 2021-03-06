package io.github.elysian_mods.terra_feram.block;

import io.github.elysian_mods.terra_feram.client.RenderMap;
import io.github.elysian_mods.terra_feram.client.RenderType;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.DEFAULT_SETTINGS;

public abstract class BlockWrapper {
  public String name;
  public Block block;
  public String translation;

  protected RenderType renderType = RenderType.OPAQUE;

  public Identifier nameId;
  public Identifier blockId;
  public Identifier itemId;

  protected Map<Identifier, JModel> blockModels;
  protected JModel itemModel;
  protected JState blockState;

  protected Map<Identifier, JRecipe> recipes = new HashMap<>();
  protected JLootTable lootTable;

  protected List<Identifier> blockTags = List.of();
  protected List<Identifier> itemTags = List.of();

  protected int burn = 0;
  protected int spread = 0;

  public BlockWrapper(String name) {
    this.name = name;
    nameId = nameId(name);
    blockId = blockId(name);
    itemId = itemId(name);
  }

  public Block register() {
    RenderMap.put(block, renderType);

    addBlockModels(blockModels);
    addModel(itemId, itemModel);
    addBlockState(nameId, blockState);

    addRecipes(recipes);
    if (lootTable != null) {
      addLootTable(blockId, lootTable);
    }

    for (Identifier tag : blockTags) {
      addTag(folder(tag, "blocks"), new String[] {name});
    }
    for (Identifier tag : itemTags) {
      addTag(folder(tag, "items"), new String[] {name});
    }

    lang.block(nameId, translation);
    lang.item(nameId, translation);

    if (burn > 0) {
      FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
    }

    Registry.register(Registry.ITEM, nameId, new BlockItem(block, DEFAULT_SETTINGS));
    return Registry.register(Registry.BLOCK, nameId, block);
  }
}
