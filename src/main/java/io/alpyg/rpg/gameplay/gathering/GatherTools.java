package io.alpyg.rpg.gameplay.gathering;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class GatherTools {

	private static ItemStack miningTool;
	private static ItemStack loggingTool;
	private static ItemStack harvestingTool;
	
	static {
		miningTool = ItemStack.builder().itemType(ItemTypes.IRON_PICKAXE).build();
		miningTool.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GRAY, "Mining Tool"));
		miningTool.offer(Keys.HIDE_ATTRIBUTES, true);
		
		loggingTool = ItemStack.builder().itemType(ItemTypes.IRON_AXE).build();
		loggingTool.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GRAY, "Logging Tool"));
		loggingTool.offer(Keys.HIDE_ATTRIBUTES, true);
		
		harvestingTool = ItemStack.builder().itemType(ItemTypes.IRON_HOE).build();
		harvestingTool.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GRAY, "Harvesting Tool"));
		harvestingTool.offer(Keys.HIDE_ATTRIBUTES, true);
	}
	
	public static ItemStack getMiningTool() {
		return miningTool;
	}
	
	public static ItemStack getLoggingTool() {
		return loggingTool;
	}
	
	public static ItemStack getHarvestingTool() {
		return harvestingTool;
	}
}
