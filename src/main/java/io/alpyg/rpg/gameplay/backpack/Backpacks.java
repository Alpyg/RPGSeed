package io.alpyg.rpg.gameplay.backpack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.data.persistence.InvalidDataFormatException;
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
import io.alpyg.rpg.Seed;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class Backpacks {
	
	public static Text backpackName = Text.of(TextColors.AQUA, "Backpack");
	
	public static ItemStack backpack = ItemStack.of(ItemTypes.SKULL);
	
	public static void openBackpack(Player viewer, Player target) {
		Map<Integer, DataContainer> backpack = deserializeBackpack(target.get(AdventurerKeys.BACKPACK_DATA).get());
		Inventory inv;
		
		if (viewer.equals(target))		// If looking at own backpack
			inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.of(9, 3))
				.property(InventoryTitle.of(Text.of("Backpack")))
				.build(Seed.plugin);
		else							// If looking at another's backpack
			inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.of(9, 3))
				.property(InventoryTitle.of(Text.of(target.getName() + "'s Backpack")))
				.build(Seed.plugin);

		if (backpack != null)			// Fill backpack slots accordingly
			for (int i = 0; i < 35; i++)
				if (backpack.containsKey(i))
					inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i)))
						.set(ItemStack.builder().fromContainer(backpack.get(i)).build());

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

		return serializeBackpack(backpack);
	}

	private static String serializeBackpack(Map<Integer, DataContainer> backpack) {		// Encode ItemStack's data with BASE64 and store it;
		String finalBackpackData = "";
		
		for (int i : backpack.keySet()) {
			try {
				String index = Integer.toString(i);
				String data = DataFormats.JSON.write(backpack.get(i)).toString();
				String data64 = Base64.getEncoder().encodeToString(data.getBytes());
			
				String finalData = index + "," + data64;
				
				finalBackpackData = finalBackpackData + finalData + ";";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return finalBackpackData;
	}
	
	public static void upgradeBackpack(Player player) {		// Upgrade backpack
		int size = player.get(AdventurerKeys.BACKPACK_SIZE).get();
		if (size < 7)
			player.offer(AdventurerKeys.BACKPACK_SIZE, size+1);
	}
	
	private static Map<Integer, DataContainer> deserializeBackpack(String serializedBackpack) {		// Inverse of serialiseBackpack()
		Map<Integer, DataContainer> backpack = new HashMap<Integer, DataContainer>();
		if (serializedBackpack.equals("")) return null;
		String[] finalData = serializedBackpack.split(";");
		for (String entiry : finalData) {
			String[] unparsedData = entiry.split(",");
			
			int index = Integer.parseInt(unparsedData[0].trim());
			String data = new String(Base64.getDecoder().decode(unparsedData[1].trim().getBytes()));

			DataContainer container = null;
			try {
				container = DataFormats.JSON.read(data);
			} catch (InvalidDataFormatException | IOException e) {
				e.printStackTrace();
			}
			
			if (container != null)
				backpack.put(index, container);
		}
		
		return backpack;
	}
	
	public static ItemStack itemBackpack() {		// Backpack ItemStack
		return backpack.copy();
	}
	
	static {
		backpack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		backpack.offer(Keys.DISPLAY_NAME, backpackName);
		
		CompletableFuture<SkinRecord> future = Seed.mineSkin.getSkin(Paths.get(Seed.configDir + File.separator + "Assets" + File.separator + "backpack.png"));
		future.thenAccept(skinRecord -> {
			skinRecord.apply(backpack);
		});
	}
}
