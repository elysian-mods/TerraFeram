package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.mixin.AxeItemAccessor;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.util.ItemUtil;
import io.github.elysian_mods.terra_feram.util.LogsToBark;
import io.github.elysian_mods.terra_feram.util.Models;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class TreeType {
  public String name;
  protected SignType signColor = SignType.OAK;

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

  protected Leaves leaves;
  protected Log log;
  protected Sapling sapling;

  protected Button button;
  protected Door door;
  protected Fence fence;
  protected FenceGate fenceGate;
  protected Planks planks;
  protected PressurePlate pressurePlate;
  protected Sign sign;
  protected Slab slab;
  protected Stairs stairs;
  protected StrippedLog strippedLog;
  protected StrippedWood strippedWood;
  protected Trapdoor trapdoor;
  protected Wood wood;

  protected Bark bark;

  protected TreeType(String name) {
    this.name = name;

    leaves = new Leaves();
    log = new Log();
    sapling = new Sapling();

    button = new Button();
    door = new Door();
    fence = new Fence();
    fenceGate = new FenceGate();
    planks = new Planks();
    pressurePlate = new PressurePlate();
    sign = new Sign();
    slab = new Slab();
    stairs = new Stairs();
    strippedLog = new StrippedLog();
    strippedWood = new StrippedWood();
    trapdoor = new Trapdoor();
    wood = new Wood();

    bark = new Bark();
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

  private void configure() {
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

  protected void create() {
    configure();
    generate();
  }

  private void generate() {
    Models.put("item", leaves.name, leaves.itemModel);
    Models.putAll("block", leaves.name, leaves.blockModels);
    Models.put("item", log.name, log.itemModel);
    Models.putAll("block", log.name, log.blockModels);
    Models.put("item", sapling.name, sapling.itemModel);
    Models.putAll("block", sapling.name, sapling.blockModels);

    Models.put("item", button.name, button.itemModel);
    Models.putAll("block", button.name, button.blockModels);
    Models.put("item", door.name, door.itemModel);
    Models.putAll("block", door.name, door.blockModels);
    Models.put("item", fence.name, fence.itemModel);
    Models.putAll("block", fence.name, fence.blockModels);
    Models.put("item", fenceGate.name, fenceGate.itemModel);
    Models.putAll("block", fenceGate.name, fenceGate.blockModels);
    Models.put("item", planks.name, planks.itemModel);
    Models.putAll("block", planks.name, planks.blockModels);
    Models.put("item", pressurePlate.name, pressurePlate.itemModel);
    Models.putAll("block", pressurePlate.name, pressurePlate.blockModels);
    Models.put("item", sign.name, sign.itemModel);
    Models.putAll("block", sign.name, sign.blockModels);
    Models.put("item", slab.name, slab.itemModel);
    Models.putAll("block", slab.name, slab.blockModels);
    Models.put("item", stairs.name, stairs.itemModel);
    Models.putAll("block", stairs.name, stairs.blockModels);
    Models.put("item", trapdoor.name, trapdoor.itemModel);
    Models.putAll("block", trapdoor.name, trapdoor.blockModels);
    Models.put("item", wood.name, wood.itemModel);
    Models.putAll("block", wood.name, wood.blockModels);

    Models.put("item", strippedLog.name, strippedLog.itemModel);
    Models.putAll("block", strippedLog.name, strippedLog.blockModels);
    Models.put("item", strippedWood.name, strippedWood.itemModel);
    Models.putAll("block", strippedWood.name, strippedWood.blockModels);

    Models.put("item", bark.name, bark.itemModel);
  }

  public ConfiguredFeature<?, ?> register() {
    RegistryKey<ConfiguredFeature<?, ?>> key =
        RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, TerraFeram.identifier(name + "_tree"));
    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), decorated);
    BiomeModifications.addFeature(BiomeSelectors.includeByKey(biomes), step, key);

    registerBlock(leaves, leaves.name);
    FlammableBlockRegistry.getDefaultInstance().add(leaves, 60, 30);
    registerBlock(log, log.name);
    FlammableBlockRegistry.getDefaultInstance().add(log, 5, 5);
    registerBlock(sapling, sapling.name);

    registerBlock(button, button.name);
    registerBlock(door, door.name);
    registerBlock(fence, fence.name);
    FlammableBlockRegistry.getDefaultInstance().add(fence, 20, 5);
    registerBlock(fenceGate, fenceGate.name);
    FlammableBlockRegistry.getDefaultInstance().add(fenceGate, 20, 5);
    registerBlock(planks, planks.name);
    FlammableBlockRegistry.getDefaultInstance().add(planks, 20, 5);
    registerBlock(pressurePlate, pressurePlate.name);
    registerBlock(sign, sign.name);
    registerBlock(slab, slab.name);
    FlammableBlockRegistry.getDefaultInstance().add(slab, 20, 5);
    registerBlock(stairs, stairs.name);
    FlammableBlockRegistry.getDefaultInstance().add(stairs, 20, 5);
    registerBlock(trapdoor, trapdoor.name);
    registerBlock(wood, wood.name);
    FlammableBlockRegistry.getDefaultInstance().add(wood, 5, 5);

    registerBlock(strippedLog, strippedLog.name);
    FlammableBlockRegistry.getDefaultInstance().add(strippedLog, 5, 5);
    registerBlock(strippedWood, strippedWood.name);
    FlammableBlockRegistry.getDefaultInstance().add(strippedWood, 5, 5);

    Map<Block, Block> stripper = new HashMap<>(AxeItemAccessor.getStrippedBlocks());
    stripper.put(log, strippedLog);
    stripper.put(wood, strippedWood);
    AxeItemAccessor.setStrippedBlocks(stripper);

    Registry.register(Registry.ITEM, TerraFeram.identifier(bark.name), bark);
    LogsToBark.put(log, bark, 4);
    LogsToBark.put(wood, bark, 6);

    return configured;
  }

  private String generateModel(String templatePath) {
    try {
      return String.format(
          new String(
              Files.readAllBytes(
                  Path.of(
                      "../src/main/resources/assets/terra_feram/models/"
                          + templatePath
                          + ".json"))),
          name);
    } catch (IOException ignored) {
    }
    return "";
  }

  private void registerBlock(Block block, String name) {
    if (block != null) {
      Registry.register(Registry.BLOCK, TerraFeram.identifier(name), block);
      Registry.register(
          Registry.ITEM,
          TerraFeram.identifier(name),
          new BlockItem(block, ItemUtil.DEFAULT_SETTINGS));
    }
  }

  public class Generator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
      return (ConfiguredFeature<TreeFeatureConfig, ?>) TreeType.this.configured;
    }
  }

  protected class Leaves extends LeavesBlock {
    public String name = String.format("%s_leaves", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_leaves"));
          }
        };
    public String itemModel = generateModel("item/tree_leaves");

    public Leaves() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LEAVES));
    }
  }

  protected class Log extends PillarBlock {
    public String name = String.format("%s_log", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_log"));
            put("_horizontal", generateModel("block/tree_log_horizontal"));
          }
        };
    public String itemModel = generateModel("item/tree_log");

    public Log() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LOG));
    }
  }

  protected class Sapling extends SaplingBlock {
    public String name = String.format("%s_sapling", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_sapling"));
          }
        };
    public String itemModel = generateModel("item/tree_sapling");

    public Sapling() {
      super(new Generator(), FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }

  protected class Button extends WoodenButtonBlock {
    public String name = String.format("%s_button", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("_inventory", generateModel("block/tree_button_inventory"));
            put("_pressed", generateModel("block/tree_button_pressed"));
          }
        };
    public String itemModel = generateModel("item/tree_button");

    public Button() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_BUTTON));
    }
  }

  protected class Door extends DoorBlock {
    public String name = String.format("%s_door", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("_bottom", generateModel("block/tree_door_bottom"));
            put("_bottom_hinge", generateModel("block/tree_door_bottom_hinge"));
            put("_top", generateModel("block/tree_door_top"));
            put("_top_hinge", generateModel("block/tree_door_top_hinge"));
          }
        };
    public String itemModel = generateModel("item/tree_door");

    public Door() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_DOOR));
    }
  }

  protected class Fence extends FenceBlock {
    public String name = String.format("%s_fence", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("_inventory", generateModel("block/tree_fence_inventory"));
            put("_post", generateModel("block/tree_fence_post"));
            put("_side", generateModel("block/tree_fence_side"));
          }
        };
    public String itemModel = generateModel("item/tree_fence");

    public Fence() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE));
    }
  }

  protected class FenceGate extends FenceGateBlock {
    public String name = String.format("%s_fence_gate", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_fence_gate"));
            put("_open", generateModel("block/tree_fence_gate_open"));
            put("_wall", generateModel("block/tree_fence_gate_wall"));
            put("_wall_open", generateModel("block/tree_fence_gate_wall_open"));
          }
        };
    public String itemModel = generateModel("item/tree_fence_gate");

    public FenceGate() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE_GATE));
    }
  }

  protected class Planks extends Block {
    public String name = String.format("%s_planks", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_planks"));
          }
        };
    public String itemModel = generateModel("item/tree_planks");

    public Planks() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PLANKS));
    }
  }

  protected class PressurePlate extends PressurePlateBlock {
    public String name = String.format("%s_pressure_plate", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_pressure_plate"));
            put("_down", generateModel("block/tree_pressure_plate_down"));
          }
        };
    public String itemModel = generateModel("item/tree_pressure_plate");

    public PressurePlate() {
      super(
          ActivationRule.EVERYTHING,
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PRESSURE_PLATE));
    }
  }

  protected class Sign extends SignBlock {
    public String name = String.format("%s_sign", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_sign"));
          }
        };
    public String itemModel = generateModel("item/tree_sign");

    public Sign() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SIGN), TreeType.this.signColor);
    }
  }

  protected class Slab extends SlabBlock {
    public String name = String.format("%s_slab", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_slab"));
            put("_top", generateModel("block/tree_slab_top"));
          }
        };
    public String itemModel = generateModel("item/tree_slab");

    public Slab() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SLAB));
    }
  }

  protected class Stairs extends StairsBlock {
    public String name = String.format("%s_stairs", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_stairs"));
            put("_inner", generateModel("block/tree_stairs_inner"));
            put("_outer", generateModel("block/tree_stairs_outer"));
          }
        };
    public String itemModel = generateModel("item/tree_stairs");

    public Stairs() {
      super(
          TreeType.this.planks.getDefaultState(),
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_STAIRS));
    }
  }

  protected class StrippedLog extends PillarBlock {
    public String name = String.format("stripped_%s_log", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/stripped_tree_log"));
            put(
                "_horizontal",
                generateModel("block/stripped_tree_log_horizontal"));
          }
        };
    public String itemModel = generateModel("item/stripped_tree_log");

    public StrippedLog() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_LOG));
    }
  }

  protected class StrippedWood extends Block {
    public String name = String.format("stripped_%s_wood", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/stripped_tree_wood"));
          }
        };
    public String itemModel = generateModel("item/stripped_tree_wood");

    public StrippedWood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_WOOD));
    }
  }

  protected class Trapdoor extends TrapdoorBlock {
    public String name = String.format("%s_trapdoor", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_trapdoor"));
            put("_bottom", generateModel("block/tree_trapdoor_bottom"));
            put("_open", generateModel("block/tree_trapdoor_open"));
            put("_top", generateModel("block/tree_trapdoor_top"));
          }
        };
    public String itemModel = generateModel("item/tree_trapdoor");

    public Trapdoor() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_TRAPDOOR));
    }
  }

  protected class Wood extends Block {
    public String name = String.format("%s_wood", TreeType.this.name);

    public Map<String, String> blockModels =
        new HashMap<>() {
          {
            put("", generateModel("block/tree_wood"));
          }
        };
    public String itemModel = generateModel("item/tree_wood");

    public Wood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_WOOD));
    }
  }

  protected class Bark extends Item {
    public String name = String.format("%s_bark", TreeType.this.name);

    public String itemModel = generateModel("item/tree_bark");

    public Bark() {
      super(ItemUtil.DEFAULT_SETTINGS);
    }
  }
}
