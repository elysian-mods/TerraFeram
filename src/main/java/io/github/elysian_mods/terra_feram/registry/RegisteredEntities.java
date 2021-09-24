package io.github.elysian_mods.terra_feram.registry;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.entity.Deer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class RegisteredEntities {
  public static final EntityType<Deer> DEER =
      Registry.register(
          Registry.ENTITY_TYPE,
          TerraFeram.identifier("cube"),
          FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Deer::new)
              .dimensions(EntityDimensions.fixed(0.7f, 1.8f))
              .build());

  public static void register() {
    FabricDefaultAttributeRegistry.register(DEER, Deer.createMobAttributes());
  }
}
