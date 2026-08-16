package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.MagicDamageHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SoulGemOfAkizukiAyaneHandler {

    private static final String NO_CONSUME_TICKS_KEY = "pasterdream.soul_gem_no_consume_ticks";

    /** 佩戴灵魂石且融梦能量低于阈值时，受到的伤害翻倍 */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer player
                && SoulGemOfAkizukiAyaneItem.isWearing(player)
                && SoulGemOfAkizukiAyaneItem.isFragile(player)) {
            event.setAmount(event.getAmount() * 2.0F);
        }
    }

    /**
     * 佩戴灵魂石的玩家造成的来源型魔法伤害享受加成。
     * 覆盖原版喷溅治疗/伤害药水对亡灵造成的 {@code indirectMagic} 伤害（无源魔法伤害在各自调用处单独乘算）。
     */
    @SubscribeEvent
    public static void onMagicDamageBoost(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player
                && event.getSource().is(DamageTypes.INDIRECT_MAGIC)
                && SoulGemOfAkizukiAyaneItem.isWearing(player)) {
            event.setAmount(event.getAmount() * MagicDamageHelper.getMagicDamageMultiplier(player));
        }
    }

    /** 激活后 2 分钟内不消耗融梦能量，计时结束后恢复消耗 */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;

        CompoundTag pd = sp.getPersistentData();
        int ticks = pd.getInt(NO_CONSUME_TICKS_KEY);
        if (ticks <= 0) return;

        ticks--;
        if (ticks > 0) {
            pd.putInt(NO_CONSUME_TICKS_KEY, ticks);
        } else {
            pd.remove(NO_CONSUME_TICKS_KEY);
            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyIsNeed(sp, true);
        }
    }
}
