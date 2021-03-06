package io.alpyg.rpg.events;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent.Death;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import io.alpyg.rpg.adventurer.AdventurerStats;
import io.alpyg.rpg.adventurer.AdventurerUI;
import io.alpyg.rpg.data.adventurer.AdventurerData;

public class PlayerEvents {
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join e, @First Player player) {
		if (!player.get(AdventurerData.class).isPresent())
			player.offer(player.getOrCreate(AdventurerData.class).get());
		
//		if (!player.getInventory().contains(Backpacks.itemBackpack()))
//			player.getInventory().offer(Backpacks.itemBackpack());
		
		AdventurerStats.updatePlayerStats(player);
	}
	
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect e, @First Player p) {
		if (AdventurerUI.gui.containsKey(e.getTargetEntity().getUniqueId()))
			AdventurerUI.gui.get(e.getTargetEntity().getUniqueId()).stop();
		for (Entity passenger : p.getPassengers()) {
			passenger.remove();
		}
	}
	
	@Listener
	public void onPlayerRespawn(RespawnPlayerEvent e) {
		e.getTargetEntity().copyFrom(e.getOriginalPlayer());

		if (e.isDeath()) {
			AdventurerStats.deathPenalty(e.getTargetEntity());
			AdventurerStats.updatePlayerStats(e.getTargetEntity());
		}
	}
	
	@Listener
	public void onPlayerDeath(Death e) {
		if (e.getTargetEntity() instanceof Player)
			AdventurerUI.gui.get(e.getTargetEntity().getUniqueId()).stop();
	}

}
