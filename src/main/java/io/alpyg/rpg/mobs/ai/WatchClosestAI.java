package io.alpyg.rpg.mobs.ai;

import org.spongepowered.api.entity.ai.task.builtin.WatchClosestAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;

public class WatchClosestAI {
	
	public static WatchClosestAITask watchClosestAITask(Agent entity, float chance, float distance) {
		WatchClosestAITask watchClosestAI = WatchClosestAITask.builder()
				.chance(chance)
		        .maxDistance(distance)
		        .watch(Player.class)
		        .build(entity);
		return watchClosestAI;
	}
}
