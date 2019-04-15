package io.alpyg.rpg.npcs;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.mobs.ai.WatchClosestAI;
import io.alpyg.rpg.npcs.data.NpcData;
import io.alpyg.rpg.npcs.data.NpcKeys;

public class Npc {

	public Npc(Location<World> location, NpcConfig config) {
		Entity entity = location.getExtent().createEntity(EntityTypes.HUMAN, location.getPosition());

		entity.offer(entity.getOrCreate(NpcData.class).get());
		entity.offer(Keys.SKIN_UNIQUE_ID, UUID.fromString("12cf83a4-8df3-46e3-9c95-fbf74ded4743"));
		entity.offer(Keys.DISPLAY_NAME, config.getDisplayName());
		entity.offer(Keys.INVULNERABLE, true);
		entity.offer(Keys.PERSISTS, true);
		entity.offer(NpcKeys.QUEST, config.getQuest());

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(entity);
		}
		
		Agent agent = (Agent) entity;
		Goal<Agent> goal = agent.getGoal(GoalTypes.NORMAL).get();
		
		goal.clear();
		goal.addTask(0, WatchClosestAI.watchClosestAITask(agent, 1, 5));
	}
	
}
