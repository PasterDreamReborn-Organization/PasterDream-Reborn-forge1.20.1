package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.enchantment.DreamGatheringEnchantment;
import com.pasterdream.pasterdreammod.world.enchantment.MeltDreamRepairEnchantment;
import com.pasterdream.pasterdreammod.world.enchantment.ShelterEnchantment;
import com.pasterdream.pasterdreammod.world.enchantment.SwiftStrikeEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantment {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, PasterDreamMod.MOD_ID);
    public static final RegistryObject<Enchantment> SWIFT_STRIKE_ENCHANTMENT = REGISTRY.register("swift_strike", () -> new SwiftStrikeEnchantment());
    public static final RegistryObject<Enchantment> SHELTER_ENCHANTMENT = REGISTRY.register("shelter", () -> new ShelterEnchantment());
    public static final RegistryObject<Enchantment> DREAM_GATHERING_ENCHANTMENT = REGISTRY.register("gather_dream", () -> new DreamGatheringEnchantment());
    public static final RegistryObject<Enchantment> MELT_DREAM_REPAIR_ENCHANTMENT = REGISTRY.register("melt_dream_repair", () -> new MeltDreamRepairEnchantment());
    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
