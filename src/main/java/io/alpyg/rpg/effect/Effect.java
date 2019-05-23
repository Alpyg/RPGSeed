package io.alpyg.rpg.effect;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.effect.effects.SphereEffect;

public class Effect {

	public ParticleEffect particleEffect;
	public int iterations;
	public int duration;
	public int quantity;

	public boolean targeted;
	public Location<World> location;
	public Entity entity;
	
	public EffectType effectType;
	
	public Effect(EffectType effectType, ParticleEffect particle, int iterations, int duration, int quantity, boolean targeted, Location<World> location, Entity entity) {
		this.effectType = effectType;
		
		this.particleEffect = particle;
		this.iterations = iterations;
		this.duration = duration;
		this.quantity = quantity;
		
		this.targeted = targeted;
		this.location = location;
		this.entity = entity;
		
		playEffect();
	}
	
	public void playEffect() {
		Task.builder().execute(new Consumer<Task>() {

			@Override
			public void accept(Task t) {
				if (iterations > 0) {
			        for (int i = 0; i < quantity; i++) {
			            display(particleEffect, effectType.run(getPosition()));
			        }
				}
				else
					t.cancel();
				iterations--;
			}
			
		}).interval(100, TimeUnit.MILLISECONDS).async().submit(Rpgs.plugin);
	}
	
	public void display(ParticleEffect particleEffect, Vector3d position) {
		for (Player p : Sponge.getServer().getOnlinePlayers()) {
			p.spawnParticles(particleEffect, position);
		}
	}
	
	public Vector3d getPosition() {
		if (targeted)
			return entity.getLocation().getPosition();
		else
			return location.getPosition();
	}
	
	public static Effect.Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private EffectType effectType = new SphereEffect(0);
		private ParticleType particle = ParticleTypes.CRITICAL_HIT;
		private int iterations = 100;
		private int duration = 1;
		private int quantity = 200;
		
		private boolean targeted;
		private Entity entity;
		private Location<World> location;
		
		private Vector3d offset = Vector3d.ZERO;
		private Color color = Color.WHITE;
		private BlockState blockState = BlockState.builder().blockType(BlockTypes.AIR).build();
		
		Builder() {}
		
		public Builder particle(ParticleType particleType) {
			this.particle = particleType;
			return this;
		}
		
		public Builder effect(EffectType effectType) {
			this.effectType = effectType;
			return this;
		}
		
		public Builder iterations(int iterations) {
			this.iterations = iterations;
			return this;
		}
		
		public Builder duration(int duration) {
			this.duration = duration;
			return this;
		}
		
		public Builder count(int quantity) {
			this.quantity = quantity;
			return this;
		}
		
		public Builder target(Entity entity) {
			this.entity = entity;
			this.targeted = true;
			return this;
		}
		
		public Builder location(Location<World> location) {
			this.location = location;
			this.targeted = false;
			return this;
		}
		
		public Builder offset(Vector3d offset) {
			this.offset = offset;
			return this;
		}
		
		public Builder color(Color color) {
			this.color = color;
			return this;
		}
		
		public Builder blockState(BlockType block) {
			this.blockState = BlockState.builder().blockType(block).build();
			return this;
		}
		
		public Effect build() {
			return new Effect(
					effectType,
					ParticleEffect.builder()
						.type(particle)
						.option(ParticleOptions.COLOR, color)
						.option(ParticleOptions.BLOCK_STATE, blockState)
						.build(),
					iterations,
					duration,
					quantity,
					targeted,
					location.add(0,1,0).add(offset),
					entity
				);
		}
		
	}
	
}
