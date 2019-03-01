package io.alpyg.rpg.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent.Death;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import io.alpyg.rpg.adventurer.AdventurerStats;
import io.alpyg.rpg.adventurer.AdventurerUI;
import io.alpyg.rpg.adventurer.data.AdventurerData;
import io.alpyg.rpg.gameplay.backpack.Backpacks;

public class PlayerEvents {
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join e, @First Player player) {
		if (!player.get(AdventurerData.class).isPresent())
			player.offer(player.getOrCreate(AdventurerData.class).get());
		
		if (!player.getInventory().contains(Backpacks.itemBackpack(player)))
			player.getInventory().offer(Backpacks.itemBackpack(player));
		
		AdventurerStats.updatePlayerStats(player);
//		player.getInventory().offer(GatherTools.getMiningTool().copy());
	}
	
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect e, @First Player p) {
		AdventurerUI.gui.get(e.getTargetEntity().getUniqueId()).stop();
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
