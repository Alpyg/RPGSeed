package io.alpyg.rpg.adventurer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.data.adventurer.AdventurerKeys;

public class AdventurerStatsMenu {

	public static List<Integer> statusSlots = (List<Integer>) Arrays.asList( 11, 21, 13, 23, 15 );
	private static HashMap<String, String> stats = new HashMap<String, String>();
	
	public static void openStatusMenu(Player p) {
		Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.of(9, 4))
				.property(InventoryTitle.of(Text.of("Adventurer Status - ", p.get(AdventurerKeys.STATS).get().points, " Points")))
				.build(Rpgs.plugin);
		
		fillStatusMenu(inv, p);
		
		p.openInventory(inv);
	}
	
	public static boolean onStatusItemClicked(Player p, int i) {
		if (p.get(AdventurerKeys.STATS).get().points < 1) {
			p.sendMessage(Text.of(TextColors.RED, "You do not have enough status points."));
			return false;
		}
		
		if (i == 11)
			p.get(AdventurerKeys.STATS).get().vitality++;
		else if (i == 21)
			p.get(AdventurerKeys.STATS).get().strength++;
		else if (i == 13)
			p.get(AdventurerKeys.STATS).get().defence++;
		else if (i == 23)
			p.get(AdventurerKeys.STATS).get().agility++;
		else if (i == 15)
			p.get(AdventurerKeys.STATS).get().magic++;
		p.get(AdventurerKeys.STATS).get().points--;
		AdventurerStats.updatePlayerStats(p);
		
		return true;
	}
	
	public static void spawnAdventurerStatusEntity(Location<World> location) {
		World world = location.getExtent();
		Entity adventurerStatus = world.createEntity(EntityTypes.ARMOR_STAND, new Vector3d(location.getPosition().add(new Vector3d(0f, 1f, 0f))));

		adventurerStatus.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		adventurerStatus.offer(Keys.DISPLAY_NAME, Text.of(TextColors.DARK_AQUA, "Adventurer Status"));
		adventurerStatus.offer(Keys.ARMOR_STAND_IS_SMALL, true);
		adventurerStatus.offer(Keys.HAS_GRAVITY, true);
		adventurerStatus.offer(Keys.INVISIBLE, true);

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			world.spawnEntity(adventurerStatus);
		}
	}
	
	private static void fillStatusMenu(Inventory inv, Player p) {
		inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(11)))
			.set(createStatusItem(ItemTypes.WOOL, DyeColors.RED, Text.of(TextColors.DARK_RED, "Vitality"), Text.of(TextColors.RED, p.get(AdventurerKeys.STATS).get().vitality, "/100")));
		inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(21)))
			.set(createStatusItem(ItemTypes.WOOL, DyeColors.YELLOW, Text.of(TextColors.GOLD, "Strength"), Text.of(TextColors.YELLOW, p.get(AdventurerKeys.STATS).get().strength, "/100")));
		inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(13)))
			.set(createStatusItem(ItemTypes.WOOL, DyeColors.GRAY, Text.of(TextColors.DARK_GRAY, "Defence"), Text.of(TextColors.GRAY, p.get(AdventurerKeys.STATS).get().defence, "/100")));
		inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(23)))
			.set(createStatusItem(ItemTypes.WOOL, DyeColors.WHITE, Text.of(TextColors.GRAY, "Agility"), Text.of(TextColors.WHITE, p.get(AdventurerKeys.STATS).get().agility, "/100")));
		inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(15)))
			.set(createStatusItem(ItemTypes.WOOL, DyeColors.BLUE, Text.of(TextColors.DARK_AQUA, "Magic"), Text.of(TextColors.AQUA, p.get(AdventurerKeys.STATS).get().magic, "/100")));
	}

	private static ItemStack createStatusItem(ItemType itemType, DyeColor dyeColor, Text text, Text lore) {
		ItemStack itemStack = ItemStack.of(itemType);
		itemStack.offer(Keys.DISPLAY_NAME, text);
		ArrayList<Text> itemLore = new ArrayList<Text>();
		itemLore.add(lore);
		itemStack.offer(Keys.ITEM_LORE, itemLore);
		itemStack.offer(Keys.DYE_COLOR, dyeColor);
		return itemStack;
	}
	
	public static Supplier<Collection<String>> getStats() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return stats.keySet();
			}
			
		};
	}
	
	public static Function<String, String> getStat() {
		return new Function<String, String>() {

			@Override
			public String apply(String t) {
				return stats.get(t);
			}
			
		};
	}
	
	static {
		stats.put("Points", "Points");
		stats.put("Vitality", "Vitality");
		stats.put("Strength", "Strength");
		stats.put("Defence", "Defence");
		stats.put("Agility", "Agility");
		stats.put("Magic", "Magic");
	}
}
