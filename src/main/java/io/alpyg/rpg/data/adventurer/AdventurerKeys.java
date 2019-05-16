package io.alpyg.rpg.data.adventurer;

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
		
		MOUNT = Key.builder()
				.type(new TypeToken<Value<EntitySnapshot>>(){ private static final long serialVersionUID = 1L; })
				.id("adventurer_mana")
				.name("Mana")
				.query(DataQuery.of('.', "adventurer.status.mana"))
				.build();
		
    }
}
