package io.alpyg.rpg.economy;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class EconomyCommands {

	public static CommandSpec payCommand = CommandSpec.builder()
			.description(Text.of("Pay Command"))
		    .permission("rpgs.economy.pay")
		    .arguments(
		    		GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.integer(Text.of("Amount")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec balCommand = CommandSpec.builder()
			.description(Text.of("Check your balance."))
		    .permission("rpgs.economy.balance")
	        .executor((CommandSource src, CommandContext args) -> {

	        	Player player = (Player) src;
	        	player.sendMessage(Text.of(TextColors.GREEN, "Balance: ", SeedEconomy.calculateCurrency(player.get(AdventurerKeys.BALANCE).get())));
	        	
	        	return CommandResult.success();
	        })
	        .build();
}
