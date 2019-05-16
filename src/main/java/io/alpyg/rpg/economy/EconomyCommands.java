package io.alpyg.rpg.economy;

import java.math.BigDecimal;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransferResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.data.adventurer.AdventurerKeys;

public class EconomyCommands {
	
	public static CommandSpec setCommand = CommandSpec.builder()
			.description(Text.of("Set Command"))
			.permission("rpgs.economy.set")
			.arguments(
		    		GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.integer(Text.of("Amount")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player player = (Player) args.getOne("Player").get();
        		BigDecimal amount = BigDecimal.valueOf((int) args.getOne("Amount").get());
        		
        		if (amount.compareTo(BigDecimal.ZERO) > 0) {
        			RpgsAccount playerAccount = (RpgsAccount) Rpgs.getEconomy().getOrCreateAccount(player.getUniqueId()).get();
        			
        			Cause cause = Cause.builder()
        					.append(Rpgs.getContainer())
        					.build(EventContext.empty());
        			
        			TransactionResult transactionResult = playerAccount.setBalance(Rpgs.getEconomy().getDefaultCurrency(), amount, cause);
        			
        			if (transactionResult.getResult() == ResultType.SUCCESS) {
        				src.sendMessage(Text.of(TextColors.AQUA, player.getName(), TextColors.GREEN, "'s balance set to ", TextColors.AQUA, amount.intValue(), TextColors.GREEN, "."));
        			} else {
        				throw new CommandException(Text.of("An unexpected error occured!"));
        			}
        		} else {
        			throw new CommandException(Text.of("Invalid Amount: has to be positive!"));
        		}
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec payCommand = CommandSpec.builder()
			.description(Text.of("Pay Command"))
		    .permission("rpgs.economy.pay")
		    .arguments(
		    		GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.integer(Text.of("Amount")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (src instanceof Player)
	        	{
	        		Player sender = (Player) src;
	        		Player recipient = (Player) args.getOne("Player").get();
	        		BigDecimal amount = BigDecimal.valueOf((int) args.getOne("Amount").get());
	        		
	        		if (sender.getUniqueId().equals(recipient.getUniqueId()))
	        			throw new CommandException(Text.of("You can't pay yourself."));
	        		
	        		if (amount.compareTo(BigDecimal.ZERO) > 0) {
	        			RpgsAccount senderAccount = (RpgsAccount) Rpgs.getEconomy().getOrCreateAccount(sender.getUniqueId()).get();
	        			RpgsAccount recipientAccount = (RpgsAccount) Rpgs.getEconomy().getOrCreateAccount(recipient.getUniqueId()).get();
	        			
	        			Cause cause = Cause.builder()
	        					.append(Rpgs.getContainer())
	        					.build(EventContext.empty());
	        			
	        			TransferResult transferResult = senderAccount.transfer(recipientAccount, Rpgs.getEconomy().getDefaultCurrency(), amount, cause);
	        			
	        			if (transferResult.getResult() == ResultType.SUCCESS) {
	        				sender.sendMessage(Text.of(TextColors.GREEN, "You have sent ", TextColors.AQUA, amount.intValue(), TextColors.GREEN, " to ", TextColors.AQUA, recipient.getName(), TextColors.GREEN, "."));
	        				recipient.sendMessage(Text.of(TextColors.GREEN, "You have recieved ", TextColors.AQUA, amount.intValue(), TextColors.GREEN, " from ", TextColors.AQUA, sender.getName(), TextColors.GREEN, "."));
	        			} else if (transferResult.getResult() == ResultType.ACCOUNT_NO_FUNDS) {
	        				throw new CommandException(Text.of("Insufficient Funds!"));
	        			} else {
	        				throw new CommandException(Text.of("An unexpected error occured!"));
	        			}
	        			
	        		} else {
	        			throw new CommandException(Text.of("Invalid Amount: has to be positive!"));
	        		}
	        	} else {
	        		throw new CommandException(Text.of("Player only command!"));
	        	}
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec balCommand = CommandSpec.builder()
			.description(Text.of("Check your balance."))
		    .permission("rpgs.economy.balance")
	        .executor((CommandSource src, CommandContext args) -> {

	        	Player player = (Player) src;
	        	player.sendMessage(Text.of(TextColors.GREEN, "Balance: ", RpgsEconomy.calculateCurrency(player.get(AdventurerKeys.BALANCE).get())));
	        	
	        	return CommandResult.success();
	        })
	        .build();
}
