package io.alpyg.rpg.gameplay.mounts;

import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;

public class MountEvents {

	@Listener
	public void onMount(RideEntityEvent.Mount e, @First Player player) {
		if (e.getTargetEntity() instanceof Human) e.setCancelled(true);
	}
	
	@Listener
	public void onDismount(RideEntityEvent.Dismount e, @First Player player) {
		e.getTargetEntity().remove();
		player.setLocation(e.getTargetEntity().getLocation());
	}
	
	@Listener
	public void onSummonMount(InteractItemEvent.Secondary.MainHand e, @First Player player) {
		if (e.getItemStack().createStack().equals(Mount.MountItem))
        	Mount.summonMount(player);
	}
	
}
