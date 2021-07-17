package io.github.elysian_mods.terra_feram.client;

import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class TerraFeram implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        render(RegisteredBlocks.GINSENG_FLOWER, RenderType.TRANSPARENT);
    }

    public void render(Block block, RenderType renderType) {
        switch (renderType) {
            case TRANSLUCENT -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            case TRANSPARENT -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}
