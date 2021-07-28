package io.github.elysian_mods.terra_feram.util;

import java.util.HashMap;
import java.util.Map;

public class Models {
    public static HashMap<String, String> MAP = new HashMap<>();

    public static String get(String name) {
        return MAP.get(name);
    }

    public static String put(String type, String name, String model) {
        return MAP.put(type + "/" + name, model);
    }

    public static void putAll(String type, String name, Map<String, String> models) {
        models.forEach((modelName, model) -> Models.put(type, name + modelName, model));
    }
}
