package io.alpyg.rpg.gameplay.shop;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.items.ItemConfig;
import ninja.leaping.configurate.ConfigurationNode;

public class ShopConfig {

	public static Map<String, ShopConfig> shops = new HashMap<String, ShopConfig>();

	private String internalName;
	private String profession;
	private String shopData = "";

	public ShopConfig(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
		this.profession = config.getNode("Profession").getString("NO_PROFESSION");
		this.shopData = createShopData(config.getNode("Items").getChildrenMap().values());
		
		shops.put(internalName, this);
	}
	
	private String createShopData(Collection<? extends ConfigurationNode> items) {
		String finalShopData = "";
		for (ConfigurationNode item : items) {
			String index = item.getKey().toString();
			String itemType = item.getValue().toString();
			ItemStack itemStack;
			if (ItemConfig.items.containsKey(itemType))
				itemStack = ItemConfig.items.get(itemType).getItemStack();
			else if (Sponge.getRegistry().getType(ItemType.class, itemType).isPresent())
				itemStack = ItemStack.of(Sponge.getRegistry().getType(ItemType.class, itemType).get());
			else
				throw new IllegalArgumentException("Invalid Item Type " + itemType + ", Shop Keeper " + this.internalName + ", Item slot " + index);
			
			try {
				finalShopData += index + ",";
				String data = DataFormats.JSON.write(itemStack.toContainer()).toString();
				String data64 = Base64.getEncoder().encodeToString(data.getBytes());
				finalShopData += data64 + ";";
			} catch (IOException e) {}
		}
		
		return finalShopData;
	}

	public String getInternalName() {
		return internalName;
	}

	public String getProfession() {
		return profession;
	}
	
	public String getShopData() {
		return shopData;
	}
	
	public void spawnShopKeeper(Location<World> location) {
		ShopKeeper.spawn(location, this);
	}
	
	public static Supplier<Collection<String>> getShops() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return shops.keySet();
			}
			
		};
	}
	
	public static Function<String, ShopConfig> getShop() {
		return new Function<String, ShopConfig>() {

			@Override
			public ShopConfig apply(String t) {
				return shops.get(t);
			}
			
		};
	}

}
