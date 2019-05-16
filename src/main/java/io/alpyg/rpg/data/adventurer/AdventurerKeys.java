package io.alpyg.rpg.adventurer.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.util.TypeTokens;

import com.google.common.reflect.TypeToken;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class AdventurerKeys {

	public static Key<Value<AdventurerStats>> STATS;
	public static Key<Value<Double>> MAX_MANA;
	public static Key<Value<Double>> MANA;
	
	public static Key<Value<Integer>> BALANCE;
	
	public static Key<Value<String>> BACKPACK_DATA;
	public static Key<Value<Integer>> BACKPACK_SIZE;
	
	public static Key<Value<EntitySnapshot>> MOUNT;

    public static void registerKeys() {
		
		STATS = Key.builder()
				.type(new TypeToken<Value<AdventurerStats>>(){ private static final long serialVersionUID = 1L; })
				.id("adventurer_stats")
				.name("Adventurer Stats")
				.query(DataQuery.of('.', "adventurer.stats"))
				.build();
		MAX_MANA = Key.builder()
				.type(TypeTokens.DOUBLE_VALUE_TOKEN)
				.id("adventurer_max_mana")
				.name("Mana")
				.query(DataQuery.of('.', "adventurer.max_mana"))
				.build();
		MANA = Key.builder()
				.type(TypeTokens.DOUBLE_VALUE_TOKEN)
				.id("adventurer_mana")
				.name("Mana")
				.query(DataQuery.of('.', "adventurer.mana"))
				.build();
		
		BALANCE = Key.builder()
				.type(TypeTokens.INTEGER_VALUE_TOKEN)
				.id("adventurer_balance")
				.name("Balance")
				.query(DataQuery.of('.', "adventurer.balance"))
				.build();
		
		BACKPACK_DATA = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("adventurer_backpack")
				.name("Backpack")
				.query(DataQuery.of('.', "adventurer.backpack.data"))
				.build();
		BACKPACK_SIZE = Key.builder()
				.type(TypeTokens.INTEGER_VALUE_TOKEN)
				.id("adventurer_backpack_size")
				.name("Backpack Size")
				.query(DataQuery.of('.', "adventurer.backpack.size"))
				.build();
		
		MOUNT = Key.builder()
				.type(new TypeToken<Value<EntitySnapshot>>(){ private static final long serialVersionUID = 1L; })
				.id("adventurer_mana")
				.name("Mana")
				.query(DataQuery.of('.', "adventurer.status.mana"))
				.build();
		
    }
}
