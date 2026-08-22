package com.hang.miraculousori.particle;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MiraculousOriginFoodMod.MODID);

//    public static final Supplier<SimpleParticleType> EXAMPLE_PARTICLE =
//            PARTICLES.register("example_particle", () -> new SimpleParticleType(false));
}