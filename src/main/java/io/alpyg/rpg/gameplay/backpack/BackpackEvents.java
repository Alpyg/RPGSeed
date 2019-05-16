package io.alpyg.rpg.gameplay.backpack;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.util.Tristate;

import io.alpyg.rpg.data.backpack.BackpackKeys;

public class BackpackEvents {

	@Listener
	public void onItemRightClickAir(InteractItemEvent.Secondary.MainHand e, @First Player player) {
		ItemStack itemStack = e.getItemStack().createStack();
		if (itemStack.get(BackpackKeys.DATA).isPresent()) {
			e.setCancelled(true);
			Backpacks.openBackpack(player, itemStack);
		}
	}
	
	@Listener
	public void onItemRightClickBlock(InteractBlockEvent.Secondary.MainHand e, @First Player player) {
		ItemStack itemStack = player.getItemInHand(HandTypes.MAIN_HAND).get();
		if (itemStack.get(BackpackKeys.DATA).isPresent()) {
			e.setCancelled(true);
			e.setUseItemResult(Tristate.FALSE);
			Backpacks.openBackpack(player, itemStack);
		}
	}
	
	@Listener
	public void onInventoryClick(ClickInventoryEvent e, @First Player player) {
		String invTitle = e.getTargetInventory().getInventoryProperty(InventoryTitle.class).get().getValue().toPlain();
		if (!invTitle.equals("Backpack")) return;
		
		for (SlotTransaction slot : e.getTransactions())
			if (slot.getOriginal().get(BackpackKeys.DATA).isPresent())
				e.setCancelled(true);
	}
	
	@Listener
	public void onInventoryClose(InteractInventoryEvent.Close e, @First Player player) {
		String invTitle = e.getTargetInventory().getInventoryProperty(InventoryTitle.class).get().getValue().toPlain();
		
		if (invTitle.equals("Backpack")) {
			ItemStack backpack = player.getItemInHand(HandTypes.MAIN_HAND).get();
			backpack.offer(BackpackKeys.DATA, Backpacks.saveBackpack(e.getTargetInventory()));
		}
	}
	
}
