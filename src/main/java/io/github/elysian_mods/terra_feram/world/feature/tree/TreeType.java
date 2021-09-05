package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.mixin.AxeItemAccessor;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.util.ItemUtil;
import io.github.elysian_mods.terra_feram.util.LogsToBark;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
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

import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.models.JModel.textures;

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

    planks = new Planks();

    button = new Button();
    door = new Door();
    fence = new Fence();
    fenceGate = new FenceGate();
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
    implement();
  }

  private void implement() {
    TerraFeram.RESOURCE_PACK.addModel(leaves.itemModel, leaves.itemId);
    addBlockModels(leaves.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(leaves.blockState, TerraFeram.identifier(leaves.name));

    TerraFeram.RESOURCE_PACK.addModel(log.itemModel, log.itemId);
    addBlockModels(log.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(log.blockState, TerraFeram.identifier(log.name));

    TerraFeram.RESOURCE_PACK.addModel(sapling.itemModel, sapling.itemId);
    addBlockModels(sapling.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(sapling.blockState, TerraFeram.identifier(sapling.name));

    TerraFeram.RESOURCE_PACK.addModel(planks.itemModel, planks.itemId);
    addBlockModels(planks.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(planks.blockState, TerraFeram.identifier(planks.name));

    TerraFeram.RESOURCE_PACK.addModel(button.itemModel, button.itemId);
    addBlockModels(button.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(button.blockState, TerraFeram.identifier(button.name));

    TerraFeram.RESOURCE_PACK.addModel(door.itemModel, door.itemId);
    addBlockModels(door.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(door.blockState, TerraFeram.identifier(door.name));

    TerraFeram.RESOURCE_PACK.addModel(fence.itemModel, fence.itemId);
    addBlockModels(fence.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(fence.blockState, TerraFeram.identifier(fence.name));

    TerraFeram.RESOURCE_PACK.addModel(fenceGate.itemModel, fenceGate.itemId);
    addBlockModels(fenceGate.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(
        fenceGate.blockState, TerraFeram.identifier(fenceGate.name));

    TerraFeram.RESOURCE_PACK.addModel(pressurePlate.itemModel, pressurePlate.itemId);
    addBlockModels(pressurePlate.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(
        pressurePlate.blockState, TerraFeram.identifier(pressurePlate.name));

    TerraFeram.RESOURCE_PACK.addModel(sign.itemModel, sign.itemId);
    addBlockModels(sign.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(sign.blockState, TerraFeram.identifier(sign.name));

    TerraFeram.RESOURCE_PACK.addModel(slab.itemModel, slab.itemId);
    addBlockModels(slab.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(slab.blockState, TerraFeram.identifier(slab.name));

    TerraFeram.RESOURCE_PACK.addModel(stairs.itemModel, stairs.itemId);
    addBlockModels(stairs.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(stairs.blockState, TerraFeram.identifier(stairs.name));

    TerraFeram.RESOURCE_PACK.addModel(strippedLog.itemModel, strippedLog.itemId);
    addBlockModels(strippedLog.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(
        strippedLog.blockState, TerraFeram.identifier(strippedLog.name));

    TerraFeram.RESOURCE_PACK.addModel(strippedWood.itemModel, strippedWood.itemId);
    addBlockModels(strippedWood.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(
        strippedWood.blockState, TerraFeram.identifier(strippedWood.name));

    TerraFeram.RESOURCE_PACK.addModel(trapdoor.itemModel, trapdoor.itemId);
    addBlockModels(trapdoor.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(
        trapdoor.blockState, TerraFeram.identifier(trapdoor.name));

    TerraFeram.RESOURCE_PACK.addModel(wood.itemModel, wood.itemId);
    addBlockModels(wood.blockModels);
    TerraFeram.RESOURCE_PACK.addBlockState(wood.blockState, TerraFeram.identifier(wood.name));

    TerraFeram.RESOURCE_PACK.addModel(bark.itemModel, bark.itemId);
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

    registerBlock(planks, planks.name);
    FlammableBlockRegistry.getDefaultInstance().add(planks, 20, 5);

    registerBlock(button, button.name);
    registerBlock(door, door.name);
    registerBlock(fence, fence.name);
    FlammableBlockRegistry.getDefaultInstance().add(fence, 20, 5);
    registerBlock(fenceGate, fenceGate.name);
    FlammableBlockRegistry.getDefaultInstance().add(fenceGate, 20, 5);
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

  public void addBlockModels(Map<Identifier, JModel> blockModels) {
    for (Map.Entry<Identifier, JModel> model : blockModels.entrySet()) {
      TerraFeram.RESOURCE_PACK.addModel(model.getValue(), model.getKey());
    }
  }

  @SafeVarargs
  private <K, V> Map<K, V> mapBuilder(Pair<K, V>... entries) {
    Map<K, V> map = new HashMap<>();
    for (Pair<K, V> entry : entries) {
      map.put(entry.getLeft(), entry.getRight());
    }
    return map;
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
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/leaves")
                    .textures(textures().var("all", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState = state(variant().put("", JState.model(blockId)));

    public Leaves() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LEAVES));
    }
  }

  protected class Log extends PillarBlock {
    public String name = String.format("%s_log", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockHorizontalId =
        TerraFeram.identifier(String.format("block/%s_horizontal", name));
    public Identifier blockTopId = TerraFeram.identifier(String.format("block/%s_top", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cube_column")
                    .textures(
                        textures()
                            .var("end", blockTopId.toString())
                            .var("side", blockId.toString()))),
            new Pair<>(
                blockHorizontalId,
                JModel.model("minecraft:block/cube_column_horizontal")
                    .textures(
                        textures()
                            .var("end", blockTopId.toString())
                            .var("side", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("axis", "x", JState.model(blockHorizontalId).x(90).y(90))
                .put("axis", "y", JState.model(blockId))
                .put("axis", "z", JState.model(blockHorizontalId).x(90)));

    public Log() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LOG));
    }
  }

  protected class Sapling extends SaplingBlock {
    public String name = String.format("%s_sapling", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cross")
                    .textures(textures().var("cross", blockId.toString()))));
    public JModel itemModel =
        JModel.model("minecraft:item/generated").textures(textures().layer0(blockId.toString()));

    public JState blockState = JState.state(variant().put("", JState.model(blockId)));

    public Sapling() {
      super(new Generator(), FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }

  protected class Button extends WoodenButtonBlock {
    public String name = String.format("%s_button", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockInventoryId =
        TerraFeram.identifier(String.format("block/%s_inventory", name));
    public Identifier blockPressedId =
        TerraFeram.identifier(String.format("block/%s_pressed", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/button")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockInventoryId,
                JModel.model("minecraft:block/button_inventory")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockPressedId,
                JModel.model("minecraft:block/button_pressed")
                    .textures(textures().var("texture", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockInventoryId);

    public JState blockState =
        state(
            variant()
                .put("face=ceiling,facing=east,powered=false", JState.model(blockId).x(180).y(270))
                .put(
                    "face=ceiling,facing=east,powered=true",
                    JState.model(blockPressedId).x(180).y(270))
                .put("face=ceiling,facing=north,powered=false", JState.model(blockId).x(180).y(180))
                .put(
                    "face=ceiling,facing=north,powered=true",
                    JState.model(blockPressedId).x(180).y(180))
                .put("face=ceiling,facing=south,powered=false", JState.model(blockId).x(180))
                .put("face=ceiling,facing=south,powered=true", JState.model(blockPressedId).x(180))
                .put("face=ceiling,facing=west,powered=false", JState.model(blockId).x(180).y(90))
                .put("face=ceiling,facing=west,powered=true", JState.model(blockPressedId).y(90))
                .put("face=floor,facing=east,powered=false", JState.model(blockId).y(90))
                .put("face=floor,facing=east,powered=true", JState.model(blockPressedId).y(90))
                .put("face=floor,facing=north,powered=false", JState.model(blockId))
                .put("face=floor,facing=north,powered=true", JState.model(blockPressedId))
                .put("face=floor,facing=south,powered=false", JState.model(blockId).y(180))
                .put("face=floor,facing=south,powered=true", JState.model(blockPressedId).y(180))
                .put("face=floor,facing=west,powered=false", JState.model(blockId).y(270))
                .put("face=floor,facing=west,powered=true", JState.model(blockPressedId).y(270))
                .put(
                    "face=wall,facing=east,powered=false",
                    JState.model(blockId).uvlock().x(90).y(90))
                .put(
                    "face=wall,facing=east,powered=true",
                    JState.model(blockPressedId).uvlock().x(90).y(90))
                .put("face=wall,facing=north,powered=false", JState.model(blockId).uvlock().x(90))
                .put(
                    "face=wall,facing=north,powered=true",
                    JState.model(blockPressedId).uvlock().x(90))
                .put(
                    "face=wall,facing=south,powered=false",
                    JState.model(blockId).uvlock().x(90).y(180))
                .put(
                    "face=wall,facing=south,powered=true",
                    JState.model(blockPressedId).uvlock().x(90).y(180))
                .put(
                    "face=wall,facing=west,powered=false",
                    JState.model(blockId).uvlock().x(90).y(270))
                .put(
                    "face=wall,facing=west,powered=true",
                    JState.model(blockPressedId).uvlock().x(90).y(270)));

    public Button() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_BUTTON));
    }
  }

  protected class Door extends DoorBlock {
    public String name = String.format("%s_door", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockBottomId = TerraFeram.identifier(String.format("%s_bottom", name));
    public Identifier blockBottomHingeId =
        TerraFeram.identifier(String.format("%s_bottom_hinge", name));
    public Identifier blockTopId = TerraFeram.identifier(String.format("%s_top", name));
    public Identifier blockTopHingeId = TerraFeram.identifier(String.format("%s_top_hinge", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockBottomId,
                JModel.model("minecraft:block/door_bottom")
                    .textures(
                        textures()
                            .var("top", blockTopId.toString())
                            .var("bottom", blockBottomId.toString()))),
            new Pair<>(
                blockBottomHingeId,
                JModel.model("minecraft:block/door_bottom_rh")
                    .textures(
                        textures()
                            .var("top", blockTopId.toString())
                            .var("bottom", blockBottomId.toString()))),
            new Pair<>(
                blockTopId,
                JModel.model("minecraft:block/door_top")
                    .textures(
                        textures()
                            .var("top", blockTopId.toString())
                            .var("bottom", blockBottomId.toString()))),
            new Pair<>(
                blockTopHingeId,
                JModel.model("minecraft:block/door_top_rh")
                    .textures(
                        textures()
                            .var("top", blockTopId.toString())
                            .var("bottom", blockBottomId.toString()))));
    public JModel itemModel =
        JModel.model("minecraft:item/generated").textures(textures().layer0(itemId.toString()));

    public JState blockState =
        state(
            variant()
                .put("facing=east,half=lower,hinge=left,open=false", JState.model(blockBottomId))
                .put(
                    "facing=east,half=lower,hinge=left,open=true",
                    JState.model(blockBottomHingeId).y(90))
                .put(
                    "facing=east,half=lower,hinge=right,open=false",
                    JState.model(blockBottomHingeId))
                .put(
                    "facing=east,half=lower,hinge=right,open=true",
                    JState.model(blockBottomId).y(270))
                .put("facing=east,half=upper,hinge=left,open=false", JState.model(blockTopId))
                .put(
                    "facing=east,half=upper,hinge=left,open=true",
                    JState.model(blockTopHingeId).y(90))
                .put("facing=east,half=upper,hinge=right,open=false", JState.model(blockTopHingeId))
                .put(
                    "facing=east,half=upper,hinge=right,open=true", JState.model(blockTopId).y(270))
                .put(
                    "facing=north,half=lower,hinge=left,open=false",
                    JState.model(blockBottomId).y(270))
                .put(
                    "facing=north,half=lower,hinge=left,open=true",
                    JState.model(blockBottomHingeId))
                .put(
                    "facing=north,half=lower,hinge=right,open=false",
                    JState.model(blockBottomHingeId).y(270))
                .put(
                    "facing=north,half=lower,hinge=right,open=true",
                    JState.model(blockBottomId).y(180))
                .put(
                    "facing=north,half=upper,hinge=left,open=false",
                    JState.model(blockTopId).y(270))
                .put("facing=north,half=upper,hinge=left,open=true", JState.model(blockTopHingeId))
                .put(
                    "facing=north,half=upper,hinge=right,open=false",
                    JState.model(blockTopHingeId).y(270))
                .put(
                    "facing=north,half=upper,hinge=right,open=true",
                    JState.model(blockTopId).y(180))
                .put(
                    "facing=south,half=lower,hinge=left,open=false",
                    JState.model(blockBottomId).y(90))
                .put(
                    "facing=south,half=lower,hinge=left,open=true",
                    JState.model(blockBottomHingeId).y(180))
                .put(
                    "facing=south,half=lower,hinge=right,open=false",
                    JState.model(blockBottomHingeId).y(90))
                .put("facing=south,half=lower,hinge=right,open=true", JState.model(blockBottomId))
                .put(
                    "facing=south,half=upper,hinge=left,open=false", JState.model(blockTopId).y(90))
                .put(
                    "facing=south,half=upper,hinge=left,open=true",
                    JState.model(blockTopHingeId).y(180))
                .put(
                    "facing=south,half=upper,hinge=right,open=false",
                    JState.model(blockTopHingeId).y(90))
                .put("facing=south,half=upper,hinge=right,open=true", JState.model(blockTopId))
                .put(
                    "facing=west,half=lower,hinge=left,open=false",
                    JState.model(blockBottomId).y(180))
                .put(
                    "facing=west,half=lower,hinge=left,open=true",
                    JState.model(blockBottomHingeId).y(270))
                .put(
                    "facing=west,half=lower,hinge=right,open=false",
                    JState.model(blockBottomHingeId).y(180))
                .put(
                    "facing=west,half=lower,hinge=right,open=true",
                    JState.model(blockBottomId).y(90))
                .put(
                    "facing=west,half=upper,hinge=left,open=false", JState.model(blockTopId).y(180))
                .put(
                    "facing=west,half=upper,hinge=left,open=true",
                    JState.model(blockTopHingeId).y(270))
                .put(
                    "facing=west,half=upper,hinge=right,open=false",
                    JState.model(blockTopHingeId).y(180))
                .put(
                    "facing=west,half=upper,hinge=right,open=true",
                    JState.model(blockTopId).y(90)));

    public Door() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_DOOR));
    }
  }

  protected class Fence extends FenceBlock {
    public String name = String.format("%s_fence", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockInventoryId =
        TerraFeram.identifier(String.format("block/%s_inventory", name));
    public Identifier blockPostId = TerraFeram.identifier(String.format("block/%s_post", name));
    public Identifier blockSideId = TerraFeram.identifier(String.format("block/%s_side", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockInventoryId,
                JModel.model("minecraft:block/fence_inventory")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockPostId,
                JModel.model("minecraft:block/fence_post")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockSideId,
                JModel.model("minecraft:block/fence_side")
                    .textures(textures().var("texture", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockInventoryId);

    public JState blockState =
        state(
            multipart(model(blockPostId)),
            multipart(model(blockSideId).uvlock()).when(when().add("north", "true")),
            multipart(model(blockSideId).uvlock().y(90)).when(when().add("east", "true")),
            multipart(model(blockSideId).uvlock().y(180)).when(when().add("south", "true")),
            multipart(model(blockSideId).uvlock().y(270)).when(when().add("west", "true")));

    public Fence() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE));
    }
  }

  protected class FenceGate extends FenceGateBlock {
    public String name = String.format("%s_fence_gate", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockOpenId = TerraFeram.identifier(String.format("%s_open", name));
    public Identifier blockWallId = TerraFeram.identifier(String.format("%s_wall", name));
    public Identifier blockWallOpenId = TerraFeram.identifier(String.format("%s_wall_open", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/template_fence_gate")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockOpenId,
                JModel.model("minecraft:block/template_fence_gate_open")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockWallId,
                JModel.model("minecraft:block/template_fence_gate_wall")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockWallOpenId,
                JModel.model("minecraft:block/template_fence_gate_wall_open")
                    .textures(textures().var("texture", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("facing=east,in_wall=false,open=false", model(blockId).uvlock().y(270))
                .put("facing=east,in_wall=false,open=true", model(blockOpenId).uvlock().y(270))
                .put("facing=east,in_wall=true,open=false", model(blockWallId).uvlock().y(270))
                .put("facing=east,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(270))
                .put("facing=north,in_wall=false,open=false", model(blockId).uvlock().y(180))
                .put("facing=north,in_wall=false,open=true", model(blockOpenId).uvlock().y(180))
                .put("facing=north,in_wall=true,open=false", model(blockWallId).uvlock().y(180))
                .put("facing=north,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(180))
                .put("facing=south,in_wall=false,open=false", model(blockId).uvlock())
                .put("facing=south,in_wall=false,open=true", model(blockOpenId).uvlock())
                .put("facing=south,in_wall=true,open=false", model(blockWallId).uvlock())
                .put("facing=south,in_wall=true,open=true", model(blockWallOpenId).uvlock())
                .put("facing=west,in_wall=false,open=false", model(blockId).uvlock().y(90))
                .put("facing=west,in_wall=false,open=true", model(blockOpenId).uvlock().y(90))
                .put("facing=west,in_wall=true,open=false", model(blockWallId).uvlock().y(90))
                .put("facing=west,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(90)));

    public FenceGate() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE_GATE));
    }
  }

  protected class Planks extends Block {
    public String name = String.format("%s_planks", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cube_all")
                    .textures(textures().var("all", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState = state(variant().put("", JState.model(blockId)));

    public Planks() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PLANKS));
    }
  }

  protected class PressurePlate extends PressurePlateBlock {
    public String name = String.format("%s_pressure_plate", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockDownId = TerraFeram.identifier(String.format("block/%s_down", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/pressure_plate_up")
                    .textures(textures().var("texture", planks.blockId.toString()))),
            new Pair<>(
                blockDownId,
                JModel.model("minecraft:block/pressure_plate_down")
                    .textures(textures().var("texture", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("powered", "false", JState.model(blockId))
                .put("powered", "true", JState.model(blockDownId)));

    public PressurePlate() {
      super(
          ActivationRule.EVERYTHING,
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PRESSURE_PLATE));
    }
  }

  protected class Sign extends SignBlock {
    public String name = String.format("%s_sign", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model().textures(textures().var("particle", planks.blockId.toString()))));
    public JModel itemModel =
        JModel.model("minecraft:item/generated").textures(textures().layer0(itemId.toString()));

    public JState blockState = state(variant().put("", JState.model(blockId)));

    public Sign() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SIGN), TreeType.this.signColor);
    }
  }

  protected class Slab extends SlabBlock {
    public String name = String.format("%s_slab", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockTopId = TerraFeram.identifier(String.format("block/%s_top", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/slab")
                    .textures(
                        textures()
                            .var("bottom", planks.blockId.toString())
                            .var("top", planks.blockId.toString())
                            .var("side", planks.blockId.toString()))),
            new Pair<>(
                blockTopId,
                JModel.model("minecraft:block/slab_top")
                    .textures(
                        textures()
                            .var("bottom", planks.blockId.toString())
                            .var("top", planks.blockId.toString())
                            .var("side", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("type", "bottom", JState.model(blockId))
                .put("type", "double", JState.model(planks.blockId))
                .put("type", "top", JState.model(blockTopId)));

    public Slab() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SLAB));
    }
  }

  protected class Stairs extends StairsBlock {
    public String name = String.format("%s_stairs", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockInnerId = TerraFeram.identifier(String.format("block/%s_inner", name));
    public Identifier blockOuterId = TerraFeram.identifier(String.format("block/%s_outer", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/stairs")
                    .textures(
                        textures()
                            .var("bottom", planks.blockId.toString())
                            .var("top", planks.blockId.toString())
                            .var("side", planks.blockId.toString()))),
            new Pair<>(
                blockInnerId,
                JModel.model("minecraft:block/inner_stairs")
                    .textures(
                        textures()
                            .var("bottom", planks.blockId.toString())
                            .var("top", planks.blockId.toString())
                            .var("side", planks.blockId.toString()))),
            new Pair<>(
                blockOuterId,
                JModel.model("minecraft:block/outer_stairs")
                    .textures(
                        textures()
                            .var("bottom", planks.blockId.toString())
                            .var("top", planks.blockId.toString())
                            .var("side", planks.blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put(
                    "facing=east,half=bottom,shape=inner_left", model(blockInnerId).uvlock().y(270))
                .put("facing=east,half=bottom,shape=inner_right", model(blockInnerId))
                .put(
                    "facing=east,half=bottom,shape=outer_left", model(blockOuterId).uvlock().y(270))
                .put("facing=east,half=bottom,shape=outer_right", model(blockOuterId))
                .put("facing=east,half=bottom,shape=straight", model(blockId))
                .put("facing=east,half=top,shape=inner_left", model(blockInnerId).uvlock().x(180))
                .put(
                    "facing=east,half=top,shape=inner_right",
                    model(blockInnerId).uvlock().x(180).y(90))
                .put("facing=east,half=top,shape=outer_left", model(blockOuterId).uvlock().x(180))
                .put(
                    "facing=east,half=top,shape=outer_right",
                    model(blockOuterId).uvlock().x(180).y(90))
                .put("facing=east,half=top,shape=straight", model(blockId).uvlock().x(180))
                .put(
                    "facing=north,half=bottom,shape=inner_left",
                    model(blockInnerId).uvlock().y(180))
                .put(
                    "facing=north,half=bottom,shape=inner_right",
                    model(blockInnerId).uvlock().y(270))
                .put(
                    "facing=north,half=bottom,shape=outer_left",
                    model(blockOuterId).uvlock().y(180))
                .put(
                    "facing=north,half=bottom,shape=outer_right",
                    model(blockOuterId).uvlock().y(270))
                .put("facing=north,half=bottom,shape=straight", model(blockId).uvlock().y(270))
                .put(
                    "facing=north,half=top,shape=inner_left",
                    model(blockInnerId).uvlock().x(180).y(270))
                .put("facing=north,half=top,shape=inner_right", model(blockInnerId).uvlock().x(180))
                .put(
                    "facing=north,half=top,shape=outer_left",
                    model(blockOuterId).uvlock().x(180).y(270))
                .put("facing=north,half=top,shape=outer_right", model(blockOuterId).uvlock().x(180))
                .put("facing=north,half=top,shape=straight", model(blockId).uvlock().x(180).y(270))
                .put("facing=south,half=bottom,shape=inner_left", model(blockInnerId))
                .put(
                    "facing=south,half=bottom,shape=inner_right",
                    model(blockInnerId).uvlock().y(90))
                .put("facing=south,half=bottom,shape=outer_left", model(blockOuterId))
                .put(
                    "facing=south,half=bottom,shape=outer_right",
                    model(blockOuterId).uvlock().y(90))
                .put("facing=south,half=bottom,shape=straight", model(blockId).uvlock().y(90))
                .put(
                    "facing=south,half=top,shape=inner_left",
                    model(blockInnerId).uvlock().x(180).y(90))
                .put(
                    "facing=south,half=top,shape=inner_right",
                    model(blockInnerId).uvlock().x(180).y(180))
                .put(
                    "facing=south,half=top,shape=outer_left",
                    model(blockOuterId).uvlock().x(180).y(90))
                .put(
                    "facing=south,half=top,shape=outer_right",
                    model(blockOuterId).uvlock().x(180).y(180))
                .put("facing=south,half=top,shape=straight", model(blockId).uvlock().x(180).y(90))
                .put("facing=west,half=bottom,shape=inner_left", model(blockInnerId).uvlock().y(90))
                .put(
                    "facing=west,half=bottom,shape=inner_right",
                    model(blockInnerId).uvlock().y(180))
                .put("facing=west,half=bottom,shape=outer_left", model(blockOuterId).uvlock().y(90))
                .put(
                    "facing=west,half=bottom,shape=outer_right",
                    model(blockOuterId).uvlock().y(180))
                .put("facing=west,half=bottom,shape=straight", model(blockId).uvlock().y(180))
                .put(
                    "facing=west,half=top,shape=inner_left",
                    model(blockInnerId).uvlock().x(180).y(180))
                .put(
                    "facing=west,half=top,shape=inner_right",
                    model(blockInnerId).uvlock().x(180).y(270))
                .put(
                    "facing=west,half=top,shape=outer_left",
                    model(blockOuterId).uvlock().x(180).y(180))
                .put(
                    "facing=west,half=top,shape=outer_right",
                    model(blockOuterId).uvlock().x(180).y(270))
                .put("facing=west,half=top,shape=straight", model(blockId).uvlock().x(180).y(180)));

    public Stairs() {
      super(
          TreeType.this.planks.getDefaultState(),
          FabricBlockSettings.copyOf(RegisteredBlocks.OAK_STAIRS));
    }
  }

  protected class StrippedLog extends PillarBlock {
    public String name = String.format("stripped_%s_log", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockHorizontalId =
        TerraFeram.identifier(String.format("block/%s_horizontal", name));
    public Identifier blockTopId = TerraFeram.identifier(String.format("block/%s_top", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cube_column")
                    .textures(
                        textures()
                            .var("end", blockTopId.toString())
                            .var("side", blockId.toString()))),
            new Pair<>(
                blockHorizontalId,
                JModel.model("minecraft:block/cube_column_horizontal")
                    .textures(
                        textures()
                            .var("end", blockTopId.toString())
                            .var("side", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("axis", "x", JState.model(blockHorizontalId).x(90).y(90))
                .put("axis", "y", JState.model(blockId))
                .put("axis", "z", JState.model(blockHorizontalId).x(90)));

    public StrippedLog() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_LOG));
    }
  }

  protected class StrippedWood extends Block {
    public String name = String.format("stripped_%s_wood", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cube_column")
                    .textures(
                        textures()
                            .var("end", blockId.toString())
                            .var("side", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("axis=x", JState.model(blockId).x(90).y(90))
                .put("axis=y", JState.model(blockId))
                .put("axis=z", JState.model(blockId).x(90)));

    public StrippedWood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_WOOD));
    }
  }

  protected class Trapdoor extends TrapdoorBlock {
    public String name = String.format("%s_trapdoor", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Identifier blockBottomId = TerraFeram.identifier(String.format("block/%s_bottom", name));
    public Identifier blockOpenId = TerraFeram.identifier(String.format("block/%s_open", name));
    public Identifier blockTopId = TerraFeram.identifier(String.format("block/%s_top", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockBottomId,
                JModel.model("minecraft:block/template_orientable_trapdoor_bottom")
                    .textures(textures().var("texture", blockId.toString()))),
            new Pair<>(
                blockOpenId,
                JModel.model("minecraft:block/template_orientable_trapdoor_open")
                    .textures(textures().var("texture", blockId.toString()))),
            new Pair<>(
                blockTopId,
                JModel.model("minecraft:block/template_orientable_trapdoor_top")
                    .textures(textures().var("texture", blockId.toString()))));
    public JModel itemModel = JModel.model(blockBottomId);

    public JState blockState =
        state(
            variant()
                .put("facing=east,half=bottom,open=false", model(blockBottomId).y(90))
                .put("facing=east,half=bottom,open=true", model(blockOpenId).y(90))
                .put("facing=east,half=top,open=false", model(blockTopId).y(90))
                .put("facing=east,half=top,open=true", model(blockOpenId).x(180).y(270))
                .put("facing=north,half=bottom,open=false", model(blockBottomId))
                .put("facing=north,half=bottom,open=true", model(blockOpenId))
                .put("facing=north,half=top,open=false", model(blockTopId))
                .put("facing=north,half=top,open=true", model(blockOpenId).x(180).y(180))
                .put("facing=south,half=bottom,open=false", model(blockBottomId).y(180))
                .put("facing=south,half=bottom,open=true", model(blockOpenId).y(180))
                .put("facing=south,half=top,open=false", model(blockTopId).y(180))
                .put("facing=south,half=top,open=true", model(blockOpenId).x(180).y(0))
                .put("facing=west,half=bottom,open=false", model(blockBottomId).y(270))
                .put("facing=west,half=bottom,open=true", model(blockOpenId).y(270))
                .put("facing=west,half=top,open=false", model(blockTopId).y(270))
                .put("facing=west,half=top,open=true", model(blockOpenId).x(180).y(90)));

    public Trapdoor() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_TRAPDOOR));
    }
  }

  protected class Wood extends Block {
    public String name = String.format("%s_wood", TreeType.this.name);
    public Identifier blockId = TerraFeram.identifier(String.format("block/%s", name));
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public Map<Identifier, JModel> blockModels =
        mapBuilder(
            new Pair<>(
                blockId,
                JModel.model("minecraft:block/cube_column")
                    .textures(
                        textures()
                            .var("end", blockId.toString())
                            .var("side", blockId.toString()))));
    public JModel itemModel = JModel.model(blockId);

    public JState blockState =
        state(
            variant()
                .put("axis=x", JState.model(blockId).x(90).y(90))
                .put("axis=y", JState.model(blockId))
                .put("axis=z", JState.model(blockId).x(90)));

    public Wood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_WOOD));
    }
  }

  protected class Bark extends Item {
    public String name = String.format("%s_bark", TreeType.this.name);
    public Identifier itemId = TerraFeram.identifier(String.format("item/%s", name));

    public JModel itemModel =
        JModel.model("minecraft:item/generated").textures(textures().layer0(itemId.toString()));

    public Bark() {
      super(ItemUtil.DEFAULT_SETTINGS);
    }
  }
}
