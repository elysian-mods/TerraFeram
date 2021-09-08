package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class ARRPUtil {
    public static void addBlockModels(Map<Identifier, JModel> blockModels) {
        for (Map.Entry<Identifier, JModel> model : blockModels.entrySet()) {
            TerraFeram.RESOURCE_PACK.addModel(model.getValue(), model.getKey());
        }
    }

    public static void addBlockState(Identifier id, JState blockState) {
        TerraFeram.RESOURCE_PACK.addBlockState(blockState, id);
    }

    public static void addDualTag(String name, String[] elements) {
        addTag(new Identifier("blocks/" + name), elements);
        addTag(new Identifier("items/" + name), elements);
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
                tag.tag(new Identifier(element.substring(1)));
            } else {
                tag.add(new Identifier(element));
            }
        }
        TerraFeram.RESOURCE_PACK.addTag(id, tag);
    }

    public static Identifier blockId(String name) {
        return genericId("block/%s", name);
    }

    public static Identifier genericId(String format, String name) {
        return TerraFeram.identifier(String.format(format, name));
    }

    public static Identifier itemId(String name) {
        return genericId("item/%s", name);
    }

    public static Identifier nameId(String name) {
        return TerraFeram.identifier(name);
    }
}
