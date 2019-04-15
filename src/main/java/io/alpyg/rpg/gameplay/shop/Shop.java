package io.alpyg.rpg.gameplay.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;
import io.alpyg.rpg.economy.RpgsEconomy;
import io.alpyg.rpg.gameplay.InventorySerializer;
import io.alpyg.rpg.gameplay.shop.data.ShopKeys;
import io.alpyg.rpg.items.data.ItemKeys;

public abstract class Shop {
	
	public static void viewShop(Entity entity, Player player) {
		Map<Integer, ItemStack> invMap = addPrice(InventorySerializer.deserializeInventory(entity.get(ShopKeys.SHOP_DATA).get()));
		
		Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryTitle.of(Text.of(entity.get(ShopKeys.SHOP_ID).get() + " - " + RpgsEconomy.calculateCurrency(player.get(AdventurerKeys.BALANCE).get()).toPlain())))
				.build(Rpgs.plugin);

		if (invMap != null)
			for (int i = 0; i < 35; i++)
				if (invMap.containsKey(i))
					inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i)))
						.set(invMap.get(i));
		
		player.openInventory(inv);
	}
	
	public static Map<Integer, ItemStack> addPrice(Map<Integer, ItemStack> invMap) {
		for (int i = 0; i < invMap.size(); i++) {
			ItemStack itemStack = invMap.get(i);
			int price = itemStack.get(ItemKeys.PRICE).orElse(9999999);
			
			List<Text> lore = itemStack.get(Keys.ITEM_LORE).orElse(new ArrayList<Text>());
			lore.add(Text.of());
			lore.add(Text.of(TextColors.WHITE, "Price: ", TextColors.GRAY, RpgsEconomy.calculateCurrency(price)));
			
			itemStack.offer(Keys.ITEM_LORE, lore);
		}
		return invMap;
	}
	
}
