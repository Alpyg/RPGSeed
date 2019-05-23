package io.alpyg.rpg.gameplay;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.data.persistence.InvalidDataFormatException;
import org.spongepowered.api.item.inventory.ItemStack;

public class InventorySerializer {

	public static String serializeInventory(Map<Integer, DataContainer> inventory) {
		String finalBackpackData = "";
		
		for (int i : inventory.keySet()) {
			try {
				String index = Integer.toString(i);
				String data = DataFormats.JSON.write(inventory.get(i)).toString();
				String data64 = Base64.getEncoder().encodeToString(data.getBytes());
			
				String finalData = index + "," + data64;
				
				finalBackpackData = finalBackpackData + finalData + ";";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return finalBackpackData;
	}
	
	public static Map<Integer, ItemStack> deserializeInventory(String serializedBackpack) {
		Map<Integer, ItemStack> inventory = new HashMap<Integer, ItemStack>();
		if (serializedBackpack.equals("")) return inventory;
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
				inventory.put(index, ItemStack.builder().fromContainer(container).build());
		}
		
		return inventory;
	}
}
