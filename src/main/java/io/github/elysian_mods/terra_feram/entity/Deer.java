package io.github.elysian_mods.terra_feram.entity;

import com.google.common.collect.ImmutableList;
import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.client.Client;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Deer extends AnimalEntity {
    public Deer(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static class Renderer extends MobEntityRenderer<Deer, Model> {

        public Renderer(EntityRendererFactory.Context context) {
            super(context, new Deer.Model(context.getPart(Client.DEER_MODEL_LAYER)), 0.5f);
        }

        @Override
        public Identifier getTexture(Deer entity) {
            return TerraFeram.identifier("textures/entity/deer/deer.png");
        }
    }

    public static class Model extends EntityModel<Deer> {

        private final ModelPart base;

        public Model(ModelPart modelPart) {
            this.base = modelPart.getChild(EntityModelPartNames.CUBE);
        }

        public static TexturedModelData getTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 12F, -6F
                    , 12F, 12F, 12F), ModelTransform.pivot(0F, 0F, 0F));
            return TexturedModelData.of(modelData, 64, 64);
        }

        @Override
        public void setAngles(Deer entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
                              float headPitch) {
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            ImmutableList.of(this.base).forEach((modelRenderer) -> {
                modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            });
        }
    }
}
