package io.github.elysian_mods.terra_feram.mixin;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.util.Models;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelLoader.class)
public class ModelInjector {
  @Inject(
      method = "loadModelFromJson",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/resource/ResourceManager;"
                      + "getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"),
      cancellable = true)
  public void addModel(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
    if (!TerraFeram.MOD_ID.equals(id.getNamespace())) return;

    String modelJson = Models.get(id.getPath());
    if (modelJson == null || modelJson.isEmpty()) return;

    JsonUnbakedModel model = JsonUnbakedModel.deserialize(modelJson);
    model.id = id.toString();
    cir.setReturnValue(model);
    cir.cancel();
  }
}
