package io.alpyg.rpg.gameplay.shop.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class ShopKeys {

	public static Key<Value<String>> SHOP_ID;
	public static Key<Value<String>> SHOP_DATA;
	
	public static void registerKeys() {
		
		SHOP_ID = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("shop_id")
				.name("Shop ID")
				.query(DataQuery.of('.', "shop.id"))
				.build();
		
		SHOP_DATA = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("shop_data")
				.name("Shop Data")
				.query(DataQuery.of('.', "shop.data"))
				.build();
		
	}

}
