package com.pasterdream.pasterdreammod.world.item.StorgeBagItem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class StorageBagEventHandler {

    private static final Field HEALTH_FIELD;

    static {
        Field f = null;
        try {
            f = ItemEntity.class.getDeclaredField("f_31985_"); // health field in mapped names
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            try {
                f = ItemEntity.class.getDeclaredField("health");
                f.setAccessible(true);
            } catch (NoSuchFieldException ignored) {}
        }
        HEALTH_FIELD = f;
    }

    /**
     * 袋子物品实体被岩浆/火焰烧毁时，散落内容物
     */
    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;
        ItemStack stack = itemEntity.getItem();
        if (!isStorageBag(stack)) return;

        // 仅在被摧毁（血量耗尽）时触发，排除玩家捡起
        int health = getHealth(itemEntity);
        if (health > 0) return;

        scatterContents(itemEntity.level(), itemEntity, stack);
    }

    private static boolean isStorageBag(ItemStack stack) {
        return stack.getItem() instanceof StorageBagItem || stack.getItem() instanceof LargeStorageBagItem;
    }

    private static int getHealth(ItemEntity entity) {
        if (HEALTH_FIELD == null) return 0;
        try {
            return HEALTH_FIELD.getInt(entity);
        } catch (IllegalAccessException e) {
            return 0;
        }
    }

    /**
     * 散落 NBT 中的物品并放出生物，最后清空标签
     */
    private static void scatterContents(Level level, ItemEntity bagEntity, ItemStack bagStack) {
        boolean hasContent = false;

        // 散落物品
        ListTag items = null;
        if (bagStack.getItem() instanceof StorageBagItem) {
            items = StorageBagItem.getInventoryTag(bagStack);
        } else if (bagStack.getItem() instanceof LargeStorageBagItem) {
            items = LargeStorageBagItem.getInventoryTag(bagStack);
        }

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack content = ItemStack.of(items.getCompound(i));
                if (!content.isEmpty()) {
                    hasContent = true;
                    ItemEntity drop = new ItemEntity(level,
                            bagEntity.getX(), bagEntity.getY() + 0.5, bagEntity.getZ(),
                            content);
                    drop.setDefaultPickUpDelay();
                    drop.setDeltaMovement(
                            (level.random.nextDouble() - 0.5) * 0.2,
                            level.random.nextDouble() * 0.3,
                            (level.random.nextDouble() - 0.5) * 0.2
                    );
                    level.addFreshEntity(drop);
                }
            }
        }

        // 放出被捕获的生物
        if (bagStack.getItem() instanceof LargeStorageBagItem
                && LargeStorageBagItem.hasCapturedEntity(bagStack)) {
            LargeStorageBagItem.releaseCapturedEntityAt(level, bagStack,
                    bagEntity.getX(), bagEntity.getY() + 0.5, bagEntity.getZ());
            hasContent = true;
        }
    }

    // === 大便携储物袋释放生物（Shift+右键方块） ===
    // 注：抓取和右键实体释放由 LargeStorageBagItem.interactLivingEntity 处理

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof LargeStorageBagItem)) return;

        if (!LargeStorageBagItem.hasCapturedEntity(stack)) return;

        // 必须潜行（Shift）才释放，否则允许正常与方块交互（如打开箱子）
        if (!player.isShiftKeyDown()) return;

        LargeStorageBagItem.releaseCapturedEntity(player.level(), player, stack);
        // 通过 setItem 替换槽位中的 ItemStack，确保库存系统跟踪到 tag 变更
        int slot = event.getHand() == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
        player.getInventory().setItem(slot, stack.copy());
        event.setCanceled(true);
        player.level().playSound(null, player.blockPosition(),
                ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
    }
}
