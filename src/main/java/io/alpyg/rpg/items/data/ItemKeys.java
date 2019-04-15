package io.alpyg.rpg.items.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class ItemKeys {
	
    public static Key<Value<String>> ID;
    
    public static Key<Value<Double>> DAMAGE;
    public static Key<Value<Double>> DEFENCE;
    public static Key<Value<String>> MATERIAL_TYPE;
    public static Key<Value<Integer>> MATERIAL_TIER;
    public static Key<Value<Integer>> PRICE;

	public static void registerKeys() {
    	
    	ID = Key.builder()
    			.type(TypeTokens.STRING_VALUE_TOKEN)
    			.id("item_id")
    			.name("Item ID")
    			.query(DataQuery.of(".", "item.id"))
    			.build();
    	DAMAGE = Key.builder()
    			.type(TypeTokens.DOUBLE_VALUE_TOKEN)
    			.id("item_damage")
    			.name("Item Damage")
    			.query(DataQuery.of(".", "item.damage"))
    			.build();
    	DEFENCE = Key.builder()
    			.type(TypeTokens.DOUBLE_VALUE_TOKEN)
    			.id("item_defence")
    			.name("Item Defence")
    			.query(DataQuery.of(".", "item.defence"))
    			.build();
    	MATERIAL_TYPE = Key.builder()
    			.type(TypeTokens.STRING_VALUE_TOKEN)
    			.id("material_type")
    			.name("Material Type")
    			.query(DataQuery.of(".", "material.type"))
    			.build();
    	MATERIAL_TIER = Key.builder()
    			.type(TypeTokens.INTEGER_VALUE_TOKEN)
    			.id("material_tier")
    			.name("Material Tier")
    			.query(DataQuery.of(".", "material.tier"))
    			.build();
    	PRICE = Key.builder()
    			.type(TypeTokens.INTEGER_VALUE_TOKEN)
    			.id("price")
    			.name("Price")
    			.query(DataQuery.of(".", "price"))
    			.build();
    	
    }
}
