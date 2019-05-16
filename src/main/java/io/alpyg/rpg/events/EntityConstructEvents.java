package io.alpyg.rpg.events;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import io.alpyg.rpg.data.npc.NpcData;

public class EntityConstructEvents {

	@Listener
	public void onEntityConstruct(SpawnEntityEvent.ChunkLoad e) {
		for (Entity entity : e.getEntities()) {
			if (entity.get(NpcData.class).isPresent()) {
				System.out.println(entity);
//				entity.remove();
			}
		}
	}
	
}
