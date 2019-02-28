package io.alpyg.rpg.effect;

import java.util.HashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;

public class EffectManager {
	
	public static HashMap<String, Effect> effects = new HashMap<String, Effect>();
	
	public static void display(ParticleEffect particleEffect, Vector3d position) {
		for (Player p : Sponge.getServer().getOnlinePlayers()) {
			p.spawnParticles(particleEffect, position);
		}
	}
}
