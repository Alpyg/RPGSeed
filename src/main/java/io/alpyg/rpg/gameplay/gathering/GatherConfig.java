package io.alpyg.rpg.gameplay.gathering;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import ninja.leaping.configurate.ConfigurationNode;

public class GatherConfig {
	
	public static Map<String, GatherConfig> nodes = new HashMap<String, GatherConfig>();
	
	public String internalName;
	public ItemType type;
	public Text displayName;
	public String material;
	public String tool;
	public int uses;
	
	public GatherConfig(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
		this.type = setType(config.getNode("Type").getString());
		this.displayName = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Display").getString());
		this.material = config.getNode("Material").getString();
		this.tool = config.getNode("Tool").getString();
		this.uses = config.getNode("Uses").getInt();
		
		if (type != null)
			nodes.put(internalName, this);
	}
	
	private ItemType setType(String type) {
		if (Sponge.getRegistry().getType(ItemType.class, type).isPresent())
			return Sponge.getRegistry().getType(ItemType.class, type).get();
		return null;
	}
	
	public static Supplier<Collection<String>> getNodes() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return nodes.keySet();
			}
			
		};
	}
	
	public static Function<String, GatherConfig> getNode() {
		return new Function<String, GatherConfig>() {

			@Override
			public GatherConfig apply(String t) {
				return nodes.get(t);
			}
			
		};
	}
}
