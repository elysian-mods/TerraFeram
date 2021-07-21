package io.github.elysian_mods.terra_feram.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public abstract class LeavesBlockWrapper extends BlockWrapper {
  @Override
  public Block register() {
    super.register();
    FlammableBlockRegistry.getDefaultInstance().add(block, 60, 30);
    return block;
  }

  public static class LeavesBlock extends net.minecraft.block.LeavesBlock {
    public LeavesBlock() {
      super(
          FabricBlockSettings.of(Material.LEAVES)
              .sounds(BlockSoundGroup.GRASS)
              .breakByTool(FabricToolTags.HOES)
              .strength(0.2F)
              .nonOpaque()
              .ticksRandomly());
    }
  }
}
