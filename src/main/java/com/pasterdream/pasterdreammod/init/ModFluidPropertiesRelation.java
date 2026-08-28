package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FluidDrinkPropertiesRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.GenericFluidDrinkProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;

import java.util.UUID;

public class ModFluidPropertiesRelation
{
    public static void register()
    {
        FluidDrinkPropertiesRegistry.register(ModFluids.MELT_DREAM_LIQUID.get(), new GenericFluidDrinkProperties().drinkAmount(1000).useDuration(32).food(new FoodProperties.Builder().alwaysEat().build()).meltDreamEnergyAdd(15));
        FluidDrinkPropertiesRegistry.register(ModFluids.RAGE_ELIXIR.get(), new GenericFluidDrinkProperties().drinkAmount(1000).useDuration(32).food(new FoodProperties.Builder().alwaysEat().build()).onDrinkSpecial(((livingEntity, level) ->
        {
            if (level.isClientSide)
            {
                return;
            }

            boolean applied = false;

            AttributeInstance skillCdAttr = livingEntity.getAttribute(ModAttributes.SKILL_COOLDOWN_RATE.get());

            //ELIXIR_BOTTLE_OF_RAGE_ELIXIR_SKILL_CD_UUID = UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c806")
            if (skillCdAttr != null && skillCdAttr.getModifier(UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c806")) == null)
            {
                skillCdAttr.addPermanentModifier(new AttributeModifier(UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c806"), "elixir_bottle_of_rage_elixir_skill_cd", -0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
                applied = true;
            }

            //ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_DAMAGE_UUID = UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c807")
            AttributeInstance attackDamageAttr = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackDamageAttr != null && attackDamageAttr.getModifier(UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c807")) == null)
            {
                attackDamageAttr.addPermanentModifier(new AttributeModifier(UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c807"), "elixir_bottle_of_rage_elixir_attack_damage", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
                applied = true;
            }

            if (livingEntity instanceof Player player)
            {
                if (applied)
                {
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.AWAKE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.success"), false);
                }
                    else
                    {
                        player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail"), false);
                    }
            }
        })));
        FluidDrinkPropertiesRegistry.register(ModFluids.POTION.get(), new GenericFluidDrinkProperties().drinkAmount(250).useDuration(32).food(new FoodProperties.Builder().alwaysEat().build()));
    }
}
