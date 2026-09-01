package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.*;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ForgeAdvancementProvider {

    public ModAdvancementProvider(PackOutput output,
                                  CompletableFuture<HolderLookup.Provider> registries,
                                  ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new StoryAdvancements()));
    }

    public static class StoryAdvancements implements AdvancementGenerator {

        // 原版冒险Tab根进度，作为跨tab子进度的父节点
        private static final Advancement ADVENTURE_ROOT = new Advancement(
                ResourceLocation.fromNamespaceAndPath("minecraft", "adventure/root"),
                null, null, AdvancementRewards.EMPTY, Map.of(), new String[0][0], false);

        private static final Advancement NETHER_ROOT = new Advancement(
                ResourceLocation.fromNamespaceAndPath("minecraft", "nether/root"),
                null, null, AdvancementRewards.EMPTY, Map.of(), new String[0][0], false);

        private static final Advancement FISHING = new Advancement(
                ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/fishy_business"),
                null, null, AdvancementRewards.EMPTY, Map.of(), new String[0][0], false);

        private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
                ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

        private static final ResourceKey<Level> WIND_JOURNEY_WORLD =
                ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

        @Override
        public void generate(HolderLookup.@NotNull Provider registries,
                             @NotNull Consumer<Advancement> saver,
                             @NotNull ExistingFileHelper existingFileHelper) {

            // ========== 根进度：帕斯特之梦 ==========
            Advancement root = Advancement.Builder.advancement()
                    .display(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(),
                            Component.translatable("advancements.pasterdream.story.root.title"),
                            Component.translatable("advancements.pasterdream.story.root.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/screens/guiblock.png"),
                            FrameType.TASK,
                            false, false, false
                    )
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/root"), existingFileHelper);

            // ========== 子进度：首次接触染梦裂隙（无显示，纯标记）==========
            Advancement firstContactCrack = Advancement.Builder.advancement()
                    .parent(root)
                    .addCriterion("first_contact", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/first_contact_dyedream_crack"), existingFileHelper);

            // ========== 子进度：染梦裂隙 ==========
            Advancement dyedreamCrackAdv = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModBlocks.DYEDREAM_CRACK.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_crack.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_crack.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("read_dyedream_crack_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("dyedreamCreak"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_crack"), existingFileHelper);

            // ========== 染梦裂隙子进度：太陽とレインボ一 ==========
            Advancement createpliers = Advancement.Builder.advancement()
                    .parent(dyedreamCrackAdv)
                    .display(
                            ModItems.PLIERS.get(),
                            Component.translatable("advancements.pasterdream.story.create_pliers.title"),
                            Component.translatable("advancements.pasterdream.story.create_pliers.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("create_pliers", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.PLIERS.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/create_pliers"), existingFileHelper);

            // ========== 染梦裂隙子进度：染梦世界 ==========
            Advancement dyedreamWorld = Advancement.Builder.advancement()
                    .parent(dyedreamCrackAdv)
                    .display(
                            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_world.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_world.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("first_enter_dyedream", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_world"), existingFileHelper);

            // ========== 染梦世界子进度：方块人会梦见粉红羊吗 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            Items.PINK_WOOL,
                            Component.translatable("advancements.pasterdream.story.look_at_pink_sheep.title"),
                            Component.translatable("advancements.pasterdream.story.look_at_pink_sheep.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("look_at_pink_sheep", LookAtPinkSheepTrigger.TriggerInstance.look())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/look_at_pink_sheep"), existingFileHelper);

            // ========== 染梦世界子进度：不可食用果冻 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DREAM_FERTILIZER.get(),
                            Component.translatable("advancements.pasterdream.story.dream_fertilizer.title"),
                            Component.translatable("advancements.pasterdream.story.dream_fertilizer.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_dream_fertilizer", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DREAM_FERTILIZER.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dream_fertilizer"), existingFileHelper);

            // ========== 染梦世界子进度：把梦捧在手心里! ==========
            Advancement dyedreamDust = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DYEDREAM_DUST.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_dust.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_dust.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_dyedream_dust", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DYEDREAM_DUST.get()))
                    .rewards(AdvancementRewards.Builder.experience(20))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_dust"), existingFileHelper);

            // ========== 把梦捧在手心里子进度：熔融于梦 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamDust)
                    .display(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(),
                            Component.translatable("advancements.pasterdream.story.melt_dream_crystal_fragment.title"),
                            Component.translatable("advancements.pasterdream.story.melt_dream_crystal_fragment.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("has_melt_dream_crystal_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/melt_dream_crystal_fragment"), existingFileHelper);

            // ========== 染梦世界子进度：好梦在何方 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.GLASS_JAR_OF_DREAM_JUICE.get(),
                            Component.translatable("advancements.pasterdream.story.glass_jar_of_dream_juice.title"),
                            Component.translatable("advancements.pasterdream.story.glass_jar_of_dream_juice.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("drink_dream_juice", ConsumeItemTrigger.TriggerInstance.usedItem(
                            ItemPredicate.Builder.item().of(ModItems.GLASS_JAR_OF_DREAM_JUICE.get()).build()))
                    .rewards(AdvancementRewards.Builder.experience(20))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/glass_jar_of_dream_juice"), existingFileHelper);

            // ========== 染梦世界子进度：尘埃落定 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DREAM_ACCUMULATOR.get(),
                            Component.translatable("advancements.pasterdream.story.dream_accumulator.title"),
                            Component.translatable("advancements.pasterdream.story.dream_accumulator.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("read_dream_accumulator_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("dreamAccumulator"))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dream_accumulator"), existingFileHelper);

            // ========== 染梦世界子进度：百味杂陈 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.MELT_DREAM_LIQUID_BUCKET.get(),
                            Component.translatable("advancements.pasterdream.story.melt_dream_liquid_bucket.title"),
                            Component.translatable("advancements.pasterdream.story.melt_dream_liquid_bucket.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_melt_dream_liquid_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MELT_DREAM_LIQUID_BUCKET.get()))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/melt_dream_liquid_bucket"), existingFileHelper);

            // ========== 染梦世界子进度：星辰凝胶 ==========
            Advancement galaxyJelly = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.GALAXY_JELLY.get(),
                            Component.translatable("advancements.pasterdream.story.galaxy_jelly.title"),
                            Component.translatable("advancements.pasterdream.story.galaxy_jelly.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_galaxy_jelly", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.GALAXY_JELLY.get()))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/galaxy_jelly"), existingFileHelper);

            // ========== 星辰凝胶子进度：太空专业配音员 ==========
            Advancement.Builder.advancement()
                    .parent(galaxyJelly)
                    .display(
                            ModItems.GALAXY_JELLY.get(),
                            Component.translatable("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.title"),
                            Component.translatable("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("eat_galaxy_jelly_on_high_height", EatGalaxyJellyAtHeightTrigger.TriggerInstance.atBuildHeight())
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/eat_galaxy_jelly_on_high_height"), existingFileHelper);

            // ========== 子进度：纯洁无暇 ==========
            Advancement pureAndFlawless = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModItems.WHITE_COROLLA.get(),
                            Component.translatable("advancements.pasterdream.story.pure_and_flawless.title"),
                            Component.translatable("advancements.pasterdream.story.pure_and_flawless.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_white_corolla", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.WHITE_COROLLA_CROP_AGE_1.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/pure_and_flawless"), existingFileHelper);

            // ========== 子进度：哦，痛！ ==========
            Advancement useBoneNeedle = Advancement.Builder.advancement()
                    .parent(pureAndFlawless)
                    .display(
                            ModItems.PALE_BONENEEDLE.get(),
                            Component.translatable("advancements.pasterdream.story.use_pale_boneneedle.title"),
                            Component.translatable("advancements.pasterdream.story.use_pale_boneneedle.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("used_boneneedle", UseBoneNeedleTrigger.TriggerInstance.use())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/use_pale_boneneedle"), existingFileHelper);

            // ========== 挑战子进度：人类跌出梦境 ==========
            Advancement.Builder.advancement()
                    .parent(useBoneNeedle)
                    .display(
                            ModItems.PALE_BONENEEDLE.get(),
                            Component.translatable("advancements.pasterdream.story.human_falls_out_of_dream.title"),
                            Component.translatable("advancements.pasterdream.story.human_falls_out_of_dream.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("fell_and_used", UseBoneNeedleTrigger.TriggerInstance.fellAndUse())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/human_falls_out_of_dream"), existingFileHelper);

            // ========== 灯影世界剧情线 ==========

            // 进度：侵染教堂 — 需要阅读"侵染教堂"笔记后解锁（笔记由玩家自行探索获取）
            Advancement lampShadowRoot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.lamp_shadow_root.title"),
                            Component.translatable("advancements.pasterdream.story.lamp_shadow_root.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("read_infested_church_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("侵染教堂-黑面"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/lamp_shadow_root"), existingFileHelper);

            // 据点守卫 — 完成暮影之笼事件后授予
            Advancement bastionGuard = Advancement.Builder.advancement()
                    .parent(lampShadowRoot)
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.bastion_guard.title"),
                            Component.translatable("advancements.pasterdream.story.bastion_guard.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("complete_bastion_guard",
                            new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/bastion_guard"), existingFileHelper);

            // ========== 灯影之下 Tab 页根进度（独立 tab，CHALLENGE）==========
            // 原作 achievement_shadow_start，首次进入 lamp_shadow_world 时触发
            Advancement enterLampShadowWorld = Advancement.Builder.advancement()
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.enter_lamp_shadow_world.title"),
                            Component.translatable("advancements.pasterdream.story.enter_lamp_shadow_world.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/screens/yin_ying_.png"),
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("enter_lamp_shadow_world",
                            ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(LAMP_SHADOW_WORLD))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/enter_lamp_shadow_world"), existingFileHelper);

            // 影中知己 —— 驯服友善怨魂
            Advancement tameFriendlyGhost = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .display(
                            ModItems.SOUL_ESSENCE.get(),
                            Component.translatable("advancements.pasterdream.story.tame_friendly_ghost.title"),
                            Component.translatable("advancements.pasterdream.story.tame_friendly_ghost.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("tame_friendly_ghost",
                            TameAnimalTrigger.TriggerInstance.tamedAnimal(
                                    EntityPredicate.Builder.entity()
                                            .of(ModEntities.FRIENDLY_SHADOW_GHOST.get())
                                            .build()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/tame_friendly_ghost"), existingFileHelper);

            // 于影共眠 —— 在暮影长床入睡前往灯影（原作 achievement_shadow_a_1，程序授予，+10 XP）
            Advancement sleepWithShadow = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .display(
                            ModItems.SHADOW_BED.get(),
                            Component.translatable("advancements.pasterdream.story.sleep_with_shadow.title"),
                            Component.translatable("advancements.pasterdream.story.sleep_with_shadow.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("sleep_with_shadow", new ImpossibleTrigger.TriggerInstance())
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/sleep_with_shadow"), existingFileHelper);

            // 浸影回忆 —— 获得笔记残页（原作 achievement_shadow_a_0，+10 XP，解锁解析配方）
            Advancement shadowBrokenNote = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .display(
                            ModItems.BROKEN_NOTE.get(),
                            Component.translatable("advancements.pasterdream.story.broken_note.title"),
                            Component.translatable("advancements.pasterdream.story.broken_note.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtain_broken_note", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ModItems.BROKEN_NOTE.get())
                                    .withCount(MinMaxBounds.Ints.exactly(1)).build()))
                    .addCriterion("has_lamp_shadow_root",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/enter_lamp_shadow_world")))
                    .rewards(AdvancementRewards.Builder.experience(10)
                            .addRecipe(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "unknown_note")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/broken_note"), existingFileHelper);

            // 于影研读 —— 获得研究台（原作 achievement_shadow_a_1 位置，+10 XP）
            Advancement shadowResearchTable = Advancement.Builder.advancement()
                    .parent(shadowBrokenNote)
                    .display(
                            ModItems.RESEARCH_TABLE.get(),
                            Component.translatable("advancements.pasterdream.story.research_table.title"),
                            Component.translatable("advancements.pasterdream.story.research_table.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtain_research_table", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.RESEARCH_TABLE.get()))
                    .addCriterion("has_lamp_shadow_root",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/enter_lamp_shadow_world")))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/research_table"), existingFileHelper);

            // 困顿囚徒 —— 首次进入暗影地牢（原作 achievement_shadow_c_0，程序授予，+10 XP）
            Advancement shadowPrisoner = Advancement.Builder.advancement()
                    .parent(shadowResearchTable)
                    .display(
                            ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_prisoner.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_prisoner.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("enter_shadow_dungeon", new ImpossibleTrigger.TriggerInstance())
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_prisoner"), existingFileHelper);

            // ========== 灯影世界剧情线（纯逻辑进度，不在进度界面显示）==========
            Advancement depositionShadow = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .addCriterion("read_deposition_shadow_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("depositionShadow"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/deposition_shadow"), existingFileHelper);

            Advancement lampShadowTravelogue1 = Advancement.Builder.advancement()
                    .parent(depositionShadow)
                    .addCriterion("read_lamp_shadow_travelogue_1_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("lampShadowTravelogue1"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/lamp_shadow_travelogue_1"), existingFileHelper);

            Advancement shadowDungeon = Advancement.Builder.advancement()
                    .parent(lampShadowTravelogue1)
                    .addCriterion("read_shadow_dungeon_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("shadowDungeon"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_dungeon"), existingFileHelper);

            Advancement deception = Advancement.Builder.advancement()
                    .parent(shadowDungeon)
                    .addCriterion("read_deception_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("欺诈"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/deception"), existingFileHelper);

            Advancement bargain = Advancement.Builder.advancement()
                    .parent(deception)
                    .addCriterion("read_bargain_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("交易"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/bargain"), existingFileHelper);

            Advancement shattered = Advancement.Builder.advancement()
                    .parent(bargain)
                    .addCriterion("read_shattered_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("破碎"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shattered"), existingFileHelper);

            // ========== 无名剧情线（暗影地牢 NPC 对话 + 灯影选择） ==========
            // 纯逻辑标记，不在进度界面显示

            // 第一次对话完成（程序授予，触发暗影窥视）
            Advancement shadowNpcFirstDialogue = Advancement.Builder.advancement()
                    .addCriterion("first_dialogue", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_npc_first_dialogue"), existingFileHelper);

            // 暗影入侵事件完成（解锁第二次对话）—— 可见目标进度，挂困顿囚徒之后
            Advancement shadowIntrudeComplete = Advancement.Builder.advancement()
                    .parent(shadowPrisoner)
                    .display(
                            ModItems.NIGHTMARE_FUEL.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_intrude_complete.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_intrude_complete.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("intrude_complete", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_intrude_complete"), existingFileHelper);

            // 第二次对话完成（解锁灯影选择）
            Advancement shadowNpcSecondDialogue = Advancement.Builder.advancement()
                    .addCriterion("second_dialogue", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_npc_second_dialogue"), existingFileHelper);

            // 灯与影 —— 做出选择（原作 achievement_shadow_d_0，parent 原作 shadow_c_0，现挂到暗影入侵 shadow_intrude_complete 之下）
            Advancement shadowChoice = Advancement.Builder.advancement()
                    .parent(shadowIntrudeComplete)
                    .display(
                            ModBlocks.SHADOW_CANDLE.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_choice.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_choice.description"),
                            null,
                            FrameType.TASK,
                            true, true, true
                    )
                    .addCriterion("make_choice", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_choice"), existingFileHelper);

            // 信仰光明 —— 选灯
            Advancement talentLight = Advancement.Builder.advancement()
                    .parent(shadowChoice)
                    .display(
                            ModItems.TALENT_LIGHT.get(),
                            Component.translatable("advancements.pasterdream.story.talent_light.title"),
                            Component.translatable("advancements.pasterdream.story.talent_light.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("choose_light", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/talent_light"), existingFileHelper);

            // 暗影仆从 —— 选影
            Advancement talentShadow = Advancement.Builder.advancement()
                    .parent(shadowChoice)
                    .display(
                            ModItems.TALENT_SHADOW.get(),
                            Component.translatable("advancements.pasterdream.story.talent_shadow.title"),
                            Component.translatable("advancements.pasterdream.story.talent_shadow.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("choose_shadow", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/talent_shadow"), existingFileHelper);

            // 吹影镂尘 —— 击败亚伦柯斯之触（原作 achievement_shadow_e_0），挂暗影入侵之后
            Advancement defeatAaroncos = Advancement.Builder.advancement()
                    .parent(shadowIntrudeComplete)
                    .display(
                            ModItems.PURE_HORROR.get(),
                            Component.translatable("advancements.pasterdream.story.defeat_aaroncos.title"),
                            Component.translatable("advancements.pasterdream.story.defeat_aaroncos.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("defeat_aaroncos", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/defeat_aaroncos"), existingFileHelper);

            // ========== 冒险Tab子进度：被遗忘的剑冢 ==========
            Advancement find_tomb = Advancement.Builder.advancement()
                    .parent(ADVENTURE_ROOT)
                    .display(
                            ModItems.SWORD_EMBRYO.get(),
                            Component.translatable("advancements.pasterdream.adventure.forgotten_sword_tomb.title"),
                            Component.translatable("advancements.pasterdream.adventure.forgotten_sword_tomb.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("found_tomb", FoundTombTrigger.TriggerInstance.found())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/forgotten_sword_tomb"), existingFileHelper);

            // ========== 被遗忘的剑冢子进度：被遗忘之剑 ==========
            Advancement draw_the_sword = Advancement.Builder.advancement()
                    .parent(find_tomb)
                    .display(
                            ModItems.SWORD_EMBRYO.get(),
                            Component.translatable("advancements.pasterdream.story.get_the_lost_sword.title"),
                            Component.translatable("advancements.pasterdream.story.get_the_lost_sword.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_sword_embryo", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SWORD_EMBRYO.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_the_lost_sword"), existingFileHelper);

            Advancement craft_kusanagi = Advancement.Builder.advancement()
                    .parent(draw_the_sword)
                    .display(
                            ModItems.KUSANAGI.get(),
                            Component.translatable("advancements.pasterdream.craft_kusanagi.title"),
                            Component.translatable("advancements.pasterdream.craft_kusanagi.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_kusanagi", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.KUSANAGI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/craft_kusanagi"), existingFileHelper);

            Advancement get_murakumo_kusanagi = Advancement.Builder.advancement()
                    .parent(craft_kusanagi)
                    .display(
                            ModItems.MURAKUMO_KUSANAGI.get(),
                            Component.translatable("advancements.pasterdream.get_murakumo_kusanagi.title"),
                            Component.translatable("advancements.pasterdream.get_murakumo_kusanagi.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_murakumo_kusanagi", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MURAKUMO_KUSANAGI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_murakumo_kusanagi"), existingFileHelper);

            // ========== 挑战进度1：邻家有女初长成，力拔三兮气盖世 ==========
            Advancement dig_down_a_tomb= Advancement.Builder.advancement()
                    .parent(find_tomb)
                    .display(
                            ModItems.LOST_SWORD_TOMB.get(),
                            Component.translatable("advancements.pasterdream.story.dig_up_a_tomb.title"),
                            Component.translatable("advancements.pasterdream.story.dig_up_a_tomb.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true,true
                    )
                    .addCriterion("has_lost_sword_tomb", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.LOST_SWORD_TOMB.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/has_lost_sword_tomb"), existingFileHelper);

            // ========== 挑战进度2：新概念拔剑 ==========
            Advancement new_standard_sword_drawing = Advancement.Builder.advancement()
                    .parent(dig_down_a_tomb)
                    .display(
                            ModItems.LOST_SWORD_TOMB.get(),
                            Component.translatable("advancements.pasterdream.new_standard_sword_drawing.title"),
                            Component.translatable("advancements.pasterdream.new_standard_sword_drawing.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true,true
                    )
                    .addCriterion("new_standard_sword_drawing", NewStandardSwordDrawingTrigger.TriggerInstance.draw())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/new_standard_sword_drawing"), existingFileHelper);

            Advancement find_desert_fortress = Advancement.Builder.advancement()
                    .parent(ADVENTURE_ROOT)
                    .display(
                            ModItems.DESERT_HERO_TOMB.get(),
                            Component.translatable("advancements.pasterdream.adventure.find_desert_fortress.title"),
                            Component.translatable("advancements.pasterdream.adventure.find_desert_fortress.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("find_desert_fortress", FoundDesertFortressTrigger.TriggerInstance.found())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/desert_fortress"), existingFileHelper);

            Advancement get_desert_sword = Advancement.Builder.advancement()
                    .parent(find_desert_fortress)
                    .display(
                            ModItems.DESERT_SWORD.get(),
                            Component.translatable("advancements.pasterdream.adventure.get_desert_sword.title"),
                            Component.translatable("advancements.pasterdream.adventure.get_desert_sword.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_desert_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DESERT_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_desert_sword"), existingFileHelper);

            Advancement get_chenjingmen_desert_sword = Advancement.Builder.advancement()
                    .parent(get_desert_sword)
                    .display(
                            ModItems.CHENJINGMEN_DESERT_SWORD.get(),
                            Component.translatable("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.title"),
                            Component.translatable("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("get_chenjingmen_desert_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.CHENJINGMEN_DESERT_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_chenjingmen_desert_sword"), existingFileHelper);

            // ========== 下界Tab子进度：真金不怕火炼 ==========
            Advancement get_molten_gold_ingot = Advancement.Builder.advancement()
                    .parent(NETHER_ROOT)
                    .display(
                            ModItems.MOLTEN_GOLD_INGOT.get(),
                            Component.translatable("advancements.pasterdream.get_molten_gold_ingot.title"),
                            Component.translatable("advancements.pasterdream.get_molten_gold_ingot.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_molten_gold_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MOLTEN_GOLD_INGOT.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/get_molten_gold_ingot"), existingFileHelper);

            // ========== 真金不怕火炼任务线1：炙热之剑 ==========
            Advancement craft_hellfire_sword = Advancement.Builder.advancement()
                    .parent(get_molten_gold_ingot)
                    .display(
                            ModItems.HELLFIRE_SWORD.get(),
                            Component.translatable("advancements.pasterdream.craft_hellfire_sword.title"),
                            Component.translatable("advancements.pasterdream.craft_hellfire_sword.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_hellfire_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.HELLFIRE_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/craft_hellfire_sword"), existingFileHelper);

            // ========== 真金不怕火炼任务线2：炼狱之火 ==========
            Advancement craft_inferno_sword = Advancement.Builder.advancement()
                    .parent(craft_hellfire_sword)
                    .display(
                            ModItems.INFERNO_SWORD.get(),
                            Component.translatable("advancements.pasterdream.craft_inferno_sword.title"),
                            Component.translatable("advancements.pasterdream.craft_inferno_sword.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_inferno_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.INFERNO_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/craft_inferno_sword"), existingFileHelper);

            // ========== 腥味十足的交易子进度：海之馈赠 ==========
            Advancement get_deep_sea_treasure = Advancement.Builder.advancement()
                    .parent(FISHING)
                    .display(
                            ModItems.DEEP_SEA_TREASURE.get(),
                            Component.translatable("advancements.pasterdream.get_deep_sea_treasure.title"),
                            Component.translatable("advancements.pasterdream.get_deep_sea_treasure.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DEEP_SEA_TREASURE.get()))
                    .addCriterion("has_dyedream_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()))
                    .addCriterion("has_shadow_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SHADOW_DEEP_SEA_TREASURE.get()))
                    .requirements(RequirementsStrategy.OR)
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_deep_sea_treasure"), existingFileHelper);

            // ========== 海之馈赠子进度：To wish upon a satellite... ==========
            CompoundTag superNbt = new CompoundTag();
            superNbt.putBoolean("deep_treasure_super", true);

            Advancement get_super_deep_sea_treasure = Advancement.Builder.advancement()
                    .parent(get_deep_sea_treasure)
                    .display(
                            ModItems.DEEP_SEA_TREASURE.get(),
                            Component.translatable("advancements.pasterdream.get_super_deep_sea_treasure.title"),
                            Component.translatable("advancements.pasterdream.get_super_deep_sea_treasure.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_super_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.DEEP_SEA_TREASURE.get())
                                    .hasNbt(superNbt)
                                    .build()))
                    .addCriterion("has_super_dyedream_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get())
                                    .hasNbt(superNbt)
                                    .build()))
                    .addCriterion("has_super_shadow_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.SHADOW_DEEP_SEA_TREASURE.get())
                                    .hasNbt(superNbt)
                                    .build()))
                    .requirements(RequirementsStrategy.OR)
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_super_deep_sea_treasure"), existingFileHelper);

            // ========== 海之馈赠子进度：海洋之泪 ==========
            Advancement get_blue_dew = Advancement.Builder.advancement()
                    .parent(get_deep_sea_treasure)
                    .display(
                            ModItems.BLUE_DEW.get(),
                            Component.translatable("advancements.pasterdream.get_blue_dew.title"),
                            Component.translatable("advancements.pasterdream.get_blue_dew.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_blue_dew", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BLUE_DEW.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_blue_dew"), existingFileHelper);

            // ========== 海洋之泪子进度：深蓝之心 ==========
            Advancement get_blue_heart_of_the_sea = Advancement.Builder.advancement()
                    .parent(get_blue_dew)
                    .display(
                            ModItems.BLUE_HEART_OF_THE_SEA.get(),
                            Component.translatable("advancements.pasterdream.get_blue_heart_of_the_sea.title"),
                            Component.translatable("advancements.pasterdream.get_blue_heart_of_the_sea.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_blue_dew", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BLUE_HEART_OF_THE_SEA.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_blue_heart_of_the_sea"), existingFileHelper);

            // ========== 深蓝之心子进度：潮汐之神 ==========
            Advancement get_beihairuo_tide_sword = Advancement.Builder.advancement()
                    .parent(get_blue_heart_of_the_sea)
                    .display(
                            ModItems.BEIHAI_RUO_TIDE_SWORD.get(),
                            Component.translatable("advancements.pasterdream.get_beihairuo_tide_sword.title"),
                            Component.translatable("advancements.pasterdream.get_beihairuo_tide_sword.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_beihairuo_tide_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BEIHAI_RUO_TIDE_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_beihairuo_tide_sword"), existingFileHelper);

            // =========== 染梦珍藏 ==========
            Advancement root_dyedream_treasure = Advancement.Builder.advancement()
                    .display(
                            ModItems.DYEDREAM_DUST.get(),
                            Component.translatable("advancements.pasterdream.root_dyedream_treasure.title"),
                            Component.translatable("advancements.pasterdream.root_dyedream_treasure.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/screens/guiblock_dyedream_treasure.png"),
                            FrameType.TASK,
                            false, false, false
                    )
                    .addCriterion("enter_dyedream", new ImpossibleTrigger.TriggerInstance())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/root_dyedream_treasure"), existingFileHelper);

            Advancement sand_of_time = Advancement.Builder.advancement()
                    .parent(root_dyedream_treasure)
                    .display(
                            ModItems.SAND_OF_TIME.get(),
                            Component.translatable("advancements.pasterdream.get_sand_of_time.title"),
                            Component.translatable("advancements.pasterdream.get_sand_of_time.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_sand_of_time", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SAND_OF_TIME.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_sand_of_time"), existingFileHelper);

            Advancement broken_hero_sword = Advancement.Builder.advancement()
                    .parent(sand_of_time)
                    .display(
                            ModItems.BROKEN_HERO_SWORD.get(),
                            Component.translatable("advancements.pasterdream.get_broken_hero_sword.title"),
                            Component.translatable("advancements.pasterdream.get_broken_hero_sword.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_broken_hero_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BROKEN_HERO_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_broken_hero_sword"), existingFileHelper);


            Advancement terra_sword = Advancement.Builder.advancement()
                    .parent(broken_hero_sword)
                    .display(
                            ModItems.TERRA_BLADE.get(),
                            Component.translatable("advancements.pasterdream.get_terra_sword.title"),
                            Component.translatable("advancements.pasterdream.get_terra_sword.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_terra_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.TERRA_BLADE.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_terra_sword"), existingFileHelper);

            Advancement boboji_curios = Advancement.Builder.advancement()
                    .parent(broken_hero_sword)
                    .display(
                            ModItems.BOBOJI_CURIO.get(),
                            Component.translatable("advancements.pasterdream.get_boboji_curios.title"),
                            Component.translatable("advancements.pasterdream.get_boboji_curios.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_boboji_curios", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BOBOJI_CURIO.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_boboji_curios"), existingFileHelper);

            Advancement allkinds_ring = Advancement.Builder.advancement()
                    .parent(boboji_curios)
                    .display(
                            ModItems.ALLKINDS_RING.get(),
                            Component.translatable("advancements.pasterdream.get_allkinds_ring.title"),
                            Component.translatable("advancements.pasterdream.get_allkinds_ring.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_allkinds_ring", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.ALLKINDS_RING.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_allkinds_ring"), existingFileHelper);

            Advancement hiyori_butterfly_hairpin = Advancement.Builder.advancement()
                    .parent(allkinds_ring)
                    .display(
                            ModItems.HIYORI_BUTTERFLY_HAIRPIN.get(),
                            Component.translatable("advancements.pasterdream.get_hiyori_butterfly_hairpin.title"),
                            Component.translatable("advancements.pasterdream.get_hiyori_butterfly_hairpin.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_hiyori_butterfly_hairpin", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.HIYORI_BUTTERFLY_HAIRPIN.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_hiyori_butterfly_hairpin"), existingFileHelper);

            Advancement snow_vow_head = Advancement.Builder.advancement()
                    .parent(hiyori_butterfly_hairpin)
                    .display(
                            ModItems.SNOW_VOW_HEAD.get(),
                            Component.translatable("advancements.pasterdream.get_snow_vow_head.title"),
                            Component.translatable("advancements.pasterdream.get_snow_vow_head.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_snow_vow_head", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SNOW_VOW_HEAD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_snow_vow_head"), existingFileHelper);

            Advancement star_wish_rod = Advancement.Builder.advancement()
                    .parent(snow_vow_head)
                    .display(
                            ModItems.STAR_WISH_ROD.get(),
                            Component.translatable("advancements.pasterdream.get_star_wish_rod.title"),
                            Component.translatable("advancements.pasterdream.get_star_wish_rod.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_star_wish_rod", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.STAR_WISH_ROD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_star_wish_rod"), existingFileHelper);

            Advancement blessing_of_cecilia = Advancement.Builder.advancement()
                    .parent(snow_vow_head)
                    .display(
                            ModItems.BLESSING_OF_CECILIA.get(),
                            Component.translatable("advancements.pasterdream.get_blessing_of_cecilia.title"),
                            Component.translatable("advancements.pasterdream.get_blessing_of_cecilia.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_blessing_of_cecilia", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BLESSING_OF_CECILIA.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_blessing_of_cecilia"), existingFileHelper);

            Advancement light_butterfly_curio = Advancement.Builder.advancement()
                    .parent(blessing_of_cecilia)
                    .display(
                            ModItems.LIGHT_BUTTERFLY_CURIO.get(),
                            Component.translatable("advancements.pasterdream.get_light_butterfly_curio.title"),
                            Component.translatable("advancements.pasterdream.get_light_butterfly_curio.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_light_butterfly_curio", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.LIGHT_BUTTERFLY_CURIO.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_light_butterfly_curio"), existingFileHelper);

            Advancement qym_doll = Advancement.Builder.advancement()
                    .parent(light_butterfly_curio)
                    .display(
                            ModItems.QYM_DOLL.get(),
                            Component.translatable("advancements.pasterdream.get_qym_doll.title"),
                            Component.translatable("advancements.pasterdream.get_qym_doll.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_qym_doll", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.QYM_DOLL.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_qym_doll"), existingFileHelper);

            Advancement terra_floating_island = Advancement.Builder.advancement()
                    .parent(qym_doll)
                    .display(
                            ModItems.TERRA_FLOATING_ISLAND.get(),
                            Component.translatable("advancements.pasterdream.get_terra_floating_island.title"),
                            Component.translatable("advancements.pasterdream.get_terra_floating_island.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_terra_floating_island", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.TERRA_FLOATING_ISLAND.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_terra_floating_island"), existingFileHelper);

            Advancement sliver_bell = Advancement.Builder.advancement()
                    .parent(terra_floating_island)
                    .display(
                            ModItems.SLIVER_BELL.get(),
                            Component.translatable("advancements.pasterdream.get_sliver_bell.title"),
                            Component.translatable("advancements.pasterdream.get_sliver_bell.description"),
                            null,
                            FrameType.GOAL,
                            true, true, true
                    )
                    .addCriterion("get_sliver_bell", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SLIVER_BELL.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_sliver_bell"), existingFileHelper);

            Advancement dream_harp_of_wanderer = Advancement.Builder.advancement()
                    .parent(sliver_bell)
                    .display(
                            ModItems.DREAM_HARP_OF_WANDERER.get(),
                            Component.translatable("advancements.pasterdream.get_dream_harp_of_wanderer.title"),
                            Component.translatable("advancements.pasterdream.get_dream_harp_of_wanderer.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_dream_harp_of_wanderer", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DREAM_HARP_OF_WANDERER.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_dream_harp_of_wanderer"), existingFileHelper);

            Advancement worldtree_seedpod = Advancement.Builder.advancement()
                    .parent(dream_harp_of_wanderer)
                    .display(
                            ModItems.WORLDTREE_SEEDPOD.get(),
                            Component.translatable("advancements.pasterdream.get_worldtree_seedpod.title"),
                            Component.translatable("advancements.pasterdream.get_worldtree_seedpod.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_worldtree_seedpod", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.WORLDTREE_SEEDPOD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_worldtree_seedpod"), existingFileHelper);

            Advancement kaichu_omamori = Advancement.Builder.advancement()
                    .parent(root_dyedream_treasure)
                    .display(
                            ModItems.KAICHU_OMAMORI.get(),
                            Component.translatable("advancements.pasterdream.get_kaichu_omamori.title"),
                            Component.translatable("advancements.pasterdream.get_kaichu_omamori.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_kaichu_omamori", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.KAICHU_OMAMORI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_kaichu_omamori"), existingFileHelper);

            // ========== 风之旅途 Tab 页根进度（独立 tab，CHALLENGE）==========
            // 首次进入 wind_journey_world 维度时触发
            Advancement enterWindJourney = Advancement.Builder.advancement()
                    .display(
                            ModBlocks.CYAN_MOSS_STONE.get(),
                            Component.translatable("advancements.pasterdream.story.enter_wind_journey.title"),
                            Component.translatable("advancements.pasterdream.story.enter_wind_journey.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/block/cyan_stone_bricks.png"),
                            FrameType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("enter_wind_journey",
                            ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(WIND_JOURNEY_WORLD))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/enter_wind_journey"), existingFileHelper);


        }
    }
}
