package io.alpyg.rpg.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;

import io.alpyg.rpg.adventurer.AdventurerStatsMenu;
import io.alpyg.rpg.crafting.EquipmentUpgradeMenu;

public class InventoryEvents {
	
	@Listener
	public void onInventoryClick(ClickInventoryEvent e, @First Player player) {
		if (e.getTransactions().isEmpty()) return;
		int slot = e.getTransactions().get(0).getSlot().getInventoryProperty(SlotIndex.class).get().getValue();
		String invTitle = e.getTargetInventory().getInventoryProperty(InventoryTitle.class).get().getValue().toPlain();
		
		// Status Menu
		if (invTitle.contains("Adventurer Status")) {
			if (slot < 36) {
				e.setCancelled(true);
				if (AdventurerStatsMenu.statusSlots.contains(slot))
					if (AdventurerStatsMenu.onStatusItemClicked(player, slot))
						AdventurerStatsMenu.openStatusMenu(player);
			}
		}
		
		else if (invTitle.contains("Quests"))
			if (slot < 54)
				e.setCancelled(true);
		
		// Upgrade Menu
		else if (invTitle.contains("Equipment Upgrade")) {
			if (!EquipmentUpgradeMenu.equipmentSlots.contains(slot) && slot < 9)
				e.setCancelled(true);
//			else if (slot == 1 || slot == 3)
//				EquipmentUpgrade.equipmentUpgrades.get(player.getUniqueId()).upgrade(slot, e.getTransactions().get(0).getDefault());
			else if (slot == 7)
				if (e.getTransactions().get(0).getOriginal().getType().equals(ItemTypes.AIR))
					e.setCancelled(true);
		}
	}
	
}
