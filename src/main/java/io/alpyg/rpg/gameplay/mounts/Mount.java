package io.alpyg.rpg.gameplay.mounts;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HorseColors;
import org.spongepowered.api.data.type.HorseStyles;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.Rpgs;

public class Mount {

	@Listener
	public void onMount(RideEntityEvent.Mount e, @First Player player) {
		if (e.getTargetEntity() instanceof Human) e.setCancelled(true);
	}
	
	@Listener
	public void onMount(RideEntityEvent.Dismount e, @First Player player) {
		Task.builder().delay(100, TimeUnit.MILLISECONDS).execute(new Runnable() {
			
			@Override
			public void run() {
				e.getTargetEntity().remove();
			}
			
		}).submit(Rpgs.plugin);
	}
	
	public static void summonMount(Player p) {
//		Entity stand = p.getLocation().getExtent().createEntity(EntityTypes.ARMOR_STAND, p.getLocation().getPosition());
//		
//		Equipable entityEquipment = (Equipable) stand;
//		entityEquipment.equip(EquipmentTypes.HEADWEAR, ItemStack.of(ItemTypes.SKULL));
//		entityEquipment.equip(EquipmentTypes.MAIN_HAND, ItemStack.of(ItemTypes.BANNER));
//		entityEquipment.equip(EquipmentTypes.OFF_HAND, ItemStack.of(ItemTypes.BANNER));
//		
//		stand.offer(Keys.DISPLAY_NAME, Text.of("lol"));
//		stand.offer(Keys.CUSTOM_NAME_VISIBLE, true);
//		stand.offer(Keys.RIGHT_ARM_ROTATION, new Vector3d(190, 330, 0));
//		stand.offer(Keys.LEFT_ARM_ROTATION, new Vector3d(190, -330, 0));
//		stand.offer(Keys.ARMOR_STAND_HAS_ARMS, true);
//		stand.offer(Keys.ARMOR_STAND_IS_SMALL, true);
//		stand.offer(Keys.INVISIBLE, true);
//		
//		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
//			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
//			p.getLocation().getExtent().spawnEntity(stand);
//		}
//
//		stand.addPassenger(p);
		
		Entity mount = p.getLocation().getExtent().createEntity(EntityTypes.HORSE, p.getLocation().getPosition());
		
		mount.offer(Keys.HORSE_STYLE, HorseStyles.NONE);
		mount.offer(Keys.HORSE_COLOR, HorseColors.BLACK);
		mount.offer(Keys.TAMED_OWNER, Optional.of(p.getUniqueId()));
		((Carrier) mount).getInventory().offer(ItemStack.of(ItemTypes.SADDLE));
		
		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			p.getLocation().getExtent().spawnEntity(mount);
		}

		mount.addPassenger(p);
	}
	
	public ItemStack mountItem(Player player) {
		ItemStack saddle = ItemStack.of(ItemTypes.SADDLE);
		saddle.offer(Keys.DISPLAY_NAME, Text.of(TextColors.AQUA, "Summon Mount"));
		
		ArrayList<Text> lore = new ArrayList<Text>();
		lore.add(Text.of(""));
		lore.add(Text.of(TextColors.GOLD, "Speed: ", TextColors.WHITE));
		saddle.offer(Keys.ITEM_LORE, lore);
		
		return saddle;
	}

}
