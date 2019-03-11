package io.alpyg.rpg.gameplay.backpack;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.gameplay.backpack.Backpacks;

public class BackpackCommands {

	public static CommandSpec backpackCommand = CommandSpec.builder()
			.description(Text.of("Backpack Commands"))
		    .permission("rpgs.backpacks")
		    .arguments(GenericArguments.optional(GenericArguments.player(Text.of("Player"))))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
        		if(!args.hasAny(Text.of("Player"))) {
        			Backpacks.openBackpack((Player) src, (Player) src);
        		} else {
        			Backpacks.openBackpack((Player) src, (Player) args.getOne("Player").get());
        		}
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
