package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public class ARRPUtil {
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

    public static void addModel(Identifier id, JModel model) {
        TerraFeram.RESOURCE_PACK.addModel(model, id);
    }

    public static void addRecipe(Identifier id, JRecipe recipe) {
        TerraFeram.RESOURCE_PACK.addRecipe(id, recipe);
    }

    public static void addTag(Identifier id, String[] elements) {
        JTag tag = JTag.tag();
        for (String element : elements) {
            if (element.charAt(0) == '#') {
                tag.tag(TerraFeram.identifier(element.substring(1)));
            } else {
                tag.add(TerraFeram.identifier(element));
            }
        }
        TerraFeram.RESOURCE_PACK.addTag(id, tag);
    }

    public static Identifier blockId(String name) {
        return genericId("block/%s", name);
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

    @SafeVarargs
    public static <K, V> Map<K, V> mapBuilder(Pair<K, V>... entries) {
        Map<K, V> map = new HashMap<>();
        for (Pair<K, V> entry : entries) {
            map.put(entry.getLeft(), entry.getRight());
        }
        return map;
    }

    public static Pair<Identifier, JModel> modelBuilder(Identifier blockId, String parent,
                                                        Map<String, Identifier> vars) {
        JTextures textures = textures();
        for (Map.Entry<String, Identifier> var : vars.entrySet()) {
            textures = textures.var(var.getKey(), var.getValue().toString());
        }
        return new Pair<>(blockId, model(parent != null ? "minecraft:block/" + parent : null).textures(textures));
    }

    public static Identifier nameId(String name) {
        return TerraFeram.identifier(name);
    }

    public static Map<String, Identifier> texture(Identifier blockId) {
        return mapBuilder(new Pair<>("texture", blockId));
    }
}
