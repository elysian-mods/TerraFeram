package io.github.elysian_mods.terra_feram.client;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RenderMap {
  private static final HashMap<Block, RenderType> MAP = new HashMap<>();

  public static Set<Map.Entry<Block, RenderType>> entrySet() {
    return MAP.entrySet();
  }

  public static RenderType get(Block block) {
    return MAP.get(block);
  }

  public static RenderType put(Block block, RenderType renderType) {
    return MAP.put(block, renderType);
  }
}
