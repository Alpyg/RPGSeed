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
import io.alpyg.rpg.data.backpack.BackpackData;
import io.alpyg.rpg.data.backpack.BackpackKeys;
import io.alpyg.rpg.gameplay.InventorySerializer;

public abstract class Backpacks {
	
	public static void openBackpack(Player player, ItemStack backpack) {
		Map<Integer, ItemStack> backpackMap = InventorySerializer.deserializeInventory(backpack.get(BackpackKeys.DATA).get());
		Inventory inv;
		
		inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
			.property(InventoryDimension.of(9, backpack.get(BackpackKeys.SIZE).get()))
			.property(InventoryTitle.of(Text.of("Backpack")))
			.build(Rpgs.plugin);

		if (backpackMap != null)
			for (int i = 0; i < 35; i++)
				if (backpackMap.containsKey(i))
					inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i)))
						.set(backpackMap.get(i));

		player.openInventory(inv);
	}
	
	public static String saveBackpack(Container container) {
		Map<Integer, DataContainer> backpack = new HashMap<Integer, DataContainer>();
		int index = 0;
		for (Inventory i : container.first().slots()) {
			if(i.peek().isPresent())
				backpack.put(index, i.peek().get().toContainer());
			index++;
		}

		return InventorySerializer.serializeInventory(backpack);
	}
	
	public static void upgradeBackpack(ItemStack backpack) {
		int size = backpack.get(BackpackKeys.SIZE).get();
		if (size < 6)
			backpack.offer(BackpackKeys.SIZE, size+1);
	}
	
	public static ItemStack backpackItem() {
		ItemStack backpack = ItemStack.of(ItemTypes.SKULL);
		backpack.offer(backpack.getOrCreate(BackpackData.class).get());
		backpack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		backpack.offer(Keys.DISPLAY_NAME, Text.of(TextColors.AQUA, "Backpack"));
		
		CompletableFuture<SkinRecord> future = Rpgs.mineSkin.getSkin(Paths.get(Rpgs.configDir + File.separator + "Assets" + File.separator + "backpack.png"));
		future.thenAccept(skinRecord -> {
			skinRecord.apply(backpack);
		});
		
		return backpack;
	}

}
