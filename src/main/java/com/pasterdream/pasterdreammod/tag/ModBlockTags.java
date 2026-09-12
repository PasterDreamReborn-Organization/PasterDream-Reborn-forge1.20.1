package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> MOD_GLASS_PANE = create("glass_panes");
    public static final TagKey<Block> MOD_GLASS = create("glass");
    public static final TagKey<Block> STRIPPED_LOGS = createForgeTag("stripped_logs");
    public static final TagKey<Block> STRIPPED_WOOD = createForgeTag("stripped_wood");
    public static final TagKey<Block> FORGE_GLASS_PANE = createForgeTag("glass_panes");
    public static final TagKey<Block> FORGE_GLASS = createForgeTag("glass");
    public static final TagKey<Block> DYEDREAM_BUD_CAN_SPAWN_ON = create("dyedream_bud_can_spawn_on");
    public static final TagKey<Block> DYEDREAM_GROUND_PLANTS_CAN_SPAWN_ON = create("dyedream_ground_plants_can_spawn_on");
    public static final TagKey<Block> PLIER_PLANTS = create("plier_plants");
    public static final TagKey<Block> CRIMSON_THORNS_CAN_PLACE_ON = create("crimson_thorns_can_place_on");
    public static final TagKey<Block> BLAZE_FLOWER_CAN_PLACE_ON = create("blaze_flower_can_place_on");
    public static final TagKey<Block> SHADOW_PLANTS_CAN_PLACE_ON = create("shadow_plants_can_place_on");
    public static final TagKey<Block> SHADOW_EROSION_TOOL_CAN_BOOST = create("shadow_erosion_tool_can_boost");
    /** 染梦侵染：满足此标签的方块会被转化为染梦泥土。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_DIRT = create("dyedream_contamination_to_dirt");
    /** 染梦侵染：满足此标签的方块会被转化为染梦草方块。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_GRASS_BLOCK = create("dyedream_contamination_to_grass_block");
    /** 染梦侵染：满足此标签的方块会被转化为染梦沙。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_SAND = create("dyedream_contamination_to_sand");
    /** 染梦侵染：满足此标签的方块会被转化为染梦原木。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_LOG = create("dyedream_contamination_to_log");
    /** 染梦侵染：满足此标签的方块会被转化为染梦树叶。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_LEAVES = create("dyedream_contamination_to_leaves");
    /** 染梦侵染：满足此标签的方块会被转化为染梦浮冰。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_TO_PACKED_ICE = create("dyedream_contamination_to_packed_ice");
    /** 染梦侵染黑名单：满足此标签的方块永远不会被转化。 */
    public static final TagKey<Block> DYEDREAM_CONTAMINATION_BLACKLIST = create("dyedream_contamination_blacklist");
    private static TagKey<Block> create(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, pName));
    }

    private static TagKey<Block> createForgeTag(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", pName));
    }
}
