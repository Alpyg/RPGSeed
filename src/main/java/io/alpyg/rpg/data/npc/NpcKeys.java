package io.alpyg.rpg.data.npc;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class NpcKeys {

	public static Key<Value<String>> ID;
	public static Key<Value<String>> QUEST;

    public static void registerKeys() {

    	ID = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("npc_id")
				.name("Npc ID")
				.query(DataQuery.of('.', "npc.id"))
				.build();
    	
    	QUEST = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("npc_quest")
				.name("Npc Quest")
				.query(DataQuery.of('.', "npc.quest"))
				.build();
		
    }
    
}
