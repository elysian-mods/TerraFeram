package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.TerraFeram;
import io.github.elysian_mods.terra_feram.mixin.AxeItemAccessor;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
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

import static io.github.elysian_mods.terra_feram.util.ARRPUtil.*;
import static io.github.elysian_mods.terra_feram.util.ItemUtil.DEFAULT_SETTINGS;
import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.recipe.JIngredient.ingredient;
import static net.devtech.arrp.json.recipe.JIngredients.ingredients;
import static net.devtech.arrp.json.recipe.JKeys.keys;
import static net.devtech.arrp.json.recipe.JPattern.pattern;
import static net.devtech.arrp.json.recipe.JRecipe.shaped;
import static net.devtech.arrp.json.recipe.JRecipe.shapeless;
import static net.devtech.arrp.json.recipe.JResult.stackedResult;

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
    addModel(leaves.itemId, leaves.itemModel);
    addBlockModels(leaves.blockModels);
    addBlockState(leaves.nameId, leaves.blockState);

    addModel(log.itemId, log.itemModel);
    addBlockModels(log.blockModels);
    addBlockState(log.nameId, log.blockState);

    addModel(sapling.itemId, sapling.itemModel);
    addBlockModels(sapling.blockModels);
    addBlockState(sapling.nameId, sapling.blockState);

    addModel(planks.itemId, planks.itemModel);
    addBlockModels(planks.blockModels);
    addBlockState(planks.nameId, planks.blockState);

    addModel(button.itemId, button.itemModel);
    addBlockModels(button.blockModels);
    addBlockState(button.nameId, button.blockState);

    addModel(door.itemId, door.itemModel);
    addBlockModels(door.blockModels);
    addBlockState(door.nameId, door.blockState);

    addModel(fence.itemId, fence.itemModel);
    addBlockModels(fence.blockModels);
    addBlockState(fence.nameId, fence.blockState);

    addModel(fenceGate.itemId, fenceGate.itemModel);
    addBlockModels(fenceGate.blockModels);
    addBlockState(fenceGate.nameId, fenceGate.blockState);

    addModel(pressurePlate.itemId, pressurePlate.itemModel);
    addBlockModels(pressurePlate.blockModels);
    addBlockState(pressurePlate.nameId, pressurePlate.blockState);

    addModel(sign.itemId, sign.itemModel);
    addBlockModels(sign.blockModels);
    addBlockState(sign.nameId, sign.blockState);

    addModel(slab.itemId, slab.itemModel);
    addBlockModels(slab.blockModels);
    addBlockState(slab.nameId, slab.blockState);

    addModel(stairs.itemId, stairs.itemModel);
    addBlockModels(stairs.blockModels);
    addBlockState(stairs.nameId, stairs.blockState);

    addModel(strippedLog.itemId, strippedLog.itemModel);
    addBlockModels(strippedLog.blockModels);
    addBlockState(strippedLog.nameId, strippedLog.blockState);

    addModel(strippedWood.itemId, strippedWood.itemModel);
    addBlockModels(strippedWood.blockModels);
    addBlockState(strippedWood.nameId, strippedWood.blockState);

    addModel(trapdoor.itemId, trapdoor.itemModel);
    addBlockModels(trapdoor.blockModels);
    addBlockState(trapdoor.nameId, trapdoor.blockState);

    addModel(wood.itemId, wood.itemModel);
    addBlockModels(wood.blockModels);
    addBlockState(wood.nameId, wood.blockState);

    addModel(bark.itemId, bark.itemModel);
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
    addRecipe(
        log.nameId,
        shaped(
            pattern(" _ ", "_#_", " _ "),
            keys()
                .key("#", ingredient().item(strippedLog.nameId.toString()))
                .key("_", ingredient().item(bark.nameId.toString())),
            stackedResult(log.nameId.toString(), 1)));

    registerBlock(sapling, sapling.name);

    registerBlock(planks, planks.name);
    FlammableBlockRegistry.getDefaultInstance().add(planks, 20, 5);
    addDualTag(new Identifier("wooden_planks"), new String[] {planks.name});
    addRecipe(
        genericId("%s_planks_from_bark", name),
        shaped(
            pattern("###", "###", "###"),
            keys().key("#", ingredient().item(bark.nameId.toString())),
            stackedResult(planks.nameId.toString(), 3)));

    registerBlock(button, button.name);
    addDualTag(new Identifier("wooden_buttons"), new String[] {button.name});
    addRecipe(
        button.nameId,
        shapeless(
            ingredients().add(ingredient().add(ingredient().item(planks.nameId.toString()))),
            stackedResult(button.nameId.toString(), 1)));

    registerBlock(door, door.name);
    addDualTag(new Identifier("wooden_doors"), new String[] {door.name});
    addRecipe(
        door.nameId,
        shaped(
            pattern("##", "##", "##"),
            keys().key("#", ingredient().item(planks.nameId.toString())),
            stackedResult(door.nameId.toString(), 3)));

    registerBlock(fence, fence.name);
    FlammableBlockRegistry.getDefaultInstance().add(fence, 20, 5);
    addDualTag(new Identifier("wooden_fences"), new String[] {fence.name});
    addRecipe(
        fence.nameId,
        shaped(
            pattern("#|#", "#|#"),
            keys()
                .key("#", ingredient().item(planks.nameId.toString()))
                .key("|", ingredient().item(RegisteredItems.STICK)),
            stackedResult(fence.nameId.toString(), 3)));

    registerBlock(fenceGate, fenceGate.name);
    FlammableBlockRegistry.getDefaultInstance().add(fenceGate, 20, 5);
    addRecipe(
        fenceGate.nameId,
        shaped(
            pattern("|#|", "|#|"),
            keys()
                .key("#", ingredient().item(planks.nameId.toString()))
                .key("|", ingredient().item(RegisteredItems.STICK)),
            stackedResult(fenceGate.nameId.toString(), 1)));

    registerBlock(pressurePlate, pressurePlate.name);
    addDualTag(new Identifier("wooden_pressure_plates"), new String[] {pressurePlate.name});
    addRecipe(
        pressurePlate.nameId,
        shaped(
            pattern("##"),
            keys().key("#", ingredient().item(planks.nameId.toString())),
            stackedResult(pressurePlate.nameId.toString(), 1)));

    registerBlock(sign, sign.name);
    addRecipe(
        sign.nameId,
        shaped(
            pattern("###", "###", " | "),
            keys()
                .key("#", ingredient().item(planks.nameId.toString()))
                .key("|", ingredient().item(RegisteredItems.STICK)),
            stackedResult(sign.nameId.toString(), 3)));

    registerBlock(slab, slab.name);
    FlammableBlockRegistry.getDefaultInstance().add(slab, 20, 5);
    addDualTag(new Identifier("wooden_slabs"), new String[] {slab.name});
    addRecipe(
        slab.nameId,
        shaped(
            pattern("###"),
            keys().key("#", ingredient().item(planks.nameId.toString())),
            stackedResult(slab.nameId.toString(), 6)));

    registerBlock(stairs, stairs.name);
    FlammableBlockRegistry.getDefaultInstance().add(stairs, 20, 5);
    addDualTag(new Identifier("wooden_stairs"), new String[] {stairs.name});
    addRecipe(
        stairs.nameId,
        shaped(
            pattern("#  ", "## ", "###"),
            keys().key("#", ingredient().item(planks.nameId.toString())),
            stackedResult(stairs.nameId.toString(), 4)));

    registerBlock(trapdoor, trapdoor.name);
    addDualTag(new Identifier("wooden_trapdoor"), new String[] {trapdoor.name});
    addRecipe(
        trapdoor.nameId,
        shaped(
            pattern("###", "###"),
            keys().key("#", ingredient().item(planks.nameId.toString())),
            stackedResult(trapdoor.nameId.toString(), 2)));

    registerBlock(wood, wood.name);
    FlammableBlockRegistry.getDefaultInstance().add(wood, 5, 5);
    addRecipe(
        wood.nameId,
        shaped(
            pattern("_", "#", "_"),
            keys()
                .key("#", ingredient().item(log.nameId.toString()))
                .key("_", ingredient().item(bark.nameId.toString())),
            stackedResult(wood.nameId.toString(), 1)));
    addRecipe(
        genericId("%s_wood_from_bark", name),
        shaped(
            pattern("___", " # ", "___"),
            keys()
                .key("#", ingredient().item(strippedWood.nameId.toString()))
                .key("_", ingredient().item(bark.nameId.toString())),
            stackedResult(wood.nameId.toString(), 1)));

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

    String[] logs = {log.name, wood.name, strippedLog.name, strippedWood.name};
    Identifier logsId = TerraFeram.identifier(String.format("%s_logs", name));
    addDualTag(logsId, logs);
    addTag(new Identifier("logs_that_burn"), new String[] {"#" + logsId.toString()});

    addRecipe(
        planks.nameId,
        shapeless(
            ingredients().add(ingredient().tag(logsId.toString())),
            stackedResult(planks.nameId.toString(), 1)));

    return configured;
  }

  private void registerBlock(Block block, String name) {
    if (block != null) {
      Registry.register(Registry.BLOCK, TerraFeram.identifier(name), block);
      Registry.register(
          Registry.ITEM, TerraFeram.identifier(name), new BlockItem(block, DEFAULT_SETTINGS));
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
    public final String name = String.format("%s_leaves", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("all", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "leaves", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState = state(variant().put("", JState.model(blockId)));

    public Leaves() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LEAVES));
    }
  }

  protected class Log extends PillarBlock {
    public final String name = String.format("%s_log", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockHorizontalId = genericId("block/%s_horizontal", name);
    public final Identifier blockTopId = genericId("block/%s_top", name);

    private final Map<String, Identifier> vars =
        mapBuilder(new Pair<>("end", blockTopId), new Pair<>("side", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "cube_column", vars),
            modelBuilder(blockHorizontalId, "cube_column_horizontal", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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
    public final String name = String.format("%s_sapling", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("cross", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "cross", vars));
    public final JModel itemModel = generatedItem(blockId);

    public final JState blockState = JState.state(variant().put("", JState.model(blockId)));

    public Sapling() {
      super(new Generator(), FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
    }
  }

  protected class Planks extends Block {
    public final String name = String.format("%s_planks", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("all", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "cube_all", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState = state(variant().put("", JState.model(blockId)));

    public Planks() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PLANKS));
    }
  }

  protected class Button extends WoodenButtonBlock {
    public final String name = String.format("%s_button", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockInventoryId = genericId("block/%s_inventory", name);
    public final Identifier blockPressedId = genericId("block/%s_pressed", name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("texture", planks.blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "button", vars),
            modelBuilder(blockInventoryId, "button_inventory", vars),
            modelBuilder(blockPressedId, "button_pressed", vars));
    public final JModel itemModel = JModel.model(blockInventoryId);

    public final JState blockState =
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
    public final String name = String.format("%s_door", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockBottomId = genericId("%s_bottom", name);
    public final Identifier blockBottomHingeId = genericId("%s_bottom_hinge", name);
    public final Identifier blockTopId = genericId("%s_top", name);
    public final Identifier blockTopHingeId = genericId("%s_top_hinge", name);

    private final Map<String, Identifier> vars =
        mapBuilder(new Pair<>("top", blockTopId), new Pair<>("bottom", blockBottomId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockBottomId, "door_bottom", vars),
            modelBuilder(blockBottomHingeId, "door_bottom_rh", vars),
            modelBuilder(blockTopId, "door_top", vars),
            modelBuilder(blockTopHingeId, "door_top_rh", vars));
    public final JModel itemModel = generatedItem(itemId);

    public final JState blockState =
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
    public final String name = String.format("%s_fence", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockInventoryId = genericId("block/%s_inventory", name);
    public final Identifier blockPostId = genericId("block/%s_post", name);
    public final Identifier blockSideId = genericId("block/%s_side", name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("texture", planks.blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockInventoryId, "fence_inventory", vars),
            modelBuilder(blockPostId, "fence_post", vars),
            modelBuilder(blockSideId, "fence_side", vars));
    public final JModel itemModel = JModel.model(blockInventoryId);

    public final JState blockState =
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
    public final String name = String.format("%s_fence_gate", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockOpenId = genericId("%s_open", name);
    public final Identifier blockWallId = genericId("%s_wall", name);
    public final Identifier blockWallOpenId = genericId("%s_wall_open", name);

    private final Map<String, Identifier> vars = texture(planks.blockId);
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "template_fence_gate", vars),
            modelBuilder(blockOpenId, "template_fence_gate_open", vars),
            modelBuilder(blockWallId, "template_fence_gate_wall", vars),
            modelBuilder(blockWallOpenId, "template_fence_gate_wall_open", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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

  protected class PressurePlate extends PressurePlateBlock {
    public final String name = String.format("%s_pressure_plate", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockDownId = genericId("block/%s_down", name);

    private final Map<String, Identifier> vars = texture(planks.blockId);
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "pressure_plate_up", vars),
            modelBuilder(blockDownId, "pressure_plate_down", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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
    public final String name = String.format("%s_sign", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars = mapBuilder(new Pair<>("particle", planks.blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, null, vars));
    public final JModel itemModel = generatedItem(itemId);

    public final JState blockState = state(variant().put("", JState.model(blockId)));

    public Sign() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SIGN), TreeType.this.signColor);
    }
  }

  protected class Slab extends SlabBlock {
    public final String name = String.format("%s_slab", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockTopId = genericId("block/%s_top", name);

    private final Map<String, Identifier> vars =
        mapBuilder(
            new Pair<>("bottom", planks.blockId),
            new Pair<>("top", planks.blockId),
            new Pair<>("side", planks.blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "slab", vars), modelBuilder(blockTopId, "slab_top", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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
    public final String name = String.format("%s_stairs", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockInnerId = genericId("block/%s_inner", name);
    public final Identifier blockOuterId = genericId("block/%s_outer", name);

    private final Map<String, Identifier> vars =
        mapBuilder(
            new Pair<>("bottom", planks.blockId),
            new Pair<>("top", planks.blockId),
            new Pair<>("side", planks.blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "stairs", vars),
            modelBuilder(blockInnerId, "inner_stairs", vars),
            modelBuilder(blockOuterId, "outer_stairs", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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
    public final String name = String.format("stripped_%s_log", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockHorizontalId = genericId("block/%s_horizontal", name);
    public final Identifier blockTopId = genericId("block/%s_top", name);

    private final Map<String, Identifier> vars =
        mapBuilder(new Pair<>("end", blockTopId), new Pair<>("side", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockId, "cube_column", vars),
            modelBuilder(blockHorizontalId, "cube_column_horizontal", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
        state(
            variant()
                .put("axis", "x", JState.model(blockHorizontalId).x(90).y(90))
                .put("axis", "y", JState.model(blockId))
                .put("axis", "z", JState.model(blockHorizontalId).x(90)));

    public StrippedLog() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_LOG));
    }
  }

  protected class StrippedWood extends PillarBlock {
    public final String name = String.format("stripped_%s_wood", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars =
        mapBuilder(new Pair<>("end", blockId), new Pair<>("side", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "cube_column", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
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
    public final String name = String.format("%s_trapdoor", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final Identifier blockBottomId = genericId("block/%s_bottom", name);
    public final Identifier blockOpenId = genericId("block/%s_open", name);
    public final Identifier blockTopId = genericId("block/%s_top", name);

    private final Map<String, Identifier> vars = texture(blockId);
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(
            modelBuilder(blockBottomId, "template_orientable_trapdoor_bottom", vars),
            modelBuilder(blockOpenId, "template_orientable_trapdoor_open", vars),
            modelBuilder(blockTopId, "template_orientable_trapdoor_top", vars));
    public final JModel itemModel = JModel.model(blockBottomId);

    public final JState blockState =
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

  protected class Wood extends PillarBlock {
    public final String name = String.format("%s_wood", TreeType.this.name);
    public final Identifier blockId = blockId(name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    private final Map<String, Identifier> vars =
        mapBuilder(new Pair<>("end", blockId), new Pair<>("side", blockId));
    public final Map<Identifier, JModel> blockModels =
        mapBuilder(modelBuilder(blockId, "cube_column", vars));
    public final JModel itemModel = JModel.model(blockId);

    public final JState blockState =
        state(
            variant()
                .put("axis=x", JState.model(log.blockId).x(90).y(90))
                .put("axis=y", JState.model(log.blockId))
                .put("axis=z", JState.model(log.blockId).x(90)));

    public Wood() {
      super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_WOOD));
    }
  }

  protected class Bark extends Item {
    public final String name = String.format("%s_bark", TreeType.this.name);
    public final Identifier itemId = itemId(name);
    public final Identifier nameId = nameId(name);

    public final JModel itemModel = generatedItem(itemId);

    public Bark() {
      super(DEFAULT_SETTINGS);
    }
  }
}
