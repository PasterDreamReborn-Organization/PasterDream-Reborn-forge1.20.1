package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

/** 爆破箭——命中实体或物块后引发 2 级爆炸（不生成火焰）。 */
public class BlastArrow extends Arrow {

    public BlastArrow(EntityType<? extends BlastArrow> type, Level level) {
        super(type, level);
    }

    public BlastArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.BLAST_ARROW.get(), level);
    }

    public BlastArrow(Level level, double x, double y, double z) {
        this(ModEntities.BLAST_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public BlastArrow(Level level, LivingEntity shooter) {
        this(ModEntities.BLAST_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.BLAST.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        ArrowBehaviors.BLAST.onHitEntityPost(this, result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        ArrowBehaviors.BLAST.onHitBlock(this, result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BLAST_ARROW.get());
    }
}
