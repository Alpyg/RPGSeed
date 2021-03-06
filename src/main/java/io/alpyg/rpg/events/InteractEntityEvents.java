package io.alpyg.rpg.events;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.data.npc.NpcKeys;
import io.alpyg.rpg.gameplay.shop.Shop;
import io.alpyg.rpg.gameplay.shop.data.ShopKeys;
import io.alpyg.rpg.quests.QuestManager;

public class InteractEntityEvents {

	@Listener
	public void on(InteractEntityEvent.Secondary.MainHand e, @First Player player) {	
		e.setCancelled(true);	
		if (!player.getItemInHand(HandTypes.MAIN_HAND).get().isEmpty()) {
			String itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get().getOrElse((Keys.DISPLAY_NAME), Text.of()).toPlain();
			if (itemInHand.contains("Entity Remover")) {
				for (Entity entity : e.getTargetEntity().getPassengers())
					entity.remove();
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
		
		if (e.getTargetEntity().get(NpcKeys.QUEST).isPresent()) {
			QuestManager.getQuest(e.getTargetEntity().get(NpcKeys.QUEST).get()).get().openQuestView(player);
			return;
		}

		else if (e.getTargetEntity().get(ShopKeys.SHOP_DATA).isPresent()) {
			Shop.viewShop(e.getTargetEntity(), player);
		}
	}
	
}
