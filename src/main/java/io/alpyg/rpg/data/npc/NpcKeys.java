package io.alpyg.rpg.npcs.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class NpcKeys {

	public static Key<Value<String>> QUEST;

    public static void registerKeys() {
		
    	QUEST = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("npc_quest")
				.name("Npc Quest")
				.query(DataQuery.of('.', "npc.quest"))
				.build();
		
    }
    
}
