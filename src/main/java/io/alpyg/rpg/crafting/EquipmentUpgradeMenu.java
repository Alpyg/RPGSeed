package io.alpyg.rpg.crafting;

import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.Rpgs;

public class EquipmentUpgradeMenu {
	
	public static List<Integer> equipmentSlots = (List<Integer>) Arrays.asList( 1, 3, 7 );
	private static ItemStack filler = ItemStack.of(ItemTypes.STAINED_GLASS_PANE);
	
	public static void openEquipmentUpgrade(Player v) {
		Inventory inv = Inventory.builder().of(InventoryArchetypes.CHEST)
				.property(InventoryDimension.of(9, 1))
				.property(InventoryTitle.of(Text.of("Equipment Upgrade")))
				.build(Rpgs.plugin);
		fillInventory(inv);
		v.openInventory(inv);
	}
	
	private static void fillInventory(Inventory inv) {
		filler.offer(Keys.DYE_COLOR, DyeColors.BLACK);
		filler.offer(Keys.DISPLAY_NAME, Text.of());
		
		for (int i = 0; i < 9; i++)
			if (!equipmentSlots.contains(i))
				inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i)))
					.set(filler);
	}
}
