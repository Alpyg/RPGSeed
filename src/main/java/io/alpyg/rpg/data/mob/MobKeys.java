package io.alpyg.rpg.data.mob;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class MobKeys {
	
    public static Key<Value<String>> ID;
    public static Key<Value<Double>> LEVEL;
    public static Key<Value<Double>> DAMAGE;
    public static Key<Value<Double>> DEFENCE;

    public static void registerKeys() {
    	
    	ID = Key.builder()
    			.type(TypeTokens.STRING_VALUE_TOKEN)
    			.id("mob_id")
    			.name("Mob ID")
    			.query(DataQuery.of(".", "mob.id"))
    			.build();
    	
    	LEVEL = Key.builder()
    			.type(TypeTokens.DOUBLE_VALUE_TOKEN)
    			.id("mob_level")
    			.name("Mob Level")
    			.query(DataQuery.of(".", "mob.level"))
    			.build();

    	DAMAGE = Key.builder()
    			.type(TypeTokens.DOUBLE_VALUE_TOKEN)
    			.id("mob_damage")
    			.name("Mob Damage")
    			.query(DataQuery.of(".", "mob.damage"))
    			.build();
    	
    	DEFENCE = Key.builder()
    			.type(TypeTokens.DOUBLE_VALUE_TOKEN)
    			.id("mob_defence")
    			.name("Mob Defence")
    			.query(DataQuery.of(".", "mob.defence"))
    			.build();
    	
    }
}
