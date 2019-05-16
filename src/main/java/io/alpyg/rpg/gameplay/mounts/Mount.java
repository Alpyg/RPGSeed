package io.alpyg.rpg.gameplay.mounts;

import java.util.ArrayList;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HorseColors;
import org.spongepowered.api.data.type.HorseStyles;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Mount {
	
	public static ItemStack MountItem;
	
	public static void summonMount(Player player) {		
		Entity mount = player.getLocation().getExtent().createEntity(EntityTypes.HORSE, player.getLocation().getPosition());
		
		mount.offer(Keys.HORSE_STYLE, HorseStyles.NONE);
		mount.offer(Keys.HORSE_COLOR, HorseColors.BLACK);
		mount.offer(Keys.TAMED_OWNER, Optional.of(player.getUniqueId()));
		((Carrier) mount).getInventory().offer(ItemStack.of(ItemTypes.SADDLE));
		
		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			player.getLocation().getExtent().spawnEntity(mount);
		}

		mount.addPassenger(player);
	}
	
	static {
		ItemStack saddle = ItemStack.of(ItemTypes.SADDLE);
		saddle.offer(Keys.DISPLAY_NAME, Text.of(TextColors.AQUA, "Summon Mount"));
		
		ArrayList<Text> lore = new ArrayList<Text>();
		lore.add(Text.of(""));
		lore.add(Text.of(TextColors.GOLD, "Speed: ", TextColors.WHITE));
		saddle.offer(Keys.ITEM_LORE, lore);
		
		MountItem = saddle;
	}

}
