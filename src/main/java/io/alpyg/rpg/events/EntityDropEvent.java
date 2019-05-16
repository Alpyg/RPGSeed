package io.alpyg.rpg.events;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent.Pickup;
import org.spongepowered.api.event.item.inventory.DropItemEvent.Destruct;

import io.alpyg.rpg.data.mob.MobKeys;

public class EntityDropEvent {

	@Listener
	public void onEntityDrop(Destruct e) {
		String mobId = e.getCause().first(Entity.class).get().get(MobKeys.ID).orElse("NO_DROPS");
//		Player player = (Player) e.getCause().first(Player.class).orElse(null);

		if (mobId == "NO_DROPS") {
			e.setCancelled(true);
			return;
		} else {
//			System.out.println(player.getName());
//			System.out.println(mobId);
		}
	}
	
	@Listener
	public void onItemPickup(Pickup e) {
//		ItemStack itemStack = e.getTransactions().get(0).getDefault().createStack();
//		System.out.println(itemStack.toContainer());
	}
	
}
