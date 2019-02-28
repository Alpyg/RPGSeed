package io.alpyg.rpg.items.types;

import java.util.ArrayList;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.items.Item;
import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.items.data.ItemData;
import io.alpyg.rpg.items.data.ItemKeys;

public class Material extends Item {
	
	public static String[] MATERIALS = { "INGOT", "ORE", "LOG", "DIAMOND", "EMERALD" };

	public Material(ItemConfig config) {
		super(config);
		
		itemStack = ItemStack.of(config.itemType);

		itemStack.offer(itemStack.getOrCreate(ItemData.class).get());
		itemStack.offer(ItemKeys.ID, config.internalName);
		itemStack.offer(Keys.DISPLAY_NAME, Text.of(config.displayName));
		itemStack.offer(Keys.ITEM_LORE, createLore(config));

		itemStack.offer(ItemKeys.MATERIAL_TYPE, config.materialType);
		itemStack.offer(ItemKeys.MATERIAL_TIER, config.materialTier);
		if (config.materialTier > 0) {
			itemStack.offer(ItemKeys.MATERIAL_TIER, config.materialTier);
			itemStack.offer(Keys.DISPLAY_NAME, Text.of(config.displayName, " +", config.materialTier));
		}
	}

	@Override
	public ItemStack getItemStack() {
		return this.itemStack.copy();
	}
	
	public ArrayList<Text> createLore(ItemConfig config) {
		ArrayList<Text> lore = new ArrayList<Text>();
		return lore;
	}

}