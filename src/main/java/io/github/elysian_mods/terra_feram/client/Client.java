package io.github.elysian_mods.terra_feram.client;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.entity.Deer;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    public static final EntityModelLayer DEER_MODEL_LAYER = new EntityModelLayer(TerraFeram.identifier("cube"),
            "main");

    @Override
    public void onInitializeClient() {
        render(RegisteredBlocks.ALDER_LEAVES, RenderType.TRANSPARENT);
        render(RegisteredBlocks.ALDER_SAPLING, RenderType.TRANSPARENT);
        render(RegisteredBlocks.GINSENG_FLOWER, RenderType.TRANSPARENT);

        EntityRendererRegistry.INSTANCE.register(RegisteredEntities.DEER, Deer.Renderer::new);
        EntityModelLayerRegistry.registerModelLayer(DEER_MODEL_LAYER, Deer.Model::getTexturedModelData);
    }

    public void render(Block block, RenderType renderType) {
        switch (renderType) {
            case TRANSLUCENT -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            case TRANSPARENT -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}
