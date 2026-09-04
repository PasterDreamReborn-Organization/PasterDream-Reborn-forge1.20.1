package com.pasterdream.pasterdreammod.world.item.StorgeBagItem;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LargeStorageBagItem extends Item {

    public static final int SLOT_COUNT = 25; // 5x5
    private static final String TAG_INVENTORY = "BagItems";
    private static final String TAG_CAPTURED_ENTITY = "CapturedEntity";

    public LargeStorageBagItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    /**
     * 从物品 NBT 读取库存，每个条目包含 SlotIndex 标识槽位，空槽位不存储
     */
    public static ListTag getInventoryTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(TAG_INVENTORY)) {
            tag.put(TAG_INVENTORY, new ListTag());
        }
        return tag.getList(TAG_INVENTORY, 10); // 10 = CompoundTag
    }

    public static void saveInventoryTag(ItemStack stack, ListTag items) {
        stack.getOrCreateTag().put(TAG_INVENTORY, items);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip1"));
            tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip2",
                    Component.translatable(Config.creatureCaptureEnabled ?
                            "tooltip.pasterdream.large_storage_bag.catch_enabled" :
                            "tooltip.pasterdream.large_storage_bag.catch_disabled")));
            // 显示袋内生物注册名
            if (hasCapturedEntity(stack)) {
                CompoundTag bagTag = stack.getOrCreateTag();
                CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
                CompoundTag entityData = capturedTag.getCompound("EntityData");
                String entityId = entityData.getString("id");
                if (!entityId.isEmpty()) {
                    tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip3",
                            "§e" + entityId));
                }
            }
        }
        else {
            tooltip.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        // 潜行时留给 capture/release，不打开 GUI
        if (entity.isShiftKeyDown()) return InteractionResultHolder.pass(entity.getItemInHand(hand));
        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack stack = entity.getItemInHand(hand);
            world.playSound(null, entity.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider(
                            (id, inv, player) -> new LargeStorageBagMenu(id, inv, stack, hand),
                            Component.empty()
                    ),
                    buf -> buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1)
            );
        }
        return InteractionResultHolder.sidedSuccess(entity.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!Config.creatureCaptureEnabled) return InteractionResult.PASS;

        Level level = player.level();

        // Shift + 右键：捕获生物
        if (player.isShiftKeyDown()) {
            if (hasCapturedEntity(stack)) return InteractionResult.PASS; // 已有捕获生物则不允许再抓
            if (!canCaptureEntity(target)) return InteractionResult.PASS;
            if (!level.isClientSide) {
                captureEntity(stack, target);
                target.discard();
                // 通过 setItem 替换槽位中的 ItemStack，确保库存系统跟踪 tag 变更并同步到客户端
                int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
                player.getInventory().setItem(slot, stack.copy());
                level.playSound(null, player.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // 普通右键：释放生物
        if (hasCapturedEntity(stack)) {
            if (!level.isClientSide) {
                releaseCapturedEntity(level, player, stack);
                // 通过 setItem 确保库存系统跟踪 tag 变更
                int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
                player.getInventory().setItem(slot, stack.copy());
                level.playSound(null, player.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    // === 辅助方法（供 EventHandler 调用） ===

    public static boolean hasCapturedEntity(ItemStack stack) {
        return stack.getOrCreateTag().contains(TAG_CAPTURED_ENTITY);
    }

    public static boolean isBagEmpty(ItemStack stack) {
        if (hasCapturedEntity(stack)) return false;
        ListTag items = getInventoryTag(stack);
        return items.isEmpty();
    }

    public static boolean canCaptureEntity(LivingEntity entity) {
        // 禁止抓取 Boss
        if (entity instanceof WitherBoss || entity instanceof EnderDragon) return false;
        // 史莱姆只允许小型（size == 1）
        if (entity instanceof Slime slime && slime.getSize() > 1) return false;
        // 检查配置白名单
        return Config.isCapturableEntity(entity.getType());
    }

    public static void captureEntity(ItemStack stack, LivingEntity entity) {
        // 先弹出所有骑乘者，防止乘客数据残留在 NBT 中导致释放时出问题
        entity.ejectPassengers();

        // 获取实体数据：用 saveWithoutId（不含 id/UUID），手动补充 id 并清除 UUID
        CompoundTag entityData = entity.saveWithoutId(new CompoundTag());
        entityData.putString("id", EntityType.getKey(entity.getType()).toString());
        entityData.remove("UUID"); // 清除原始 UUID，让新实体自己生成

        // 清除乘客数据（已通过 ejectPassengers 弹出，残留引用可能导致释放失败）
        entityData.remove("Passengers");

        CompoundTag capturedTag = new CompoundTag();
        capturedTag.put("EntityData", entityData);
        if (entity.hasCustomName()) {
            capturedTag.putString("CustomName", Component.Serializer.toJson(entity.getCustomName()));
        }
        // 用 copy()+setTag() 保留既有物品数据，配合 interactLivingEntity 中的 setItem 确保客户端同步
        CompoundTag bagTag = stack.getOrCreateTag().copy();
        bagTag.put(TAG_CAPTURED_ENTITY, capturedTag);
        stack.setTag(bagTag);
    }

    public static void releaseCapturedEntity(Level level, Player player, ItemStack stack) {
        CompoundTag bagTag = stack.getOrCreateTag();
        if (!bagTag.contains(TAG_CAPTURED_ENTITY)) return;

        CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
        CompoundTag entityData = capturedTag.getCompound("EntityData");
        if (entityData.isEmpty()) return;

        // 通过 id 查找 EntityType
        String entityId = entityData.getString("id");
        ResourceLocation rl = ResourceLocation.tryParse(entityId);
        if (rl == null) return;
        if (!ForgeRegistries.ENTITY_TYPES.containsKey(rl)) return;
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(rl);

        // 计算目标生成位置
        Vec3 look = player.getLookAngle();
        double targetX = player.getX() + look.x * 1.5;
        double targetY = player.getY() + player.getEyeHeight() + look.y * 0.5;
        double targetZ = player.getZ() + look.z * 1.5;

        Entity entity = null;
        try {
            entity = type.create(level);
            if (entity == null) return;

            // 修改 entityData，防止 load() 恢复旧位置/旧速度导致实体乱飞
            // -- 覆盖 Pos 为目标生成位置
            ListTag posTag = new ListTag();
            posTag.add(DoubleTag.valueOf(targetX));
            posTag.add(DoubleTag.valueOf(targetY));
            posTag.add(DoubleTag.valueOf(targetZ));
            entityData.put("Pos", posTag);

            // -- 清除 Motion，防止实体带着旧速度飞出去
            ListTag motionTag = new ListTag();
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            entityData.put("Motion", motionTag);

            // -- Rotation 改为玩家朝向
            ListTag rotTag = new ListTag();
            rotTag.add(FloatTag.valueOf(player.getYRot()));
            rotTag.add(FloatTag.valueOf(player.getXRot()));
            entityData.put("Rotation", rotTag);

            // -- 移除 id 字段，避免干扰 entity.load()
            entityData.remove("id");

            entity.load(entityData);

            // 二次确保：清除速度和掉落距离，防止实体受伤/乱飞
            entity.setDeltaMovement(0, 0, 0);
            entity.fallDistance = 0;

            if (entity instanceof LivingEntity living) {
                living.yHeadRot = player.getYRot();
                living.yBodyRot = player.getYRot();
                living.yHeadRotO = player.getYRot();
                living.yBodyRotO = player.getYRot();
            }

            // 恢复自定义名称
            if (capturedTag.contains("CustomName")) {
                entity.setCustomName(Component.Serializer.fromJson(capturedTag.getString("CustomName")));
            }

            // 添加到世界，失败则不删除 NBT（防止实体永久丢失）
            if (!level.addFreshEntity(entity)) {
                // 添加失败，保留 CapturedEntity 数据供重试
                return;
            }
        } catch (Exception e) {
            // 加载或生成失败时保留 NBT 数据，防止实体被"吞掉"
            if (entity != null && entity.isAlive()) {
                entity.discard();
            }
            return;
        }

        // 成功释放后才清除 NBT（用 copy+setTag 确保客户端同步）
        CompoundTag newBagTag = stack.getOrCreateTag().copy();
        newBagTag.remove(TAG_CAPTURED_ENTITY);
        stack.setTag(newBagTag);
    }

    /**
     * 在指定坐标释放生物（无需玩家），用于袋子被销毁时放出。不关心朝向，随机即可。
     */
    public static void releaseCapturedEntityAt(Level level, ItemStack stack, double x, double y, double z) {
        CompoundTag bagTag = stack.getOrCreateTag();
        if (!bagTag.contains(TAG_CAPTURED_ENTITY)) return;

        CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
        CompoundTag entityData = capturedTag.getCompound("EntityData");
        if (entityData.isEmpty()) return;

        String entityId = entityData.getString("id");
        ResourceLocation rl = ResourceLocation.tryParse(entityId);
        if (rl == null) return;
        if (!ForgeRegistries.ENTITY_TYPES.containsKey(rl)) return;
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(rl);

        Entity entity = null;
        try {
            entity = type.create(level);
            if (entity == null) return;

            ListTag posTag = new ListTag();
            posTag.add(DoubleTag.valueOf(x));
            posTag.add(DoubleTag.valueOf(y));
            posTag.add(DoubleTag.valueOf(z));
            entityData.put("Pos", posTag);

            ListTag motionTag = new ListTag();
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            entityData.put("Motion", motionTag);

            ListTag rotTag = new ListTag();
            rotTag.add(FloatTag.valueOf(level.random.nextFloat() * 360f));
            rotTag.add(FloatTag.valueOf(0f));
            entityData.put("Rotation", rotTag);

            entityData.remove("id");
            entity.load(entityData);
            entity.setDeltaMovement(0, 0, 0);
            entity.fallDistance = 0;

            if (capturedTag.contains("CustomName")) {
                entity.setCustomName(Component.Serializer.fromJson(capturedTag.getString("CustomName")));
            }

            if (!level.addFreshEntity(entity)) return;
        } catch (Exception e) {
            if (entity != null && entity.isAlive()) entity.discard();
            return;
        }

        CompoundTag newBagTag = stack.getOrCreateTag().copy();
        newBagTag.remove(TAG_CAPTURED_ENTITY);
        stack.setTag(newBagTag);
    }
}
