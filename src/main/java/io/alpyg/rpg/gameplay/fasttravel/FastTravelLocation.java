package io.alpyg.rpg.gameplay.fasttravel;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import ninja.leaping.configurate.ConfigurationNode;

public class FastTravelLocation {
	
	private final String key;
	private final Transform<World> transform;
	
	public FastTravelLocation(ConfigurationNode data) {
		this.key = data.getKey().toString();
		World world = Sponge.getServer().getWorld(data.getNode("World").getString()).get();
		Vector3d position = new Vector3d(data.getNode("Position").getNode("x").getFloat(), data.getNode("Position").getNode("y").getFloat(), data.getNode("Position").getNode("z").getFloat());
		Vector3d rotation = new Vector3d(0, data.getNode("Yaw").getFloat(), 0);
		this.transform = new Transform<>(world, position.toDouble(), rotation);
	}
	
	public String getKey() {
		return this.key;
	}
	
	public Location<World> getLocation() {
		return this.transform.getLocation();
	}
	
	public Vector3d getRotation() {
		return this.transform.getRotation();
	}
	
}
