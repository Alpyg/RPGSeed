package io.alpyg.rpg.effect;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scheduler.Task;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.Seed;

public abstract class Effect {

	public EffectType type = EffectType.REPEATING;
	public ParticleEffect particleEffect = ParticleEffect.builder().type(ParticleTypes.CRITICAL_HIT).build();
	public Vector3d position = new Vector3d();
	public int iterations = 100;
	public int duration = 1;
	public int particleCount = 50;
	
	public Entity targetEntity;
	
	public Vector3d getPosition() {
		return this.targetEntity.getLocation().getPosition();
	}
	
	public void display(ParticleEffect particleEffect, Vector3d position) {
		EffectManager.display(particleEffect, position);
	}
	
	public void effect() {
		
	}
	
	public void playEffect() {
		Task.builder().execute(new Consumer<Task>() {

			@Override
			public void accept(Task t) {
				if (iterations < 1)
					t.cancel();
				else
					onPlay();
				iterations--;
			}
			
		}).interval(10, TimeUnit.MILLISECONDS).async().submit(Seed.plugin);
	}
	
	public abstract void onPlay();
}
