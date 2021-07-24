package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.mixin.AxeItemAccessor;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.util.Item;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class TreeType {
  public String name;
  protected SignType signColor;

  protected ConfiguredDecorator<?> decorator;
  private ConfiguredFeature<?, ?> configured;
  private ConfiguredFeature<?, ?> decorated;

  protected Collection<RegistryKey<Biome>> biomes;
  protected Heightmap.Type heightmap = Heightmap.Type.WORLD_SURFACE_WG;
  protected int maxWaterDepth = 0;
  protected GenerationStep.Feature step = GenerationStep.Feature.VEGETAL_DECORATION;

  protected int baseCount = 0;
  protected float extraChance = 1;
  protected int extraCount = 1;

  protected TrunkPlacer trunkPlacer;
  protected FoliagePlacer foliagePlacer;
  protected FeatureSize size = new TwoLayersFeatureSize(1, 0, 1);

  protected Block leaves;
  protected Block log;
  protected Block sapling;

  protected Block button;
  protected Block door;
  protected Block fence;
  protected Block fence_gate;
  protected Block planks;
  protected Block pressure_plate;
  protected Block sign;
  protected Block slab;
  protected Block stairs;
  protected Block stripped_log;
  protected Block stripped_wood;
  protected Block trapdoor;
  protected Block wood;

  protected TreeType() {
    leaves = new Leaves();
    log = new Log();
    sapling = new Sapling();

    button = new Button();
    door = new Door();
    fence = new Fence();
    fence_gate = new FenceGate();
    planks = new Planks();
    pressure_plate = new PressurePlate();
    sign = new Sign();
    slab = new Slab();
    stairs = new Stairs();
    stripped_log = new StrippedLog();
    stripped_wood = new StrippedWood();
    trapdoor = new Trapdoor();
    wood = new Wood();
  }

  private TreeFeatureConfig build() {
    return new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(log.getDefaultState()),
            trunkPlacer,
            new SimpleBlockStateProvider(leaves.getDefaultState()),
            new SimpleBlockStateProvider(sapling.getDefaultState()),
            foliagePlacer,
            size)
        .ignoreVines()
        .forceDirt()
        .build();
  }

  public void configure() {
    configured = Feature.TREE.configure(build());
    decorated =
        configured
            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(heightmap)))
            .decorate(
                Decorator.WATER_DEPTH_THRESHOLD.configure(
                    new WaterDepthThresholdDecoratorConfig(maxWaterDepth)))
            .spreadHorizontally()
            .decorate(
                Decorator.COUNT_EXTRA.configure(
                    new CountExtraDecoratorConfig(baseCount, extraChance, extraCount)));
    if (decorator != null) decorated = decorated.decorate(decorator);
  }

  public ConfiguredFeature<?, ?> register() {
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, TerraFeram.identifier(name + "_tree"));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), decorated);
    BiomeModifications.addFeature(BiomeSelectors.includeByKey(biomes), step, key);

    registerBlock(leaves, "%s_leaves");
    FlammableBlockRegistry.getDefaultInstance().add(leaves, 60, 30);
    registerBlock(log, "%s_log");
    FlammableBlockRegistry.getDefaultInstance().add(log, 5, 5);
    registerBlock(sapling, "%s_sapling");

    registerBlock(button, "%s_button");
    registerBlock(door, "%s_door");
    registerBlock(fence, "%s_fence");
    FlammableBlockRegistry.getDefaultInstance().add(fence, 20, 5);
    registerBlock(fence_gate, "%s_fence_gate");
    FlammableBlockRegistry.getDefaultInstance().add(fence_gate, 20, 5);
    registerBlock(planks, "%s_planks");
    FlammableBlockRegistry.getDefaultInstance().add(planks, 20, 5);
    registerBlock(pressure_plate, "%s_pressure_plate");
    registerBlock(sign, "%s_sign");
    registerBlock(slab, "%s_slab");
    FlammableBlockRegistry.getDefaultInstance().add(slab, 20, 5);
    registerBlock(stairs, "%s_stairs");
    FlammableBlockRegistry.getDefaultInstance().add(stairs, 20, 5);
    registerBlock(trapdoor, "%s_trapdoor");
    registerBlock(wood, "%s_wood");
    FlammableBlockRegistry.getDefaultInstance().add(wood, 5, 5);

    registerBlock(stripped_log, "stripped_%s_log");
    FlammableBlockRegistry.getDefaultInstance().add(stripped_log, 5, 5);
    registerBlock(stripped_wood, "stripped_%s_wood");
    FlammableBlockRegistry.getDefaultInstance().add(stripped_wood, 5, 5);

    Map<Block, Block> stripper = new HashMap<>(AxeItemAccessor.getStrippedBlocks());
    stripper.put(log, stripped_log);
    stripper.put(wood, stripped_wood);
    AxeItemAccessor.setStrippedBlocks(stripper);

    return configured;
  }

  private void registerBlock(Block block, String template) {
    if (block != null) {
      String name = String.format(template, this.name);
      Registry.register(Registry.BLOCK, TerraFeram.identifier(name), block);
      Registry.register(
          Registry.ITEM, TerraFeram.identifier(name), new BlockItem(block, Item.DEFAULT_SETTINGS));
    }
  }

  public class Generator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
      return (ConfiguredFeature<TreeFeatureConfig, ?>) TreeType.this.configured;
    }
  }

  public static class Leaves extends LeavesBlock {
    public Leaves() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LEAVES));
    }
  }

  public static class Log extends PillarBlock {
    public Log() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LOG));
    }
  }

  public class Sapling extends SaplingBlock {
    public Sapling() {
      super(new Generator(), FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }

  public static class Button extends WoodenButtonBlock {
    public Button() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_BUTTON));
    }
  }

  public static class Door extends DoorBlock {
    public Door() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_DOOR));
    }
  }

  public static class Fence extends FenceBlock {
    public Fence() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE));
    }
  }

  public static class FenceGate extends FenceGateBlock {
    public FenceGate() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE_GATE));
    }
  }

  public static class Planks extends Block {
    public Planks() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PLANKS));
    }
  }

  public static class PressurePlate extends PressurePlateBlock {
    public PressurePlate() {
      super(
          ActivationRule.EVERYTHING,
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PRESSURE_PLATE));
    }
  }

  public class Sign extends SignBlock {
    public Sign() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SIGN), TreeType.this.signColor);
    }
  }

  public static class Slab extends SlabBlock {
    public Slab() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SLAB));
    }
  }

  public class Stairs extends StairsBlock {
    public Stairs() {
      super(
          TreeType.this.planks.getDefaultState(),
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_STAIRS));
    }
  }

  public static class StrippedLog extends PillarBlock {
    public StrippedLog() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_LOG));
    }
  }

  public static class StrippedWood extends PillarBlock {
    public StrippedWood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_WOOD));
    }
  }

  public static class Trapdoor extends TrapdoorBlock {
    public Trapdoor() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_TRAPDOOR));
    }
  }

  public static class Wood extends PillarBlock {
    public Wood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_WOOD));
    }
  }
}
