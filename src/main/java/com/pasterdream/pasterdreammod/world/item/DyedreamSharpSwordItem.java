package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 极锋染梦合金剑 —— 右键蓄力特殊攻击，命中时击飞敌人并造成额外伤害。
 * <p>
 * 镶嵌：融梦水晶碎片<br>
 * 战技：凌空斩 —— 冷却 5 秒，拥有染梦守护缩短为 3 秒。
 */
public class DyedreamSharpSwordItem extends SwordItem {

    private static final String TAG_COOLDOWN = "SharpCooldown";
    private static final String TAG_CHARGED = "SharpCharged";

    public DyedreamSharpSwordItem(Tier tier, Properties properties) {
        super(tier, 4, -2.4f, properties);
    }

    /**
     * 右键蓄力：冷却就绪时标记下次攻击为特殊攻击，播放音效。
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            long now = level.getGameTime();
            long lastUse = stack.getOrCreateTag().getLong(TAG_COOLDOWN);
            // 染梦守护 buff 缩短冷却：3 秒 vs 5 秒
            int cooldownTicks = player.hasEffect(ModEffects.DYEDREAM_ARMOR_BUFF.get()) ? 60 : 100;
            if (now - lastUse >= cooldownTicks) {
                stack.getOrCreateTag().putLong(TAG_COOLDOWN, now);
                stack.getOrCreateTag().putBoolean(TAG_CHARGED, true);
                player.getCooldowns().addCooldown(this, cooldownTicks);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.SHARP_DYEDREAM_SWORD_USE.get(), player.getSoundSource(), 1.0f, 1.0f);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    /**
     * 命中时若已蓄力 → 凌空斩：击飞敌人。
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean(TAG_CHARGED)) {
            stack.getOrCreateTag().putBoolean(TAG_CHARGED, false);
            target.setDeltaMovement(target.getDeltaMovement().add(0, 0.6, 0));
            target.hurtMarked = true;
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_dyedream_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_dyedream_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_dyedream_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_dyedream_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_dyedream_sword.5"));
    }
}
