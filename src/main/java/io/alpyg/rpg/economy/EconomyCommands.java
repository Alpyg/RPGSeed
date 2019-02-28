package io.alpyg.rpg.economy;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

public class EconomyCommands {

	public static CommandSpec payCommand = CommandSpec.builder()
			.description(Text.of("Pay Command"))
		    .arguments(
		    		GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.integer(Text.of("Amount")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	// TODO pay
//	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec balCommand = CommandSpec.builder()
			.description(Text.of("Check your balance."))
	        .executor((CommandSource src, CommandContext args) -> {

	        	// TODO bal
	        	EconomyService economy = Sponge.getServiceManager().provide(EconomyService.class).get();
	        	Economy.withdraw(economy, (Player) src);
	        	
	        	return CommandResult.success();
	        })
	        .build();
}
