package io.alpyg.rpg.npcs;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.utils.ConfigLoader;

public class NpcCommands {

	public static CommandSpec spawn = CommandSpec.builder()
			.description(Text.of("Spawn Npc"))
		    .permission("rpgs.npcs.spawn")
			.arguments(GenericArguments.onlyOne(GenericArguments.choices(Text.of("Npc"), NpcConfig.getNpcs(), NpcConfig.getNpc(), true)))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
        		Player player = (Player) src;
        		NpcConfig npc = (NpcConfig) args.getOne("Npc").get();
        		npc.spawnNpc(player.getLocation());
	        	player.sendMessage(Text.of(TextColors.GREEN, "Spawned Npc ", npc.getDisplayName()));
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec reload = CommandSpec.builder()
			.description(Text.of("Reload Npcs"))
		    .permission("rpgs.npcs.reload")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	NpcConfig.npcs.clear();
	        	ConfigLoader.loadConfig("Npcs");
	        	
	        	src.sendMessage(Text.of(TextColors.GREEN, "Npcs Reloaded."));
	        	
	        	return CommandResult.success();	
	        })
	        .build();
	
	public static CommandSpec npcCommand = CommandSpec.builder()
			.description(Text.of("NPC Commands"))
		    .permission("rpgs.npcs")
		    .child(reload, "reload")
		    .child(spawn, "spawn")
		    
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	return CommandResult.success();	
	        })
	        .build();

}
