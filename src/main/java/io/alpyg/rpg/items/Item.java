package io.alpyg.rpg.items;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public abstract class Item {
	
	protected ItemStack itemStack;
	protected String internalName;
	protected Text displayName;
	protected double damage;
	protected double defence;

	public Item(ItemConfig config) {
		this.internalName = config.internalName;
		this.displayName = config.displayName;
		this.itemStack = ItemStack.of(config.itemType);
		this.damage = config.damage;
		this.defence = config.defence;
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	public String getInternalName() {
		return this.internalName;
	}
	
	public Text getDisplayName() {
		return this.displayName;
	}
	
	public double getDamage() {
		return this.damage;
	}
	
	public double getDefence() {
		return this.defence;
	}
}
