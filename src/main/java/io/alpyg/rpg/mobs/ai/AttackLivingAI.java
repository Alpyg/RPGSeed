package io.alpyg.rpg.mobs.ai;

import org.spongepowered.api.entity.ai.task.builtin.creature.AttackLivingAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class AttackLivingAI {
	
	public static AttackLivingAITask attackLivingAITask(Agent agent, double speed) {
		AttackLivingAITask attackLivingAiTask = AttackLivingAITask.builder()
				.speed(speed)
				.build((Creature) agent);
		return attackLivingAiTask;
	}
}
