package com.hang.miraculousori.datagen.models;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.block.custom.*;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MiraculousOriginFoodMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // ----- 糖块 -----
        registerSugarBlock();

        // ----- 枯魂野草（双格植物）-----
        registerWitheredSoulweed();

        // ----- 下界缠魂麦（双格作物）-----
        registerNetherSoulWheat();

        // ----- 猩红藤蔓 -----
        registerCrimsonVines();

        // ----- 奇异藤蔓平台 -----
        registerWarpedVinePlatform();

        // ----- 奇异藤蔓 -----
        registerWarpedVines();

        // ----- 磨台 -----
        registerMillBlock();

        // ----- 盐岩 -----
        registerSaltRockAndDeep();

        // ----- 浮瓜 -----
        registerFloatingMelon();

        // ----- 浮瓜作物 -----
        registerFloatingMelonCrop();

        // ----- 跃进果植株 -----
        registerLeapingFruitPlants();
    }

    // 糖块
    private void registerSugarBlock() {
        // 使用 cubeAll 创建方块模型（所有面相同纹理）
        ModelFile sugarModel = cubeAll(ModBlocks.SUGAR_BLOCK.get());
        // 方块状态
        simpleBlock(ModBlocks.SUGAR_BLOCK.get(), sugarModel);
        // 物品模型（继承方块模型）
//        simpleBlockItem(ModBlocks.SUGAR_BLOCK.get(), sugarModel);
    }

    // ==================== 枯魂野草 ====================
    private void registerWitheredSoulweed() {
        ResourceLocation bottomTexture = modLoc("block/plant/withered_soulweed_bottom");
        ResourceLocation topTexture = modLoc("block/plant/withered_soulweed_top");

        ModelFile bottomModel = models().withExistingParent("plant/withered_soulweed_bottom", mcLoc("block/tinted_cross"))
                .texture("cross", bottomTexture)
                .renderType("cutout");

        ModelFile topModel = models().withExistingParent("plant/withered_soulweed_top", mcLoc("block/tinted_cross"))
                .texture("cross", topTexture)
                .renderType("cutout");

        getVariantBuilder(ModBlocks.WITHERED_SOULWEED.get())
                .forAllStates(state -> {
                    boolean isUpper = state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
                    return ConfiguredModel.builder()
                            .modelFile(isUpper ? topModel : bottomModel)
                            .build();
                });

//        simpleBlockItem(ModBlocks.WITHERED_SOULWEED.get(), bottomModel);
    }

    // ==================== 下界缠魂麦 ====================
    private void registerNetherSoulWheat() {
        // 底部模型（8个生长阶段）
        ModelFile[] bottomModels = new ModelFile[8];
        for (int age = 0; age < 8; age++) {
            ResourceLocation tex = modLoc("block/plant/nether_soul_wheat" + age);
            bottomModels[age] = models().withExistingParent("plant/nether_soul_wheat" + age, mcLoc("block/cross"))
                    .texture("cross", tex)
                    .renderType("cutout");
        }

        // 顶部模型（两种）
        ModelFile topModel5 = models().withExistingParent("plant/nether_soul_wheat_top5", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/nether_soul_wheat_top5"))
                .renderType("cutout");

        ModelFile topModel8 = models().withExistingParent("plant/nether_soul_wheat_top8", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/nether_soul_wheat_top8"))
                .renderType("cutout");

        // 状态绑定（修改顶部模型选择条件）
        getVariantBuilder(ModBlocks.NETHER_SOUL_WHEAT.get())
                .forAllStates(state -> {
                    int age = state.getValue(NetherSoulWheatCrop.AGE);
                    DoubleBlockHalf half = state.getValue(NetherSoulWheatCrop.HALF);
                    ModelFile model = (half == DoubleBlockHalf.LOWER) ? bottomModels[age]
                            : (age < 7 ? topModel5 : topModel8);
                    return ConfiguredModel.builder().modelFile(model).build();
                });
    }

    private void registerCrimsonVines() {
        for (int age = 0; age < 5; age++) {
            // 顶部模型（第一格）- 使用 crop 模型
            ModelFile topModel = models().withExistingParent("plant/crimson_vines_top_" + age, mcLoc("block/crop"))
                    .texture("crop", modLoc("block/plant/crimson_vines_" + age))
                    .renderType("cutout");
            // 底部模型（第二格）- 使用 crop 模型
            ModelFile bottomModel = models().withExistingParent("plant/crimson_vines_bottom_" + age, mcLoc("block/crop"))
                    .texture("crop", modLoc("block/plant/crimson_vines_" + age + "_2"))
                    .renderType("cutout");

            getVariantBuilder(ModBlocks.CRIMSON_VINES.get())
                    .partialState()
                    .with(CrimsonVinesBlock.AGE, age)
                    .with(CrimsonVinesBlock.HALF, DoubleBlockHalf.UPPER)
                    .setModels(new ConfiguredModel(topModel));

            getVariantBuilder(ModBlocks.CRIMSON_VINES.get())
                    .partialState()
                    .with(CrimsonVinesBlock.AGE, age)
                    .with(CrimsonVinesBlock.HALF, DoubleBlockHalf.LOWER)
                    .setModels(new ConfiguredModel(bottomModel));
        }
    }

    // ==================== 诡异藤 ====================
    private void registerWarpedVines() {
        // ---- 头部模型（使用新纹理名称，与身体共用） ----
        ModelFile headTop = models().withExistingParent("plant/warped_vines_head_top", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_connection"))   // 原 head_top → connection
                .renderType("cutout");
        ModelFile headMiddle = models().withExistingParent("plant/warped_vines_head_middle", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines"))              // 原 head → vine
                .renderType("cutout");
        ModelFile headBottomNoPlatform = models().withExistingParent("plant/warped_vines_head_bottom_no_platform", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_bottom"))       // 原 head_bottom → bottom
                .renderType("cutout");
        ModelFile headBottomWithPlatform = models().withExistingParent("plant/warped_vines_head_bottom_platform", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_bottom_connection")) // 原 head_bottom_platform → bottom_connection
                .renderType("cutout");

        getVariantBuilder(ModBlocks.WARPED_VINES_HEAD.get())
                .forAllStates(state -> {
                    HeadType type = state.getValue(WarpedVinesHeadBlock.HEAD_TYPE);
                    ModelFile model = switch (type) {
                        case TOP -> headTop;
                        case MIDDLE -> headMiddle;
                        case BOTTOM_NO_PLATFORM -> headBottomNoPlatform;
                        case BOTTOM_WITH_PLATFORM -> headBottomWithPlatform;
                        default -> headMiddle;
                    };
                    return ConfiguredModel.builder().modelFile(model).build();
                });

        // ---- 身体模型 ----
        ModelFile bodyConnection = models().withExistingParent("plant/warped_vines_connection", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_connection"))
                .renderType("cutout");
        ModelFile bodyBottom = models().withExistingParent("plant/warped_vines_bottom", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_bottom"))
                .renderType("cutout");
        ModelFile bodyBottomConnection = models().withExistingParent("plant/warped_vines_bottom_connection", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_bottom_connection"))
                .renderType("cutout");
        ModelFile bodyNormal = models().withExistingParent("plant/warped_vines_normal", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines"))
                .renderType("cutout");
        ModelFile bodyBerries = models().withExistingParent("plant/warped_vines_berries", mcLoc("block/cross"))
                .texture("cross", modLoc("block/plant/warped_vines_berries"))
                .renderType("cutout");

        getVariantBuilder(ModBlocks.WARPED_VINES_PLANT.get())
                .forAllStates(state -> {
                    boolean top = state.getValue(WarpedVines.TOP);
                    boolean bottom = state.getValue(WarpedVines.BOTTOM);
                    boolean belowAir = state.getValue(WarpedVines.BELOW_AIR);
                    boolean belowHead = state.getValue(WarpedVines.BELOW_HEAD);
                    boolean berries = state.getValue(WarpedVines.BERRIES);

                    ModelFile model;

                    if (top) {
                        // 最顶部连接地狱岩
                        model = bodyConnection;
                    } else if (bottom) {
                        // 底部身体段
                        if (belowAir) {
                            // 底部悬空（无平台）
                            model = bodyBottom;
                        } else if (belowHead) {
                            model = berries ? bodyBerries : bodyNormal;
                        } else {// 底部落地（有平台或其他方块）
                            model = bodyBottomConnection;
                        }

                    } else {
                        // 中间段：根据是否有浆果显示不同
                        model = berries ? bodyBerries : bodyNormal;
                    }

                    return ConfiguredModel.builder().modelFile(model).build();
                });

        // 物品模型（使用头部中间纹理，指向 vine）
        simpleBlockItem(ModBlocks.WARPED_VINES_HEAD.get(), headMiddle);
    }

    private void registerWarpedVinePlatform() {
        ModelFile platformModel = models().withExistingParent("plant/warped_vine_platform", mcLoc("block/block"))
                .texture("particle", modLoc("block/plant/warped_vine_platform_top"))
                .texture("top", modLoc("block/plant/warped_vine_platform_top"))
                .texture("bottom", modLoc("block/plant/warped_vine_platform_bottom"))
                .texture("side", modLoc("block/plant/warped_vine_platform_side"))
                .renderType("cutout")

                // ========== 元素 1：主体（完整方块，但底面透明/不渲染） ==========
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.UP).texture("#top").uvs(0, 0, 16, 16).end()
                // 底面：不渲染（移除 .face(Direction.DOWN) 即可）
                .face(Direction.NORTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.EAST).texture("#side").uvs(0, 0, 16, 16).end()
                .end()

                // ========== 元素 2：底部纹理显示在顶面下方 1 像素处 ==========
                .element()
                .from(0, 15, 0)      // y=15~16，紧贴顶面下方
                .to(16, 16, 16)
                .face(Direction.UP).texture("#bottom").uvs(0, 0, 16, 16).end()   // 上方显示底面纹理
                .face(Direction.DOWN).texture("#bottom").uvs(0, 0, 16, 16).end() // 下方也显示底面纹理
                .face(Direction.NORTH).texture("#bottom").uvs(0, 0, 16, 1).end()
                .face(Direction.SOUTH).texture("#bottom").uvs(0, 0, 16, 1).end()
                .face(Direction.WEST).texture("#bottom").uvs(0, 0, 1, 16).end()
                .face(Direction.EAST).texture("#bottom").uvs(0, 0, 1, 16).end()
                .end();

        simpleBlock(ModBlocks.WARPED_VINE_PLATFORM.get(), platformModel);
//        simpleBlockItem(ModBlocks.WARPED_VINE_PLATFORM.get(), platformModel);
    }

    private void registerMillBlock() {
        // 创建模型：顶面、底面、侧面各有不同纹理
        ModelFile millModel = models().withExistingParent("misc/mill_block", mcLoc("block/block"))
                .texture("particle", modLoc("block/misc/mill_top"))
                .texture("top", modLoc("block/misc/mill_top"))
                .texture("bottom", modLoc("block/misc/mill_bottom"))
                .texture("side", modLoc("block/misc/mill_side"))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.UP).texture("#top").uvs(0, 0, 16, 16).end()
                .face(Direction.DOWN).texture("#bottom").uvs(0, 0, 16, 16).end()
                .face(Direction.NORTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.EAST).texture("#side").uvs(0, 0, 16, 16).end()
                .end();

        // 方块状态
        simpleBlock(ModBlocks.MILL_BLOCK.get(), millModel);
        // 物品模型
//        simpleBlockItem(ModBlocks.MILL_BLOCK.get(), millModel);
    }

    private void registerSaltRockAndDeep() {
        // ==================== 盐岩 ====================
        ModelFile model = cubeAll(ModBlocks.SALT_ROCK.get());
        simpleBlock(ModBlocks.SALT_ROCK.get(), model);
//        simpleBlockItem(ModBlocks.SALT_ROCK.get(), model);

        // 物品模型默认使用方块模型，无需额外设置
// ====================  深层盐岩 ====================
        ModelFile model1 = cubeAll(ModBlocks.DEEPSLATE_SALT_ROCK.get());
        simpleBlock(ModBlocks.DEEPSLATE_SALT_ROCK.get(), model1);
//        simpleBlockItem(ModBlocks.DEEPSLATE_SALT_ROCK.get(), model1);
    }

    private void registerFloatingMelon() {
        ModelFile floatmelonmodel = models().withExistingParent("plant/floating_melon", mcLoc("block/block"))
                .texture("particle", modLoc("block/plant/floating_melon_top"))   // 粒子用顶面
                .texture("top", modLoc("block/plant/floating_melon_top"))
                .texture("bottom", modLoc("block/plant/floating_melon_bottom"))
                .texture("side", modLoc("block/plant/floating_melon_side"))
                // 构建完整方块，各面指定不同纹理
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.UP).texture("#top").uvs(0, 0, 16, 16).end()
                .face(Direction.DOWN).texture("#bottom").uvs(0, 0, 16, 16).end()
                .face(Direction.NORTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.EAST).texture("#side").uvs(0, 0, 16, 16).end()
                .end();

        simpleBlock(ModBlocks.FLOATING_MELON_BLOCK.get(), floatmelonmodel);
//        simpleBlockItem(ModBlocks.FLOATING_MELON_BLOCK.get(), floatmelonmodel);
    }

    private void registerFloatingMelonCrop() {
        // 纹理分组
        ResourceLocation texStage0 = modLoc("block/plant/floating_melon_stage0"); // age 0-1
        ResourceLocation texStage1 = modLoc("block/plant/floating_melon_stage1"); // age 2-5
        ResourceLocation texStage2 = modLoc("block/plant/floating_melon_stage2"); // age 6
        ResourceLocation texStage3 = modLoc("block/plant/floating_melon_stage3"); // age 7

        // 建立8个模型（每个age一个）
        ModelFile[] models = new ModelFile[8];
        for (int age = 0; age < 8; age++) {
            ResourceLocation tex;
            if (age <= 1) tex = texStage0;
            else if (age <= 5) tex = texStage1;
            else if (age == 6) tex = texStage2;
            else tex = texStage3;
            models[age] = models().withExistingParent("plant/floating_melon_crop_" + age, mcLoc("block/cross"))
                    .texture("cross", tex)
                    .renderType("cutout");
        }

        getVariantBuilder(ModBlocks.FLOATING_MELON_CROP.get())
                .forAllStates(state -> {
                    int age = state.getValue(FloatingMelonCropBlock.AGE);
                    return ConfiguredModel.builder().modelFile(models[age]).build();
                });
    }

    // ==================== 跃进果植株模型 ====================
    private void registerLeapingFruitPlants() {
        registerLeapingFruitPlantWithFacing(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), "spreading");

        // 为 TOP 和 ROOT 注册
        registerLeapingFruitPlantWithFacing(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), "top");
        registerLeapingFruitPlantWithFacing(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), "root");
    }

    /**
     * 为指定的方块生成6个朝向的模型，并绑定到 blockstate
     */
    private void registerLeapingFruitPlantWithFacing(Block block, String variantName) {
        for (Direction facing : Direction.values()) {
            ModelFile model = createLeapingFruitPlantModel(variantName, facing);
            getVariantBuilder(block)
                    .partialState()
                    .with(LeapingFruitPlantBlock.FACING, facing)
                    .setModels(new ConfiguredModel(model));
        }
    }

    /**
     * 根据朝向生成模型文件
     */
    private ModelFile createLeapingFruitPlantModel(String variantName, Direction facing) {
        String path = "plant/leaping_fruit_plant_" + variantName + "_facing_" + facing.getName();

        String texUp, texDown, texNorth, texSouth, texWest, texEast;
        float[] uvUp, uvDown, uvNorth, uvSouth, uvWest, uvEast;
        float[] defaultUV = {0, 0, 16, 16};

        if ("spreading".equals(variantName)) {
            // 蔓延根系：所有面 interlace，UV 默认
            texUp = texDown = texNorth = texSouth = texWest = texEast = "interlace";
            uvUp = uvDown = uvNorth = uvSouth = uvWest = uvEast = defaultUV;
        } else if ("root".equals(variantName)) {
            // 根系：根据 facing 分配纹理，所有面 UV 默认
            texUp = texDown = texNorth = texSouth = texWest = texEast = "side";

            if (facing == Direction.UP || facing == Direction.DOWN) {
                texUp = texDown = "interlace";
            } else if (facing == Direction.EAST || facing == Direction.WEST) {
                texEast = texWest = "interlace";
                texNorth = texSouth = texUp = texDown = "side_";
            } else if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                texNorth = texSouth = "interlace";
                texUp = texDown = "side";
                texWest = texEast = "side_";
            }
            uvUp = uvDown = uvNorth = uvSouth = uvWest = uvEast = defaultUV;
        } else {
            texUp = texDown = texNorth = texSouth = texWest = texEast = "side";

            if (facing == Direction.UP) {
                texDown = "interlace";
                texUp = "top";
            } else if (facing == Direction.DOWN) {
                texUp = "interlace";
                texDown = "top";
            }else if (facing == Direction.EAST /* || facing == Direction.WEST*/) {
                texWest = "interlace";
                texEast = "top";
                texNorth = texSouth = texUp = texDown = "side_";
            }else if (facing == Direction.WEST){
                texEast = "interlace";
                texWest = "top";
                texNorth = texSouth = texUp = texDown = "side_";
            }else if (facing == Direction.SOUTH){
                texNorth = "interlace";
                texSouth = "top";
                texUp = texDown = "side";
                texWest = texEast = "side_";
            }else if (facing == Direction.NORTH) {
                texNorth = "top";
                texSouth = "interlace";
                texUp = texDown = "side";
                texWest = texEast = "side_";
            }
            uvUp = uvDown = uvNorth = uvSouth = uvWest = uvEast = defaultUV;
        }

        // 构建模型
        ModelFile model = models().withExistingParent(path, mcLoc("block/block"))
                .texture("particle", modLoc("block/plant/side"))
                .texture("tex_up", modLoc("block/plant/" + texUp))
                .texture("tex_down", modLoc("block/plant/" + texDown))
                .texture("tex_north", modLoc("block/plant/" + texNorth))
                .texture("tex_south", modLoc("block/plant/" + texSouth))
                .texture("tex_west", modLoc("block/plant/" + texWest))
                .texture("tex_east", modLoc("block/plant/" + texEast))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.UP).texture("#tex_up").uvs(uvUp[0], uvUp[1], uvUp[2], uvUp[3]).end()
                .face(Direction.DOWN).texture("#tex_down").uvs(uvDown[0], uvDown[1], uvDown[2], uvDown[3]).end()
                .face(Direction.NORTH).texture("#tex_north").uvs(uvNorth[0], uvNorth[1], uvNorth[2], uvNorth[3]).end()
                .face(Direction.SOUTH).texture("#tex_south").uvs(uvSouth[0], uvSouth[1], uvSouth[2], uvSouth[3]).end()
                .face(Direction.WEST).texture("#tex_west").uvs(uvWest[0], uvWest[1], uvWest[2], uvWest[3]).end()
                .face(Direction.EAST).texture("#tex_east").uvs(uvEast[0], uvEast[1], uvEast[2], uvEast[3]).end()
                .end();
        return model;
    }

    /**
     * 根据朝向和面返回纹理名称（top/interlace/side）
     */
    private String getTextureName(Direction face, Direction facing) {
        // 先判断是否使用 top 或 interlace
        if (facing == Direction.UP) {
            if (face == Direction.UP) return "top";
            else if (face == Direction.DOWN) return "interlace";
            else return getSideTexture(face, facing);
        } else if (facing == Direction.DOWN) {
            if (face == Direction.UP) return "interlace";
            else if (face == Direction.DOWN) return "top";
            else return getSideTexture(face, facing);
        } else if (facing == Direction.EAST) {
            if (face == Direction.EAST) return "top";
            else if (face == Direction.WEST) return "interlace";
            else return getSideTexture(face, facing);
        } else if (facing == Direction.WEST) {
            if (face == Direction.WEST) return "top";
            else if (face == Direction.EAST) return "interlace";
            else return getSideTexture(face, facing);
        } else if (facing == Direction.NORTH) {
            if (face == Direction.NORTH) return "top";
            else if (face == Direction.SOUTH) return "interlace";
            else return getSideTexture(face, facing);
        } else { // SOUTH
            if (face == Direction.SOUTH) return "top";
            else if (face == Direction.NORTH) return "interlace";
            else return getSideTexture(face, facing);
        }
    }

    /**
     * 根据朝向和面决定使用 side.png 还是 side_.png
     */
    private String getSideTexture(Direction face, Direction facing) {
        // facing 为上下时，所有侧面使用 side.png
        if (facing == Direction.UP || facing == Direction.DOWN) {
            return "side";
        }
        // facing 为东西时，上、下、南、北使用 side.png，东西面已在上层处理为 interlace
        if (facing == Direction.EAST || facing == Direction.WEST) {
            if (face == Direction.UP || face == Direction.DOWN || face == Direction.NORTH || face == Direction.SOUTH) {
                return "side";
            }
        }
        // facing 为南北时，上、下、东、西使用 side_.png
        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            if (face == Direction.UP || face == Direction.DOWN || face == Direction.EAST || face == Direction.WEST) {
                return "side_";
            }
        }
        // 默认返回 side
        return "side";
    }
}