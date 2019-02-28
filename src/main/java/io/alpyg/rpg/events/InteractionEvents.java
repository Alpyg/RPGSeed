package io.alpyg.rpg.events;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Tristate;

import io.alpyg.rpg.adventurer.AdventurerStatsMenu;
import io.alpyg.rpg.crafting.EquipmentUpgradeMenu;
import io.alpyg.rpg.gameplay.backpack.Backpacks;

public class InteractionEvents {
	
	@Listener
	public void onItemRightClick(InteractItemEvent e, @First Player player) {
		ItemStack item = e.getItemStack().createStack();
		if (item.get(Keys.DISPLAY_NAME).isPresent()) {
			if (item.get(Keys.DISPLAY_NAME).get().equals(Backpacks.backpackName)) {
				e.setCancelled(true);
				Backpacks.openBackpack(player, player);
			}
		}
	}
	
	@Listener
	public void onBlockRightClick(InteractBlockEvent.Secondary.MainHand e, @First Player player) {
		BlockType type = e.getTargetBlock().getState().getType();
		// Status Menu
		if (type.equals(BlockTypes.ENCHANTING_TABLE)) {
			if (e.getTargetBlock().getLocation().get().sub(0, 1, 0).getBlock().getType().equals(BlockTypes.CRAFTING_TABLE)) {
				e.setUseItemResult(Tristate.FALSE);
				AdventurerStatsMenu.openStatusMenu(player);
				e.setCancelled(true);
				return;
			}
		} 
		
		// Equipment Upgrade Menu
		else if (type.equals(BlockTypes.ANVIL)) {
			if (e.getTargetBlock().getLocation().get().sub(0, 1, 0).getBlock().getType().equals(BlockTypes.CRAFTING_TABLE)) {
				e.setUseItemResult(Tristate.FALSE);
				EquipmentUpgradeMenu.openEquipmentUpgrade(player);
				e.setCancelled(true);
				return;
			}
		}
		
		else if (type.equals(BlockTypes.CRAFTING_TABLE)) {
			// Status Menu
			if (e.getTargetBlock().getLocation().get().add(0, 1, 0).getBlock().getType().equals(BlockTypes.ENCHANTING_TABLE)) {
				e.setUseItemResult(Tristate.FALSE);
				AdventurerStatsMenu.openStatusMenu(player);
				e.setCancelled(true);
				return;
			}
			// Equipment Upgrade Menu
			else if (e.getTargetBlock().getLocation().get().add(0, 1, 0).getBlock().getType().equals(BlockTypes.ANVIL)) {
				e.setUseItemResult(Tristate.FALSE);
				EquipmentUpgradeMenu.openEquipmentUpgrade(player);
				e.setCancelled(true);
				return;
			}
		}
		
		ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
		// Backpack
		if (item.get(Keys.DISPLAY_NAME).isPresent()) {
			if (item.get(Keys.DISPLAY_NAME).get().equals(Backpacks.backpackName)) {
				e.setUseItemResult(Tristate.FALSE);
				Backpacks.openBackpack(player, player);
				e.setCancelled(true);
			}
		}
	}
}
