package io.alpyg.rpg.gameplay.shop;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.gameplay.shop.data.ShopData;
import io.alpyg.rpg.gameplay.shop.data.ShopKeys;

public abstract class ShopKeeper {

	public static void spawn(Location<World> location, ShopConfig config) {
		Entity entity = location.getExtent().createEntity(EntityTypes.VILLAGER, location.getPosition());

		entity.offer(entity.getOrCreate(ShopData.class).get());
		entity.offer(Keys.DISPLAY_NAME, Text.of(TextColors.AQUA, config.getInternalName()));
		entity.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		entity.offer(Keys.INVULNERABLE, true);
		entity.offer(Keys.PERSISTS, true);
		entity.offer(ShopKeys.SHOP_ID, config.getInternalName());
		entity.offer(ShopKeys.SHOP_DATA, config.getShopData());

		Rpgs.getLogger().info(config.getShopData());
		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(entity);
		}
	}
	
}
