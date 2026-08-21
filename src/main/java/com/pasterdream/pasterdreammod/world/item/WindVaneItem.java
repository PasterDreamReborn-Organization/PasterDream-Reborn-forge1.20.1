package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 风向标：右键检测当前风向与玩家朝向角度。
 */
public class WindVaneItem extends Item {

    private static final DecimalFormat FMT = new DecimalFormat("##.##");

    public WindVaneItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.wind_vane.desc"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            int direction = level.getGameRules().getInt(ModGameRules.WIND_DIRECTION);
            player.displayClientMessage(Component.translatable("message.pasterdream.wind_vane.angle",
                    FMT.format(player.getXRot()), FMT.format(player.getYRot())), true);
            player.displayClientMessage(Component.translatable("message.pasterdream.wind_vane.direction." + direction), false);
            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                    ModSounds.DING.get(), SoundSource.PLAYERS, 1, 1);
        }
        return super.use(level, player, hand);
    }
}
