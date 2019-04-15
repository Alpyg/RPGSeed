package io.alpyg.rpg.npcs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ninja.leaping.configurate.ConfigurationNode;

public class NpcConfig {

	public static Map<String, NpcConfig> npcs = new HashMap<String, NpcConfig>();
	
	private String internalName;
	private Text displayName;
	private boolean invulnerable;
	private String quest;
	
	public NpcConfig(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
		this.displayName = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Display").getString());
		this.invulnerable = config.getNode("Invulnerable").getBoolean(true);
		this.quest = config.getNode("Quest").getString();
		
		npcs.put(internalName, this);
	}
	
	public void spawnNpc(Location<World> location) {
		new Npc(location, this);
	}
	
	public String getInternalName() {
		return internalName;
	}

	public Text getDisplayName() {
		return displayName;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public String getQuest() {
		return quest;
	}
	
	public static Supplier<Collection<String>> getNpcs() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return npcs.keySet();
			}
			
		};
	}
	
	public static Function<String, NpcConfig> getNpc() {
		return new Function<String, NpcConfig>() {

			@Override
			public NpcConfig apply(String t) {
				return npcs.get(t);
			}
			
		};
	}
	
}
