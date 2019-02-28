package io.alpyg.rpg.damage;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.Seed;

public class DamageIndicator {

	public DamageIndicator(Location<World> location, double damage) {
		World world = location.getExtent();
		Entity damageIndicator = world.createEntity(EntityTypes.ARMOR_STAND, new Vector3d(location.getPosition().add(new Vector3d(0f, 1f, 0f))));

		damageIndicator.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		damageIndicator.offer(Keys.DISPLAY_NAME, Text.of(TextColors.RED, "-", Math.round(damage)));
		damageIndicator.offer(Keys.ARMOR_STAND_IS_SMALL, true);
		damageIndicator.offer(Keys.HAS_GRAVITY, true);
		damageIndicator.offer(Keys.INVISIBLE, true);

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			world.spawnEntity(damageIndicator);
		}
		
		Task.builder().execute(() -> damageIndicator.remove()).delay(500,  TimeUnit.MILLISECONDS).submit(Seed.plugin);
	}
}
