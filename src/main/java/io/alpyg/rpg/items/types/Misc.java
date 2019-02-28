package io.alpyg.rpg.items.types;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.items.Item;
import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.items.data.ItemData;
import io.alpyg.rpg.items.data.ItemKeys;

public class Misc extends Item {

	public Misc(ItemConfig config) {
		super(config);
		
		itemStack = ItemStack.of(config.itemType);

		itemStack.offer(itemStack.getOrCreate(ItemData.class).get());
		itemStack.offer(ItemKeys.ID, config.internalName);
		itemStack.offer(Keys.DISPLAY_NAME, Text.of(config.displayName));
	}

	@Override
	public ItemStack getItemStack() {
		return this.itemStack.copy();
	}
}
