package io.github.elysian_mods.terra_feram.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

public abstract class LogBlockWrapper extends BlockWrapper {
  @Override
  public Block register() {
    super.register();
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 5);
    return block;
  }

  public static class LogBlock extends PillarBlock {
    public LogBlock() {
      super(
          FabricBlockSettings.of(Material.WOOD)
              .sounds(BlockSoundGroup.WOOD)
              .breakByTool(FabricToolTags.AXES)
              .strength(2.0F));
    }
  }
}
