package io.alpyg.rpg.gameplay.fasttravel;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.utils.VectorUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class FastTravel {

	public static Map<String, FastTravelLocation> locations = new HashMap<String, FastTravelLocation>();
	private static ConfigurationLoader<CommentedConfigurationNode> configLoader;
	private static ConfigurationNode fastTravelConfig;
	
	public static void loadFastTravelLocations() {
		try {
			File fastTravelFile = new File(Rpgs.configDir + File.separator + "FastTravel.conf");	// Load file
			if (fastTravelFile.createNewFile()) return;		// Create file if not exist and return
			
			configLoader = HoconConfigurationLoader.builder().setFile(fastTravelFile).build();
			fastTravelConfig = configLoader.load();
			
			for (ConfigurationNode fastTravelLocation : fastTravelConfig.getChildrenMap().values()) {
				locations.put(fastTravelLocation.getKey().toString(), new FastTravelLocation(fastTravelLocation));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean teleport(Player player, FastTravelLocation location) {
		return player.setLocationAndRotationSafely(location.getLocation(), location.getRotation());
	}
	
	public static void createFastTravelLocation(String location, Transform<World> transform) {
		try {
			fastTravelConfig.getNode(location, "World").setValue(transform.getExtent().getName());
			fastTravelConfig.getNode(location, "Position").setValue(TypeToken.of(Vector3d.class), transform.getPosition().toInt().toDouble().add(0.5, 0, 0.5));
			fastTravelConfig.getNode(location, "Yaw").setValue(VectorUtils.roundYaw(transform.getYaw()));
		} catch (ObjectMappingException e) {
			e.printStackTrace();
		}
		
		locations.put(location, new FastTravelLocation(fastTravelConfig.getNode(location)));
		saveConfig();
	}
	
	public static void deleteFastTravelLocation(FastTravelLocation location) {
		fastTravelConfig.removeChild(location.getKey());
		locations.remove(location.getKey());
		saveConfig();
	}
	
	private static void saveConfig() {
		try {
			configLoader.save(fastTravelConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Supplier<Collection<String>> getLocations() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return locations.keySet();
			}
			
		};
	}
	
	public static Function<String, FastTravelLocation> getLocation() {
		return new Function<String, FastTravelLocation>() {

			@Override
			public FastTravelLocation apply(String t) {
				return locations.get(t);
			}
			
		};
	}
}
