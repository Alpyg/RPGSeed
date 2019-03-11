package io.alpyg.rpg.gameplay.fasttravel;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class FastTravelCommands {

	public static CommandSpec fasttravelTeleportCommand = CommandSpec.builder()
			.description(Text.of("Fast Travel Teleport Command"))
		    .permission("rpgs.fasttravel.teleport")
		    .arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Location"), FastTravel.getLocations(), FastTravel.getLocation(), true)))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
	        	Player player = (Player) src;
	        	
	        	if (!args.hasAny(Text.of("Location"))) {
	        		player.sendMessage(Text.of(TextColors.RED, "Usage: /ft tp <Location>"));
	        		return CommandResult.empty();
	        	}
	        	
	        	FastTravel.teleport(player, (FastTravelLocation) args.getOne(Text.of("Location")).get());
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec fasttravelListCommand = CommandSpec.builder()
			.description(Text.of("Fast Travel List Command"))
		    .permission("rpgs.fasttravel.list")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	src.sendMessage(Text.of(TextColors.GREEN, "Waypoints: ", TextColors.WHITE, FastTravel.getLocations().get()));
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec fasttravelCreateCommand = CommandSpec.builder()
			.description(Text.of("Fast Travel Create Command"))
		    .permission("rpgs.fasttravel.create")
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("Location"))))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();

	        	Player player = (Player) src;
	        	
	        	if (!args.hasAny(Text.of("Location"))) {
	        		player.sendMessage(Text.of(TextColors.RED, "Usage: /ft create <Location>"));
	        		return CommandResult.empty();
	        	}
	        	
	        	FastTravel.createFastTravelLocation((String) args.getOne("Location").get(), player.getTransform());
        		player.sendMessage(Text.of(TextColors.GREEN, "Waypoint ", TextColors.DARK_AQUA, (String) args.getOne("Location").get(), TextColors.GREEN, " created."));
	        	
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec fasttravelRemoveCommand = CommandSpec.builder()
			.description(Text.of("Fast Travel Remove Command"))
		    .permission("rpgs.fasttravel.remove")
		    .arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Location"), FastTravel.getLocations(), FastTravel.getLocation(), true)))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();

	        	Player player = (Player) src;
	        	
	        	if (!args.hasAny(Text.of("Location"))) {
	        		player.sendMessage(Text.of(TextColors.RED, "Usage: /ft remove <Location>"));
	        		return CommandResult.empty();
	        	}
	        	
	        	FastTravel.deleteFastTravelLocation((FastTravelLocation) args.getOne(Text.of("Location")).get());
        		player.sendMessage(Text.of(TextColors.GREEN, "Waypoint ", TextColors.DARK_AQUA, ((FastTravelLocation) args.getOne("Location").get()).getKey(), TextColors.GREEN, " removed."));
	        	
	        	
	        	return CommandResult.success();	
	        })
	        .build();
	
	public static CommandSpec fasttravelCommand = CommandSpec.builder()
			.description(Text.of("Fast Travel Command"))
		    .permission("rpgs.fasttravel")
		    .child(fasttravelTeleportCommand, "tp")
		    .child(fasttravelListCommand, "list")
		    .child(fasttravelCreateCommand, "create")
		    .child(fasttravelRemoveCommand, "remove")
		    
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
