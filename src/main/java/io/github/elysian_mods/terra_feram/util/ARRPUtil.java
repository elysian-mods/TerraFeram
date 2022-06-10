package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public class ARRPUtil {
    public static final Map<Identifier, JTag> tags = new HashMap<>();
    public static final JLang lang = JLang.lang();

    public static void addBlockModels(Map<Identifier, JModel> blockModels) {
        for (Map.Entry<Identifier, JModel> model : blockModels.entrySet()) {
            TerraFeram.RESOURCE_PACK.addModel(model.getValue(), model.getKey());
        }
    }

    public static void addBlockState(Identifier id, JState blockState) {
        TerraFeram.RESOURCE_PACK.addBlockState(blockState, id);
    }

    public static void addDualTag(Identifier id, String[] elements) {
        addTag(folder(id, "blocks"), elements);
        addTag(folder(id, "items"), elements);
    }

    public static void addLootTable(Identifier id, JLootTable lootTable) {
        TerraFeram.RESOURCE_PACK.addLootTable(id, lootTable);
    }

    public static void addModel(Identifier id, JModel model) {
        TerraFeram.RESOURCE_PACK.addModel(model, id);
    }

    public static void addRecipes(Map<Identifier, JRecipe> recipes) {
        for (Map.Entry<Identifier, JRecipe> recipe: recipes.entrySet()) {
            TerraFeram.RESOURCE_PACK.addRecipe(recipe.getKey(), recipe.getValue());
        }
    }

    public static void addTag(Identifier id, String[] elements) {
        JTag tag = tags.containsKey(id) ? tags.get(id) : JTag.tag();

        for (String element : elements) {
            if (element.charAt(0) == '#') {
                tag.tag(TerraFeram.identifier(element.substring(1)));
            } else {
                tag.add(TerraFeram.identifier(element));
            }
        }

        tags.put(id, tag);
    }

    public static Identifier blockId(String name) {
        return genericId("block/%s", name);
    }

    public static Map<Identifier, JModel> blockModels(Map<Identifier, String> parents, Map<String, Identifier> vars) {
        Map<Identifier, JModel> blockModels = new HashMap<>();
        for (Map.Entry<Identifier, String> parent : parents.entrySet()) {
            JTextures textures = textures();
            for (Map.Entry<String, Identifier> var: vars.entrySet()) {
                textures = textures.var(var.getKey(), var.getValue().toString());
            }
            blockModels.put(parent.getKey(),
                    model(!parent.getValue().isBlank() ? "minecraft:block/" + parent.getValue() : null).textures(textures));
        }
        return blockModels;
    }

    public static Identifier folder(Identifier id, String folder) {
        return new Identifier(id.getNamespace() + folder + "/" + id.getPath());
    }

    public static JModel generatedItem(Identifier itemId) {
        return JModel.model("minecraft:item/generated").textures(textures().layer0(itemId.toString()));
    }

    public static Identifier genericId(String format, String name) {
        return TerraFeram.identifier(String.format(format, name));
    }

    public static Identifier itemId(String name) {
        return genericId("item/%s", name);
    }

    public static JModel itemModel(Identifier itemId) {
        return JModel.model(itemId);
    }

    public static Identifier nameId(String name) {
        return TerraFeram.identifier(name);
    }

    public static Map<String, Identifier> texture(Identifier blockId) {
        return Map.of("texture", blockId);
    }
}
