package io.alpyg.rpg.gameplay.gathering;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.utils.ConfigLoader;

public class GatherCommands {

	public static CommandSpec set = CommandSpec.builder()
			.description(Text.of("Set Gathering Node"))
			.arguments(GenericArguments.onlyOne(GenericArguments.choices(Text.of("Node"), GatherConfig.getNodes(), GatherConfig.getNode(), true)))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
        		Player player = (Player) src;
        		
        		new GatherNode(player.getLocation(), (GatherConfig) args.getOne("Node").get());
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec reload = CommandSpec.builder()
			.description(Text.of("Set Gathering Node"))
	        .executor((CommandSource src, CommandContext args) -> { 

	        	GatherConfig.nodes.clear();
	        	ConfigLoader.loadConfig("Gathering");
	        	
	        	return CommandResult.success();	
	        })
	        .build();
	
	public static CommandSpec gatheringCommand = CommandSpec.builder()
			.description(Text.of("Gathering Commands"))
		    .child(reload, "reload")
		    .child(set, "set")
		    
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
