package io.alpyg.rpg.utils;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import io.alpyg.rpg.items.ItemConfig;

public class ItemStackUtils {

	public static Optional<ItemStack> getItemStack(String itemType) {
		if (ItemConfig.items.containsKey(itemType))
			return Optional.of(ItemConfig.items.get(itemType).getItemStack());
		else if (Sponge.getRegistry().getType(ItemType.class, itemType).isPresent())
			return Optional.of(ItemStack.of(Sponge.getRegistry().getType(ItemType.class, itemType).get()));
		else
			return Optional.empty();
	}

}
