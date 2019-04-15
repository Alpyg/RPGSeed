package io.alpyg.rpg.gameplay.backpack;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import cz.creeper.mineskinsponge.SkinRecord;
import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;
import io.alpyg.rpg.gameplay.InventorySerializer;

public abstract class Backpacks {
	
	public static Text backpackName = Text.of(TextColors.AQUA, "Backpack");
	
	public static ItemStack backpack = ItemStack.of(ItemTypes.SKULL);
	
	public static void openBackpack(Player viewer, Player target) {
		Map<Integer, ItemStack> backpack = InventorySerializer.deserializeInventory(target.get(AdventurerKeys.BACKPACK_DATA).get());
		Inventory inv;
		
		if (viewer.equals(target))		// If looking at own backpack
			inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.of(9, 3))
				.property(InventoryTitle.of(Text.of("Backpack")))
				.build(Rpgs.plugin);
		else							// If looking at another's backpack
			inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.of(9, 3))
				.property(InventoryTitle.of(Text.of(target.getName() + "'s Backpack")))
				.build(Rpgs.plugin);

		if (backpack != null)			// Fill backpack slots accordingly
			for (int i = 0; i < 35; i++)
				if (backpack.containsKey(i))
					inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i)))
						.set(backpack.get(i));

		viewer.openInventory(inv);
	}
	
	public static String saveBackpack(Container container) {	// Get each slot's Index and ItemStack and store them in a map
		Map<Integer, DataContainer> backpack = new HashMap<Integer, DataContainer>();
		int index = 0;
		for (Inventory i : container.first().slots()) {
			if(i.peek().isPresent())
				backpack.put(index, i.peek().get().toContainer());
			index++;
		}

		return InventorySerializer.serializeInventory(backpack);
	}
	
	public static void upgradeBackpack(Player player) {		// Upgrade backpack
		int size = player.get(AdventurerKeys.BACKPACK_SIZE).get();
		if (size < 7)
			player.offer(AdventurerKeys.BACKPACK_SIZE, size+1);
	}
	
	public static ItemStack itemBackpack() {		// Backpack ItemStack
		return backpack.copy();
	}
	
	static {
		backpack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		backpack.offer(Keys.DISPLAY_NAME, backpackName);
		
		CompletableFuture<SkinRecord> future = Rpgs.mineSkin.getSkin(Paths.get(Rpgs.configDir + File.separator + "Assets" + File.separator + "backpack.png"));
		future.thenAccept(skinRecord -> {
			skinRecord.apply(backpack);
		});
	}
}
