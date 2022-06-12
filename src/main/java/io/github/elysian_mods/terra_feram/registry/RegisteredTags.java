package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static io.github.elysian_mods.terra_feram.TerraFeram.RESOURCE_PACK;
import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;

public class RegisteredTags {

    public static final Map<Identifier, JTag> TAGS = new HashMap<>();

    public static void register() {
        addSubTag("wooden_buttons");
        addSubTag("wooden_doors");
        addSubTag("wooden_fences");
        addSubTag("wooden_pressure_plates");
        addSubTag("wooden_slabs");
        addSubTag("wooden_stairs");

        addItemTag(TerraFeram.id("bark"), new String[]{RegisteredItems.OAK_BARK.toString(),
                RegisteredItems.SPRUCE_BARK.toString(), RegisteredItems.BIRCH_BARK.toString(),
                RegisteredItems.JUNGLE_BARK.toString(), RegisteredItems.ACACIA_BARK.toString(),
                RegisteredItems.DARK_OAK_BARK.toString()});

        for (Map.Entry<Identifier, JTag> tag : TAGS.entrySet()) {
            RESOURCE_PACK.addTag(tag.getKey(), tag.getValue());
        }
    }
}
