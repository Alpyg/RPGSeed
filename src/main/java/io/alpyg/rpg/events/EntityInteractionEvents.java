package io.alpyg.rpg.events;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.gameplay.gathering.data.GatherKeys;
import io.alpyg.rpg.mobs.data.MobKeys;
import io.alpyg.rpg.npcs.data.NpcKeys;
import io.alpyg.rpg.quests.QuestManager;

public class EntityInteractionEvents {

	@Listener
	public void on(InteractEntityEvent.Secondary.MainHand e, @First Player player) {	
		e.setCancelled(true);	
		if (!player.getItemInHand(HandTypes.MAIN_HAND).get().isEmpty()) {
			String itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get().getOrElse((Keys.DISPLAY_NAME), Text.of()).toPlain();
			if (itemInHand.contains("Entity Remover")) {
				e.getTargetEntity().remove();
				return;
			}
			
//			if (e.getTargetEntity().get(GatherData.class).isPresent()) {
//				e.setCancelled(true);
//					
//				if (itemInHand.contains("Tool"))
//					if (itemInHand.contains(e.getTargetEntity().get(GatherKeys.TOOL).get()))
//						GatherNode.gatherNode(player, e.getTargetEntity());
//				return;
//			}
		}
		
		System.out.println(e.getTargetEntity().get(GatherKeys.ID).orElse("NONE"));
		
		if (e.getTargetEntity() instanceof Human) {
			if (e.getTargetEntity().get(NpcKeys.QUEST).isPresent())
				player.sendMessage(Text.of(QuestManager.getQuest(e.getTargetEntity().get(NpcKeys.QUEST).get()).get().getStage(1).get().objective.toPlainSingle()));
			System.out.println(e.getTargetEntity().get(MobKeys.ID).get());
			return;
		}
	}
}
