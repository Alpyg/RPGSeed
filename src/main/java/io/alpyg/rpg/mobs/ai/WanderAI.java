package io.alpyg.rpg.mobs.ai;

import org.spongepowered.api.entity.ai.task.builtin.creature.WanderAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class WanderAI {
	
	public static WanderAITask wanderAiTask(Agent agent, double speed) {
		WanderAITask wanderAiTask = WanderAITask.builder()
				.speed(speed)
				.build((Creature) agent);
		return wanderAiTask;
	}
}
