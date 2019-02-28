package io.alpyg.rpg.items.types;

import java.util.ArrayList;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.items.Item;
import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.items.data.ItemData;
import io.alpyg.rpg.items.data.ItemKeys;

public class Armor extends Item {
	
	public static String[] ARMOR = { "HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS", "SHIELD" };
	
	public Armor(ItemConfig config) {
		super(config);

		itemStack = ItemStack.of(config.itemType);
		
		itemStack.offer(itemStack.getOrCreate(ItemData.class).get());
		itemStack.offer(ItemKeys.ID, config.internalName);
		itemStack.offer(ItemKeys.DEFENCE, config.defence);
		itemStack.offer(Keys.ITEM_LORE, createLore(config));
		if(config.color != null)
			itemStack.offer(Keys.COLOR, config.color.getColor());

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
	public double getDefence() {
		return this.itemStack.get(ItemKeys.DEFENCE).get();
	}
	
	public ArrayList<Text> createLore(ItemConfig config) {
		ArrayList<Text> lore = new ArrayList<Text>();
		lore.add(Text.of(""));
		lore.add(Text.of(TextColors.GRAY, "Defence: ", TextColors.WHITE, "+", getDefence()));
		return lore;
	}

}
