package io.alpyg.rpg.items;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import io.alpyg.rpg.Seed;
import io.alpyg.rpg.items.types.Armor;
import io.alpyg.rpg.items.types.Material;
import io.alpyg.rpg.items.types.Misc;
import io.alpyg.rpg.items.types.Weapon;
import ninja.leaping.configurate.ConfigurationNode;

public class ItemConfig {
	
	public static HashMap<String, Item> items = new HashMap<String, Item>();

	public String internalName;
	public ItemType itemType;
	public Text displayName;
	public String materialType;
	public int materialTier;
	public boolean unbreakable;
	public double damage;
	public double defence;
	public DyeColor color;
	
	public ItemConfig(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
        this.itemType = setItemType(config.getNode("Type").getString().toUpperCase());
		this.displayName = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Display").getString(internalName));
		this.materialType = config.getNode("Material", "Type").getString("");
		this.materialTier = config.getNode("Material", "Tier").getInt();
		this.unbreakable = config.getNode("Unbreakable").getBoolean(false);
		this.damage = config.getNode("Damage").getDouble(0);
		this.defence = config.getNode("Defence").getDouble(0);
        this.color = setColor(config.getNode("Color").getString());
		
        if (itemType == null) return;
        if (Arrays.stream(Armor.ARMOR).parallel().anyMatch(itemType.getId().toUpperCase()::contains))
        	items.put(internalName, new Armor(this));
        else if (Arrays.stream(Weapon.WEAPONS).parallel().anyMatch(itemType.getId().toUpperCase()::contains))
        	items.put(internalName, new Weapon(this));
        else if (Arrays.stream(Material.MATERIALS).parallel().anyMatch(itemType.getId().toUpperCase()::contains))
        	items.put(internalName, new Material(this));
        else
        	items.put(internalName, new Misc(this));
	}
    
	private DyeColor setColor(String strColor) {
    	if (itemType == null) return null;
        if (itemType.getId().contains("leather_")) {
        	if (Sponge.getRegistry().getType(DyeColor.class, strColor).isPresent())
        		return Sponge.getRegistry().getType(DyeColor.class, strColor).get();
        	else
    			Seed.getLogger().warn("Invalid DyeColor for item " + internalName);
        }
        return null;
    }
    
    private ItemType setItemType(String strType) {
    	if (Sponge.getRegistry().getType(ItemType.class, strType).isPresent())
    		return Sponge.getRegistry().getType(ItemType.class, strType).get();
    	else
			Seed.getLogger().warn("Invalid ItemType for item " + internalName);
		return null;
    }

	public static Supplier<Collection<String>> getItems() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return items.keySet();
			}
			
		};
	}
	
	public static Function<String, Item> getItem() {
		return new Function<String, Item>() {

			@Override
			public Item apply(String t) {
				return items.get(t);
			}
			
		};
	}
}
