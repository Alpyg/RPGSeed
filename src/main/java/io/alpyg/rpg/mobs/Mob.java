package io.alpyg.rpg.mobs;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.data.mob.MobData;
import io.alpyg.rpg.data.mob.MobKeys;
import io.alpyg.rpg.utils.ItemStackUtils;

public class Mob {
	
	public Mob(Location<World> location, MobConfig smd) {
		Entity entity = location.getExtent().createEntity(smd.entityType, location.getPosition());

		// Setting Custom Data
		entity.offer(entity.getOrCreate(MobData.class).get());
		entity.offer(MobKeys.ID, smd.internalName);
		
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
			entityEquipment.equip(EquipmentTypes.MAIN_HAND, ItemStackUtils.getItemStack(smd.mainHand).orElse(ItemStack.empty()));
			entityEquipment.equip(EquipmentTypes.OFF_HAND, ItemStackUtils.getItemStack(smd.offHand).orElse(ItemStack.empty()));
			entityEquipment.equip(EquipmentTypes.HEADWEAR, ItemStackUtils.getItemStack(smd.head).orElse(ItemStack.empty()));
			entityEquipment.equip(EquipmentTypes.CHESTPLATE, ItemStackUtils.getItemStack(smd.chest).orElse(ItemStack.empty()));
			entityEquipment.equip(EquipmentTypes.LEGGINGS, ItemStackUtils.getItemStack(smd.legs).orElse(ItemStack.empty()));
			entityEquipment.equip(EquipmentTypes.BOOTS, ItemStackUtils.getItemStack(smd.feet).orElse(ItemStack.empty()));
		}

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(entity);
		}
	}
	
}
