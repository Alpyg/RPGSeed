package io.alpyg.rpg.events;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Tristate;

import io.alpyg.rpg.adventurer.AdventurerStatsMenu;
import io.alpyg.rpg.crafting.EquipmentUpgradeMenu;
import io.alpyg.rpg.quests.QuestManager;

public class InteractEvents {
	
	@Listener
	public void onItemRightClick(InteractItemEvent.Secondary.MainHand e, @First Player player) {
		Text item = e.getItemStack().get(Keys.DISPLAY_NAME).orElse(Text.of());
		
		if (item.equals(QuestManager.journalName)) {
			e.setCancelled(true);
			QuestManager.openPlayerJournal(player);
		}
	}
	
	@Listener
	public void onEntityRightClick(InteractEntityEvent.Secondary.MainHand e, @First Player player) {
		if (e.getTargetEntity() instanceof Player) return;
	}
	
	@Listener
	public void onBlockRightClick(InteractBlockEvent.Secondary.MainHand e, @First Player player) {
		BlockType type = e.getTargetBlock().getState().getType();
		// Status Menu
		if (type.equals(BlockTypes.ENCHANTING_TABLE)) {
			if (e.getTargetBlock().getLocation().get().sub(0, 1, 0).getBlock().getType().equals(BlockTypes.BEDROCK)) {
				e.setCancelled(true);
				e.setUseItemResult(Tristate.FALSE);
				AdventurerStatsMenu.openStatusMenu(player);
			}
		} 
		
		// Equipment Upgrade Menu
		else if (type.equals(BlockTypes.ANVIL)) {
			if (e.getTargetBlock().getLocation().get().sub(0, 1, 0).getBlock().getType().equals(BlockTypes.BEDROCK)) {
				e.setCancelled(true);
				e.setUseItemResult(Tristate.FALSE);
				EquipmentUpgradeMenu.openEquipmentUpgrade(player);
			}
		}
		
		else if (type.equals(BlockTypes.CRAFTING_TABLE)) {
			// Status Menu
			if (e.getTargetBlock().getLocation().get().add(0, 1, 0).getBlock().getType().equals(BlockTypes.ENCHANTING_TABLE)) {
				e.setCancelled(true);
				e.setUseItemResult(Tristate.FALSE);
				AdventurerStatsMenu.openStatusMenu(player);
			}
			// Equipment Upgrade Menu
			else if (e.getTargetBlock().getLocation().get().add(0, 1, 0).getBlock().getType().equals(BlockTypes.ANVIL)) {
				e.setCancelled(true);
				e.setUseItemResult(Tristate.FALSE);
				EquipmentUpgradeMenu.openEquipmentUpgrade(player);
			}
		}
		
		Text item = player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).orElse(Text.of());
		// Backpack
		
		if (item.equals(QuestManager.journalName)) {
			e.setCancelled(true);
			QuestManager.openPlayerJournal(player);
		}
	}
	
}
