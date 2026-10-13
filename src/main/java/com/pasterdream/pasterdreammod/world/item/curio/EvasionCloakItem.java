package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModKeyMappings;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class EvasionCloakItem extends Item implements ICurioItem {

    public static final double ENERGY_COST = 10.0;
    public static final int COOLDOWN_TICKS = 6000;   // 300s
    public static final int EFFECT_DURATION = 1800;   // 90s

    public EvasionCloakItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.LEGENDARY));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    /**
     * 由 Z 键 CloakActivationPacket 调用，在服务端检查能量/冷却/装备状态后激活衣装。
     */
    public static boolean tryActivate(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return false;

        boolean equipped = CuriosApi.getCuriosInventory(serverPlayer)
                .map(h -> h.findFirstCurio(ModItems.EVASION_CLOAK.get()).isPresent())
                .orElse(false);
        if (!equipped) return false;

        if (player.getCooldowns().isOnCooldown(ModItems.EVASION_CLOAK.get())) return false;

        double energy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);
        if (!player.isCreative() && energy < ENERGY_COST) {
            player.displayClientMessage(Component.translatable("tooltip.pasterdream.terra_blade.no_energy"), true);
            return false;
        }
        if (!player.isCreative()) {
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -ENERGY_COST);
        }
        player.getCooldowns().addCooldown(ModItems.EVASION_CLOAK.get(), COOLDOWN_TICKS);

        Level level = player.level();
        level.playSound(null, player.blockPosition(), ModSounds.CLOAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        level.playSound(null, player.blockPosition(), ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ModParticleTypes.SILVER_PARTICLE.get(),
                    player.getX(), player.getY() + 1, player.getZ(), 32, 0.4, 0.5, 0.4, 0.01);
        }

        player.addEffect(new MobEffectInstance(ModEffects.EVASION_CLOAK.get(),
                EFFECT_DURATION, 0, false, false));
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.evasion_cloak.hotkey",
                ModKeyMappings.CLOAK_ACTIVATE.getTranslatedKeyMessage()).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.evasion_cloak.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.evasion_cloak.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.evasion_cloak.cooldown"));
        list.add(Component.translatable("tooltip.pasterdream.evasion_cloak.energy_cost"));
    }
}
