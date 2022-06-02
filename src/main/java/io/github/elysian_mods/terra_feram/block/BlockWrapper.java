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

  protected Map<Identifier, JRecipe> recipes;
  protected JLootTable lootTable;

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

    if (recipes != null) {
      addRecipes(recipes);
    }
    if (lootTable != null) {
      addLootTable(blockId, lootTable);
    }

    if (burn > 0) {
      FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
    }

    Registry.register(Registry.ITEM, nameId, new BlockItem(block, DEFAULT_SETTINGS));
    return Registry.register(Registry.BLOCK, nameId, block);
  }
}
