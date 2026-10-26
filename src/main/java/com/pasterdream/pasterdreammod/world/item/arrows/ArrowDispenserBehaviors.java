package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.arrows.BlackstoneArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.BlastArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ChargedArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ChaosicArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.CongealWindArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.DewArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.FragdreamArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.MagicalArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ToxicSporeArrow;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * 自定义箭矢的发射器行为注册。
 * <p>
 * 使发射器能射出对应的自定义箭矢：在朝向方向生成箭矢实体（由各箭矢的坐标构造函数创建），
 * 并允许回收。行为逻辑沿用 {@link PotionBottleRegistry#registerDispenserBehavior()} 的写法。
 */
public final class ArrowDispenserBehaviors {

    private ArrowDispenserBehaviors() {
    }

    public static void register() {
        register(ModItems.TOXIC_SPORE_ARROW.get(), (level, x, y, z) -> new ToxicSporeArrow(level, x, y, z));
        register(ModItems.FRAGDREAM_ARROW.get(), (level, x, y, z) -> new FragdreamArrow(level, x, y, z));
        register(ModItems.MAGICAL_ARROW.get(), (level, x, y, z) -> new MagicalArrow(level, x, y, z));
        register(ModItems.CHARGED_ARROW.get(), (level, x, y, z) -> new ChargedArrow(level, x, y, z));
        register(ModItems.BLACKSTONE_ARROW.get(), (level, x, y, z) -> new BlackstoneArrow(level, x, y, z));
        register(ModItems.CHAOSIC_ARROW.get(), (level, x, y, z) -> new ChaosicArrow(level, x, y, z));
        register(ModItems.BLAST_ARROW.get(), (level, x, y, z) -> new BlastArrow(level, x, y, z));
        register(ModItems.CONGEAL_WIND_ARROW.get(), (level, x, y, z) -> new CongealWindArrow(level, x, y, z));
        register(ModItems.DEW_ARROW.get(), (level, x, y, z) -> new DewArrow(level, x, y, z));
    }

    private static void register(Item item, ArrowCreator creator) {
        DispenserBlock.registerBehavior(item, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level level, Position pos, ItemStack stack) {
                AbstractArrow arrow = creator.create(level, pos.x(), pos.y(), pos.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrow;
            }
        });
    }

    @FunctionalInterface
    private interface ArrowCreator {
        AbstractArrow create(Level level, double x, double y, double z);
    }
}
