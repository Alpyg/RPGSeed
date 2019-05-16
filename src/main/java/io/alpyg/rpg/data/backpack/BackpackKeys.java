package io.alpyg.rpg.data.backpack;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class BackpackKeys {
	
	public static Key<Value<Integer>> SIZE;
	public static Key<Value<String>> DATA;
	
	public static void registerKeys() {

		SIZE = Key.builder()
				.type(TypeTokens.INTEGER_VALUE_TOKEN)
				.id("backpack_size")
				.name("Backpack Size")
				.query(DataQuery.of('.', "backpack.size"))
				.build();
		DATA = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("backpack_data")
				.name("Backpack Data")
				.query(DataQuery.of('.', "backpack.data"))
				.build();
		
	}

}
