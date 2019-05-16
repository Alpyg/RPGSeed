package io.alpyg.rpg.items.types;

import java.util.ArrayList;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.data.item.ItemData;
import io.alpyg.rpg.data.item.ItemKeys;
import io.alpyg.rpg.items.Item;
import io.alpyg.rpg.items.ItemConfig;

public class Weapon extends Item {
	
	public static String[] WEAPONS = { "SWORD", "BOW", "AXE", "PICKAXE", "SHOVEL" };
	
	public Weapon(ItemConfig config) {
		super(config);
		
		itemStack = ItemStack.of(config.itemType);

		itemStack.offer(itemStack.getOrCreate(ItemData.class).get());
		itemStack.offer(ItemKeys.ID, config.internalName);
		itemStack.offer(ItemKeys.DAMAGE, config.damage);
		itemStack.offer(Keys.ITEM_LORE, createLore(config));
		
		

		itemStack.offer(Keys.DISPLAY_NAME, config.displayName);
		itemStack.offer(Keys.UNBREAKABLE, config.unbreakable);
		itemStack.offer(Keys.HIDE_ATTRIBUTES, true);
		itemStack.offer(Keys.HIDE_UNBREAKABLE, true);
		itemStack.offer(Keys.HIDE_MISCELLANEOUS, true);
	}

	@Override
	public ItemStack getItemStack() {
		return this.itemStack.copy();
	}
	
	@Override
	public double getDamage() {
		return this.itemStack.get(ItemKeys.DAMAGE).get();
	}
	
	public ArrayList<Text> createLore(ItemConfig config) {
		ArrayList<Text> lore = new ArrayList<Text>();
		lore.add(Text.of(""));
		lore.add(Text.of(TextColors.GRAY, "Damage: ", TextColors.WHITE, "+", getDamage()));
		return lore;
	}

}
