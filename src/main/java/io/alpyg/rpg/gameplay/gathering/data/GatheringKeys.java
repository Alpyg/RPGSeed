package io.alpyg.rpg.gameplay.gathering.data;

import java.util.UUID;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

import com.google.common.reflect.TypeToken;

public class GatheringKeys {

	public static Key<Value<String>> ID;
	public static Key<Value<String>> TYPE;
	public static Key<Value<String>> MATERIAL;
	public static Key<Value<String>> TOOL;
	public static Key<MapValue<UUID, Integer>> USES;

    public static void registerKeys() {
    	
    	ID = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("gathering_node")
				.name("Gathering Node")
				.query(DataQuery.of('.', "gathering.node"))
				.build();
		TYPE = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("gathering_type")
				.name("Gathering Type")
				.query(DataQuery.of('.', "gathering.type"))
				.build();
		MATERIAL = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("gathering_material")
				.name("Gathering Material")
				.query(DataQuery.of('.', "gathering.material"))
				.build();
		TOOL = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("gathering_tool")
				.name("Gathering Tool")
				.query(DataQuery.of('.', "gathering.tool"))
				.build();
		USES = Key.builder()
				.type(new TypeToken<MapValue<UUID, Integer>>() { private static final long serialVersionUID = -1; })
				.id("gathering_uses")
				.name("Gathering Uses")
				.query(DataQuery.of('.', "gathering.uses"))
				.build();
		
	}
}
