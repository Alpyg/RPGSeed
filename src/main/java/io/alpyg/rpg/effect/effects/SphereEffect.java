package io.alpyg.rpg.effect.effects;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.entity.Entity;

import io.alpyg.rpg.effect.Effect;
import io.alpyg.rpg.utils.VectorUtils;

public class SphereEffect extends Effect {
	
	private double radius = 1;

	public SphereEffect(ParticleType particleType, Entity entity) {
		this.particleEffect = ParticleEffect.builder().type(particleType).build();
		this.targetEntity = entity;
		this.duration = 2;
		
		this.iterations *= this.duration;
		playEffect(); // TODO remove this like, it's temporary
	}

	@Override
	public void onPlay() {
        for (int i = 0; i < particleCount; i++) {
            display(particleEffect, getPosition().add(0, 1, 0).add(VectorUtils.randomVector3d().mul(radius)));
        }		
	}
}
