package io.alpyg.rpg.gameplay.mounts;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class MountCommands {
	
	public static CommandSpec mountCommand = CommandSpec.builder()
			.description(Text.of("Mount Command"))
		    .permission("rpgs.mount")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
	        	Player player = (Player) src;
	        	player.getInventory().offer(Mount.MountItem);
	        	
	        	return CommandResult.success();	
	        })
	        .build();

}
