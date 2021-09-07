package io.github.elysian_mods.terra_feram.util;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

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

    public static void addModel(Identifier id, JModel model) {
        TerraFeram.RESOURCE_PACK.addModel(model, id);
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
