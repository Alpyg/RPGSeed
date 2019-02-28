package io.alpyg.rpg.mobs;

import java.util.Optional;

import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.living.Agent;

import ninja.leaping.configurate.ConfigurationNode;

public class MobAI {
	
	public void configureAI(Agent seedAgent, ConfigurationNode aiGoals, ConfigurationNode aiTargets) {
		Optional<Goal<Agent>> normalGoal = seedAgent.getGoal(GoalTypes.NORMAL);
		
		if(normalGoal.isPresent())
			normalGoal.get().clear();
		
//		for(ConfigurationNode aiGoal : aiGoals.getChildrenMap().values()) {
//			normalGoal.get().addTask(Integer.parseInt(aiGoal.getKey().toString()), SeedMobGoals.getAiGoal(aiGoals.getNode(aiGoal.getKey()).getString()));
//		}

//		normalGoal.get().addTask(0, AttackLivingAI.attackLivingAITask(seedAgent, 1));
//		normalGoal.get().addTask(1, WanderAI.wanderAiTask(seedAgent, 1));
//		normalGoal.get().addTask(2, WatchClosestAI.watchClosestAITask(seedAgent, 1, 10));

//		System.out.println("AI Goals: " + normalGoal.get().getTasks());
	}
}
