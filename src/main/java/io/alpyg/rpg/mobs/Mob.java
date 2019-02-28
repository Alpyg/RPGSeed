package io.alpyg.rpg.mobs;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.mobs.data.MobData;
import io.alpyg.rpg.mobs.data.MobKeys;

public class Mob {
	
	public Mob(Location<World> location, MobConfig smd) {
		Entity entity = location.getExtent().createEntity(smd.entityType, location.getPosition());

		// Setting Custom Data
		entity.offer(entity.getOrCreate(MobData.class).get());
		entity.offer(MobKeys.ID, smd.internalName);
		entity.offer(MobKeys.DISPLAY_NAME, smd.displayName);
		
		// Setting Data
		entity.offer(Keys.DISPLAY_NAME, Text.of(smd.displayName, TextColors.GOLD, " Lvl.", (int) smd.level));
		entity.offer(Keys.CUSTOM_NAME_VISIBLE, smd.optionAlwaysShowName);
		entity.offer(Keys.MAX_HEALTH, smd.health);
		entity.offer(Keys.HEALTH, smd.health);
		entity.offer(MobKeys.DAMAGE, smd.damage);
		entity.offer(MobKeys.DEFENCE, smd.defence);
		entity.offer(Keys.EXPERIENCE_LEVEL, (int) smd.level);
		entity.offer(Keys.PERSISTS, true);
		entity.offer(Keys.WALKING_SPEED, smd.movementSpeed);
		if(smd.optionIsBaby)
			entity.offer(Keys.IS_ADULT, false);
		
		// Setting Options
		entity.offer(Keys.IS_SILENT, smd.optionSilent);
		entity.offer(Keys.AI_ENABLED, !smd.optionNoAI);
		entity.offer(Keys.GLOWING, smd.optionGlowing);
		entity.offer(Keys.INVISIBLE, smd.optionInvincible);
		entity.offer(Keys.HAS_GRAVITY, !smd.optionNoGravity);
		
		// Setting Equipment
		if(entity instanceof Equipable) {
			Equipable entityEquipment = (Equipable) entity;
			if(ItemConfig.items.containsKey(smd.mainHand))
				entityEquipment.equip(EquipmentTypes.MAIN_HAND, ItemConfig.getItem().apply(smd.mainHand).getItemStack());
			if(ItemConfig.items.containsKey(smd.offHand))
				entityEquipment.equip(EquipmentTypes.OFF_HAND, ItemConfig.getItem().apply(smd.offHand).getItemStack());
			if(ItemConfig.items.containsKey(smd.head))
				entityEquipment.equip(EquipmentTypes.HEADWEAR, ItemConfig.getItem().apply(smd.head).getItemStack());
			if(ItemConfig.items.containsKey(smd.chest))
				entityEquipment.equip(EquipmentTypes.CHESTPLATE, ItemConfig.getItem().apply(smd.chest).getItemStack());
			if(ItemConfig.items.containsKey(smd.legs))
				entityEquipment.equip(EquipmentTypes.LEGGINGS, ItemConfig.getItem().apply(smd.legs).getItemStack());
			if(ItemConfig.items.containsKey(smd.feet))
				entityEquipment.equip(EquipmentTypes.BOOTS, ItemConfig.getItem().apply(smd.feet).getItemStack());
		}

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(entity);
		}
	}
	
}
