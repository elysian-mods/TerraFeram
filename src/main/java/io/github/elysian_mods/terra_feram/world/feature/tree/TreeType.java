package io.github.elysian_mods.terra_feram.world.feature.tree;

import io.github.elysian_mods.terra_feram.block.BlockWrapper;
import io.github.elysian_mods.terra_feram.client.RenderType;
import io.github.elysian_mods.terra_feram.item.ItemWrapper;
import io.github.elysian_mods.terra_feram.mixin.AxeItemAccessor;
import io.github.elysian_mods.terra_feram.registry.RegisteredBlocks;
import io.github.elysian_mods.terra_feram.registry.RegisteredItems;
import io.github.elysian_mods.terra_feram.util.LogsToBark;
import io.github.elysian_mods.terra_feram.world.feature.FeatureWrapper;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

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

public abstract class TreeType extends FeatureWrapper<TreeFeatureConfig> {
  public String type;
  public String translation;

  protected SignType signColor = SignType.OAK;

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

  protected Identifier logsId;

  protected TreeType(String name) {
    super(name + "_tree");
    translation = StringUtils.capitalize(name);
    type = name;

    feature = Feature.TREE;

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
    trapdoor = new Trapdoor();
    wood = new Wood();

    strippedLog = new StrippedLog();
    strippedWood = new StrippedWood();

    bark = new Bark();

    logsId = genericId("%s_logs", name);
  }

  public RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> register() {
    leaves.register();
    log.register();
    sapling.register();

    planks.register();
    addDualTag(new Identifier("wooden_planks"), new String[] {planks.name});

    button.register();
    addDualTag(new Identifier("wooden_buttons"), new String[] {button.name});
    door.register();
    addDualTag(new Identifier("wooden_doors"), new String[] {door.name});
    fence.register();
    addDualTag(new Identifier("wooden_fences"), new String[] {fence.name});
    fenceGate.register();
    pressurePlate.register();
    addDualTag(new Identifier("wooden_pressure_plates"), new String[] {pressurePlate.name});
    sign.register();
    slab.register();
    addDualTag(new Identifier("wooden_slabs"), new String[] {slab.name});
    stairs.register();
    addDualTag(new Identifier("wooden_stairs"), new String[] {stairs.name});
    trapdoor.register();
    addDualTag(new Identifier("wooden_trapdoor"), new String[] {trapdoor.name});
    wood.register();

    strippedLog.register();
    strippedWood.register();

    Map<Block, Block> stripper = new HashMap<>(AxeItemAccessor.getStrippedBlocks());
    stripper.put(log.block, strippedLog.block);
    stripper.put(wood.block, strippedWood.block);
    AxeItemAccessor.setStrippedBlocks(stripper);

    bark.register();
    LogsToBark.put(log.block, bark.item, 4);
    LogsToBark.put(wood.block, bark.item, 6);

    String[] logs = {log.name, wood.name, strippedLog.name, strippedWood.name};
    addDualTag(logsId, logs);
    addTag(new Identifier("logs_that_burn"), new String[] {"#" + logsId.getPath()});

    return super.register();
  }

  public class Generator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
      return TreeType.this.configured;
    }
  }

  protected class Leaves extends BlockWrapper {
    protected Leaves() {
      super(String.format("%s_leaves", TreeType.this.type));
      translation = String.format("%s Leaves", TreeType.this.translation);

      block = new BLeaves();

      blockModels = blockModels(Map.of(blockId, "leaves"), Map.of("all", blockId));
      itemModel = itemModel(blockId);

      blockState = state(variant().put("", JState.model(blockId)));

      burn = 60;
      spread = 30;
    }

    @Override
    public Block register() {
      return super.register();
    }

    protected class BLeaves extends LeavesBlock {
      protected BLeaves() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LEAVES));
      }
    }
  }

  protected class Log extends BlockWrapper {
    public Identifier blockHorizontalId = genericId("block/%s_horizontal", name);
    public Identifier blockTopId = genericId("block/%s_top", name);

    protected Log() {
      super(String.format("%s_log", TreeType.this.type));
      translation = String.format("%s Log", TreeType.this.translation);

      block = new BLog();

      blockModels =
          blockModels(
              Map.of(blockId, "cube_column", blockHorizontalId, "cube_column_horizontal"),
              Map.of("end", blockTopId, "side", blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("axis", "x", JState.model(blockHorizontalId).x(90).y(90))
                  .put("axis", "y", JState.model(blockId))
                  .put("axis", "z", JState.model(blockHorizontalId).x(90)));

      burn = 5;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern(" _ ", "_#_", " _ "),
                  keys()
                      .key("#", ingredient().item(strippedLog.nameId.toString()))
                      .key("_", ingredient().item(bark.nameId.toString())),
                  stackedResult(nameId.toString(), 1)));

      return super.register();
    }

    protected class BLog extends PillarBlock {
      protected BLog() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_LOG));
      }
    }
  }

  protected class Sapling extends BlockWrapper {
    protected Sapling() {
      super(String.format("%s_sapling", TreeType.this.type));
      translation = String.format("%s Sapling", TreeType.this.translation);

      block = new BSapling();
      renderType = RenderType.TRANSPARENT;

      blockModels = blockModels(Map.of(blockId, "cross"), Map.of("cross", blockId));
      itemModel = generatedItem(blockId);

      blockState = JState.state(variant().put("", JState.model(blockId)));
    }

    protected class BSapling extends SaplingBlock {
      protected BSapling() {
        super(new Generator(), FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SAPLING));
      }
    }
  }

  protected class Planks extends BlockWrapper {
    protected Planks() {
      super(String.format("%s_planks", TreeType.this.type));
      translation = String.format("%s Planks", TreeType.this.translation);

      block = new BPlanks();

      blockModels = blockModels(Map.of(blockId, "cube_all"), Map.of("all", blockId));
      itemModel = itemModel(blockId);

      blockState = state(variant().put("", JState.model(blockId)));

      burn = 20;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shapeless(
                  ingredients().add(ingredient().tag(logsId.toString())),
                  stackedResult(planks.nameId.toString(), 4)),
              genericId("%s_planks_from_bark", name),
              shaped(
                  pattern("###", "###", "###"),
                  keys().key("#", ingredient().item(bark.nameId.toString())),
                  stackedResult(nameId.toString(), 3)));

      return super.register();
    }

    protected class BPlanks extends Block {
      protected BPlanks() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PLANKS));
      }
    }
  }

  protected class Button extends BlockWrapper {
    public Identifier blockInventoryId = genericId("block/%s_inventory", name);
    public Identifier blockPressedId = genericId("block/%s_pressed", name);

    protected Button() {
      super(String.format("%s_button", TreeType.this.type));
      translation = String.format("%s Button", TreeType.this.translation);

      block = new BButton();

      blockModels =
          blockModels(
              Map.of(
                  blockId,
                  "button",
                  blockInventoryId,
                  "button_inventory",
                  blockPressedId,
                  "button_pressed"),
              texture(planks.blockId));
      itemModel = JModel.model(blockInventoryId);

      blockState =
          state(
              variant()
                  .put(
                      "face=ceiling,facing=east,powered=false", JState.model(blockId).x(180).y(270))
                  .put(
                      "face=ceiling,facing=east,powered=true",
                      JState.model(blockPressedId).x(180).y(270))
                  .put(
                      "face=ceiling,facing=north,powered=false",
                      JState.model(blockId).x(180).y(180))
                  .put(
                      "face=ceiling,facing=north,powered=true",
                      JState.model(blockPressedId).x(180).y(180))
                  .put("face=ceiling,facing=south,powered=false", JState.model(blockId).x(180))
                  .put(
                      "face=ceiling,facing=south,powered=true", JState.model(blockPressedId).x(180))
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
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shapeless(
                  ingredients().add(ingredient().add(ingredient().item(planks.nameId.toString()))),
                  stackedResult(nameId.toString(), 1)));

      return super.register();
    }

    protected class BButton extends WoodenButtonBlock {
      protected BButton() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_BUTTON));
      }
    }
  }

  protected class Door extends BlockWrapper {
    public Identifier blockBottomId = genericId("block/%s_bottom", name);
    public Identifier blockBottomHingeId = genericId("block/%s_bottom_hinge", name);
    public Identifier blockTopId = genericId("block/%s_top", name);
    public Identifier blockTopHingeId = genericId("block/%s_top_hinge", name);

    protected Door() {
      super(String.format("%s_door", TreeType.this.type));
      translation = String.format("%s Door", TreeType.this.translation);

      block = new BDoor();

      blockModels =
          blockModels(
              Map.of(
                  blockBottomId,
                  "door_bottom",
                  blockBottomHingeId,
                  "door_bottom_rh",
                  blockTopId,
                  "door_top",
                  blockTopHingeId,
                  "door_top_rh"),
              Map.of("top", blockTopId, "bottom", blockBottomId));
      itemModel = generatedItem(itemId);

      blockState =
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
                  .put(
                      "facing=east,half=upper,hinge=right,open=false",
                      JState.model(blockTopHingeId))
                  .put(
                      "facing=east,half=upper,hinge=right,open=true",
                      JState.model(blockTopId).y(270))
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
                  .put(
                      "facing=north,half=upper,hinge=left,open=true", JState.model(blockTopHingeId))
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
                      "facing=south,half=upper,hinge=left,open=false",
                      JState.model(blockTopId).y(90))
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
                      "facing=west,half=upper,hinge=left,open=false",
                      JState.model(blockTopId).y(180))
                  .put(
                      "facing=west,half=upper,hinge=left,open=true",
                      JState.model(blockTopHingeId).y(270))
                  .put(
                      "facing=west,half=upper,hinge=right,open=false",
                      JState.model(blockTopHingeId).y(180))
                  .put(
                      "facing=west,half=upper,hinge=right,open=true",
                      JState.model(blockTopId).y(90)));
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("##", "##", "##"),
                  keys().key("#", ingredient().item(planks.nameId.toString())),
                  stackedResult(nameId.toString(), 3)));

      return super.register();
    }

    protected class BDoor extends DoorBlock {
      protected BDoor() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_DOOR));
      }
    }
  }

  protected class Fence extends BlockWrapper {
    public Identifier blockInventoryId = genericId("block/%s_inventory", name);
    public Identifier blockPostId = genericId("block/%s_post", name);
    public Identifier blockSideId = genericId("block/%s_side", name);

    protected Fence() {
      super(String.format("%s_fence", TreeType.this.type));
      translation = String.format("%s Fence", TreeType.this.translation);

      block = new BFence();

      blockModels =
          blockModels(
              Map.of(
                  blockInventoryId,
                  "fence_inventory",
                  blockPostId,
                  "fence_post",
                  blockSideId,
                  "fence_side"),
              Map.of("texture", planks.blockId));
      itemModel = JModel.model(blockInventoryId);

      blockState =
          state(
              multipart(model(blockPostId)),
              multipart(model(blockSideId).uvlock()).when(when().add("north", "true")),
              multipart(model(blockSideId).uvlock().y(90)).when(when().add("east", "true")),
              multipart(model(blockSideId).uvlock().y(180)).when(when().add("south", "true")),
              multipart(model(blockSideId).uvlock().y(270)).when(when().add("west", "true")));

      burn = 20;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("#|#", "#|#"),
                  keys()
                      .key("#", ingredient().item(planks.nameId.toString()))
                      .key("|", ingredient().item(RegisteredItems.STICK)),
                  stackedResult(nameId.toString(), 3)));

      return super.register();
    }

    protected class BFence extends FenceBlock {
      protected BFence() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE));
      }
    }
  }

  protected class FenceGate extends BlockWrapper {
    public Identifier blockOpenId = genericId("%s_open", name);
    public Identifier blockWallId = genericId("%s_wall", name);
    public Identifier blockWallOpenId = genericId("%s_wall_open", name);

    protected FenceGate() {
      super(String.format("%s_fence_gate", TreeType.this.type));
      translation = String.format("%s Fence Gate", TreeType.this.translation);

      block = new BFenceGate();

      blockModels =
          blockModels(
              Map.of(
                  blockId,
                  "template_fence_gate",
                  blockOpenId,
                  "template_fence_gate_open",
                  blockWallId,
                  "template_fence_gate_wall",
                  blockWallOpenId,
                  "template_fence_gate_wall_open"),
              texture(planks.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("facing=east,in_wall=false,open=false", model(blockId).uvlock().y(270))
                  .put("facing=east,in_wall=false,open=true", model(blockOpenId).uvlock().y(270))
                  .put("facing=east,in_wall=true,open=false", model(blockWallId).uvlock().y(270))
                  .put("facing=east,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(270))
                  .put("facing=north,in_wall=false,open=false", model(blockId).uvlock().y(180))
                  .put("facing=north,in_wall=false,open=true", model(blockOpenId).uvlock().y(180))
                  .put("facing=north,in_wall=true,open=false", model(blockWallId).uvlock().y(180))
                  .put(
                      "facing=north,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(180))
                  .put("facing=south,in_wall=false,open=false", model(blockId).uvlock())
                  .put("facing=south,in_wall=false,open=true", model(blockOpenId).uvlock())
                  .put("facing=south,in_wall=true,open=false", model(blockWallId).uvlock())
                  .put("facing=south,in_wall=true,open=true", model(blockWallOpenId).uvlock())
                  .put("facing=west,in_wall=false,open=false", model(blockId).uvlock().y(90))
                  .put("facing=west,in_wall=false,open=true", model(blockOpenId).uvlock().y(90))
                  .put("facing=west,in_wall=true,open=false", model(blockWallId).uvlock().y(90))
                  .put(
                      "facing=west,in_wall=true,open=true", model(blockWallOpenId).uvlock().y(90)));

      burn = 20;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("|#|", "|#|"),
                  keys()
                      .key("#", ingredient().item(planks.nameId.toString()))
                      .key("|", ingredient().item(RegisteredItems.STICK)),
                  stackedResult(nameId.toString(), 1)));

      return super.register();
    }

    protected class BFenceGate extends FenceGateBlock {
      protected BFenceGate() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_FENCE_GATE));
      }
    }
  }

  protected class PressurePlate extends BlockWrapper {
    public Identifier blockDownId = genericId("block/%s_down", name);

    protected PressurePlate() {
      super(String.format("%s_pressure_plate", TreeType.this.type));
      translation = String.format("%s Pressure Plate", TreeType.this.translation);

      block = new BPressurePlate();

      blockModels =
          blockModels(
              Map.of(blockId, "pressure_plate_up", blockDownId, "pressure_plate_down"),
              texture(planks.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("powered", "false", JState.model(blockId))
                  .put("powered", "true", JState.model(blockDownId)));
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("##"),
                  keys().key("#", ingredient().item(planks.nameId.toString())),
                  stackedResult(nameId.toString(), 1)));

      return super.register();
    }

    protected class BPressurePlate extends PressurePlateBlock {
      protected BPressurePlate() {
        super(
            ActivationRule.EVERYTHING,
            FabricBlockSettings.copyOf(RegisteredBlocks.OAK_PRESSURE_PLATE));
      }
    }
  }

  protected class Sign extends BlockWrapper {
    protected Sign() {
      super(String.format("%s_sign", TreeType.this.type));
      translation = String.format("%s Sign", TreeType.this.translation);

      block = new BSign();

      blockModels = blockModels(Map.of(blockId, ""), Map.of("particle", planks.blockId));
      itemModel = generatedItem(itemId);

      blockState = state(variant().put("", JState.model(blockId)));
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("###", "###", " | "),
                  keys()
                      .key("#", ingredient().item(planks.nameId.toString()))
                      .key("|", ingredient().item(RegisteredItems.STICK)),
                  stackedResult(nameId.toString(), 3)));

      return super.register();
    }

    protected class BSign extends SignBlock {
      protected BSign() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SIGN), TreeType.this.signColor);
      }
    }
  }

  protected class Slab extends BlockWrapper {
    public Identifier blockTopId = genericId("block/%s_top", name);

    protected Slab() {
      super(String.format("%s_slab", TreeType.this.type));
      translation = String.format("%s Slab", TreeType.this.translation);

      block = new BSlab();

      blockModels =
          blockModels(
              Map.of(blockId, "slab", blockTopId, "slab_top"),
              Map.of("bottom", planks.blockId, "top", planks.blockId, "side", planks.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("type", "bottom", JState.model(blockId))
                  .put("type", "double", JState.model(planks.blockId))
                  .put("type", "top", JState.model(blockTopId)));

      burn = 20;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("###"),
                  keys().key("#", ingredient().item(planks.nameId.toString())),
                  stackedResult(nameId.toString(), 6)));

      return super.register();
    }

    protected class BSlab extends SlabBlock {
      protected BSlab() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_SLAB));
      }
    }
  }

  protected class Stairs extends BlockWrapper {
    public Identifier blockInnerId = genericId("block/%s_inner", name);
    public Identifier blockOuterId = genericId("block/%s_outer", name);

    protected Stairs() {
      super(String.format("%s_stairs", TreeType.this.type));
      translation = String.format("%s Stairs", TreeType.this.translation);

      block = new BStairs();

      blockModels =
          blockModels(
              Map.of(blockId, "stairs", blockInnerId, "inner_stairs", blockOuterId, "outer_stairs"),
              Map.of("bottom", planks.blockId, "top", planks.blockId, "side", planks.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put(
                      "facing=east,half=bottom,shape=inner_left",
                      model(blockInnerId).uvlock().y(270))
                  .put("facing=east,half=bottom,shape=inner_right", model(blockInnerId))
                  .put(
                      "facing=east,half=bottom,shape=outer_left",
                      model(blockOuterId).uvlock().y(270))
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
                  .put(
                      "facing=north,half=top,shape=inner_right",
                      model(blockInnerId).uvlock().x(180))
                  .put(
                      "facing=north,half=top,shape=outer_left",
                      model(blockOuterId).uvlock().x(180).y(270))
                  .put(
                      "facing=north,half=top,shape=outer_right",
                      model(blockOuterId).uvlock().x(180))
                  .put(
                      "facing=north,half=top,shape=straight", model(blockId).uvlock().x(180).y(270))
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
                  .put(
                      "facing=west,half=bottom,shape=inner_left",
                      model(blockInnerId).uvlock().y(90))
                  .put(
                      "facing=west,half=bottom,shape=inner_right",
                      model(blockInnerId).uvlock().y(180))
                  .put(
                      "facing=west,half=bottom,shape=outer_left",
                      model(blockOuterId).uvlock().y(90))
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
                  .put(
                      "facing=west,half=top,shape=straight",
                      model(blockId).uvlock().x(180).y(180)));

      burn = 20;
      spread = 5;
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("#  ", "## ", "###"),
                  keys().key("#", ingredient().item(planks.nameId.toString())),
                  stackedResult(nameId.toString(), 4)));

      return super.register();
    }

    protected class BStairs extends StairsBlock {
      protected BStairs() {
        super(
            TreeType.this.planks.block.getDefaultState(),
            FabricBlockSettings.copyOf(RegisteredBlocks.OAK_STAIRS));
      }
    }
  }

  protected class Trapdoor extends BlockWrapper {
    public Identifier blockBottomId = genericId("block/%s_bottom", name);
    public Identifier blockOpenId = genericId("block/%s_open", name);
    public Identifier blockTopId = genericId("block/%s_top", name);

    protected Trapdoor() {
      super(String.format("%s_trapdoor", TreeType.this.type));
      translation = String.format("%s Trapdoor", TreeType.this.translation);

      block = new BTrapdoor();

      blockModels =
          blockModels(
              Map.of(
                  blockBottomId,
                  "template_orientable_trapdoor_bottom",
                  blockOpenId,
                  "template_orientable_trapdoor_open",
                  blockTopId,
                  "template_orientable_trapdoor_top"),
              texture(blockId));
      itemModel = JModel.model(blockBottomId);

      blockState =
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
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("###", "###"),
                  keys().key("#", ingredient().item(planks.nameId.toString())),
                  stackedResult(nameId.toString(), 2)));

      return super.register();
    }

    protected class BTrapdoor extends TrapdoorBlock {
      protected BTrapdoor() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_TRAPDOOR));
      }
    }
  }

  protected class Wood extends BlockWrapper {
    protected Wood() {
      super(String.format("%s_wood", TreeType.this.type));
      translation = String.format("%s Wood", TreeType.this.translation);

      block = new BWood();

      blockModels =
          blockModels(
              Map.of(blockId, "cube_column"), Map.of("end", log.blockId, "side", log.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("axis=x", JState.model(log.blockId).x(90).y(90))
                  .put("axis=y", JState.model(log.blockId))
                  .put("axis=z", JState.model(log.blockId).x(90)));
    }

    @Override
    public Block register() {
      recipes =
          Map.of(
              nameId,
              shaped(
                  pattern("_", "#", "_"),
                  keys()
                      .key("#", ingredient().item(log.nameId.toString()))
                      .key("_", ingredient().item(bark.nameId.toString())),
                  stackedResult(nameId.toString(), 1)),
              genericId("%s_wood_from_stripped", name),
              shaped(
                  pattern("___", " # ", "___"),
                  keys()
                      .key("#", ingredient().item(strippedWood.nameId.toString()))
                      .key("_", ingredient().item(bark.nameId.toString())),
                  stackedResult(nameId.toString(), 1)));

      return super.register();
    }

    protected class BWood extends PillarBlock {
      protected BWood() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.OAK_WOOD));
      }
    }
  }

  protected class StrippedLog extends BlockWrapper {
    public Identifier blockHorizontalId = genericId("block/%s_horizontal", name);
    public Identifier blockTopId = genericId("block/%s_top", name);

    protected StrippedLog() {
      super(String.format("stripped_%s_log", TreeType.this.type));
      translation = String.format("Stripped %s Log", TreeType.this.translation);

      block = new BStrippedLog();

      blockModels =
          blockModels(
              Map.of(blockId, "cube_column", blockHorizontalId, "cube_column_horizontal"),
              Map.of("end", blockTopId, "side", blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("axis", "x", JState.model(blockHorizontalId).x(90).y(90))
                  .put("axis", "y", JState.model(blockId))
                  .put("axis", "z", JState.model(blockHorizontalId).x(90)));

      burn = 5;
      spread = 5;
    }

    protected class BStrippedLog extends PillarBlock {
      protected BStrippedLog() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_LOG));
      }
    }
  }

  protected class StrippedWood extends BlockWrapper {
    protected StrippedWood() {
      super(String.format("stripped_%s_wood", TreeType.this.type));
      translation = String.format("Stripped %s Wood", TreeType.this.translation);

      block = new BStrippedWood();

      blockModels =
          blockModels(
              Map.of(blockId, "cube_column"),
              Map.of("end", strippedLog.blockId, "side", strippedLog.blockId));
      itemModel = itemModel(blockId);

      blockState =
          state(
              variant()
                  .put("axis=x", JState.model(blockId).x(90).y(90))
                  .put("axis=y", JState.model(blockId))
                  .put("axis=z", JState.model(blockId).x(90)));

      burn = 5;
      spread = 5;
    }

    protected class BStrippedWood extends PillarBlock {
      protected BStrippedWood() {
        super(FabricBlockSettings.copyOf(RegisteredBlocks.STRIPPED_OAK_WOOD));
      }
    }
  }

  protected class Bark extends ItemWrapper {
    protected Bark() {
      super(String.format("%s_bark", TreeType.this.type));
      translation = String.format("%s Bark", TreeType.this.translation);

      item = new IBark();

      itemModel = generatedItem(itemId);
    }

    protected class IBark extends Item {
      protected IBark() {
        super(DEFAULT_SETTINGS);
      }
    }
  }
}
