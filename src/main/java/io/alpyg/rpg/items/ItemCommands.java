package io.alpyg.rpg.items;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.utils.ConfigLoader;

public class ItemCommands {
	
	public static CommandSpec seedItemsReload = CommandSpec.builder()
		    .description(Text.of("Reload Items"))
		    .permission("rpgs.items.reload")
	        .executor((CommandSource src, CommandContext args) -> {
	        	src.sendMessage(Text.of(TextColors.GREEN, Text.of("Seed Items Reloaded")));

	        	ItemConfig.items.clear();
	        	ConfigLoader.loadConfig("Items");
	        	
	        	return CommandResult.success();
	        })
	        .build();
	
	public static CommandSpec seedItemsGive = CommandSpec.builder()
		    .description(Text.of("Give Item"))
		    .permission("rpgs.items.give")
		    .arguments(
		    		GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.onlyOne(GenericArguments.choices(Text.of("Item"), ItemConfig.getItems(), ItemConfig.getItem(), true)),
		    		GenericArguments.optionalWeak(GenericArguments.integer(Text.of("Amount")), 1))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player player = (Player) args.getOne("Player").get();
	        	Item item = (Item) args.getOne("Item").get();
	        	ItemStack itemStack = ((Item) args.getOne("Item").get()).getItemStack();
	        	itemStack.setQuantity((int) args.getOne("Amount").get());
	        	
		        src.sendMessage(Text.of(TextColors.GREEN, Text.of("Given ", player.getName(), " x", (int) args.getOne("Amount").get(), " ", item.getDisplayName())));
		        player.getInventory().offer(itemStack);
	        	
	        	return CommandResult.success();
	        })
	        .build();
	
	public static CommandSpec seedItemsList = CommandSpec.builder()
		    .description(Text.of("List Items"))
		    .permission("rpgs.items.list")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	String list = ItemConfig.getItems().get().toString();
	        	src.sendMessage(Text.of(TextColors.GREEN, "Item List:"));
	        	src.sendMessage(Text.of(list.substring(1, list.length() - 1)));
	        	
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec seedItemsCommand = CommandSpec.builder()
		    .description(Text.of("Items Commands"))
		    .permission("rpgs.items")
		    .child(seedItemsReload, "Reload")
		    .child(seedItemsGive, "Give")
		    .child(seedItemsList, "List")

	        .executor((CommandSource src, CommandContext args) -> {
	        	return CommandResult.success();
	        })
	        .build();
}
