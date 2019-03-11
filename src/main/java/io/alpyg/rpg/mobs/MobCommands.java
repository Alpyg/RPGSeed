package io.alpyg.rpg.mobs;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.utils.ConfigLoader;

public class MobCommands {
	
	public static CommandSpec seedMobsReload = CommandSpec.builder()
		    .description(Text.of("Reload Mobs"))
		    .permission("rpgs.mobs.reload")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	src.sendMessage(Text.of(TextColors.GREEN, Text.of("Seed Mobs Reloaded")));

	        	MobConfig.mobs.clear();
	        	ConfigLoader.loadConfig("Mobs");
	        	
	        	return CommandResult.success();
	        })
	        .build();
	
	public static CommandSpec seedMobsSpawn = CommandSpec.builder()
		    .description(Text.of("Spawn Mob"))
		    .permission("rpgs.mobs.spawn")
		    .arguments(GenericArguments.onlyOne(GenericArguments.choices(Text.of("Mob"), MobConfig.getMobsConfig(), MobConfig.getMobConfig(), true)))
	        .executor((CommandSource src, CommandContext args) -> {

	        	if (!(src instanceof Player)) return CommandResult.empty();

	        	Player player = (Player) src;
	        	
	        	MobConfig mob = (MobConfig) args.getOne("Mob").get();
	        	player.sendMessage(Text.of(TextColors.GREEN, "Spawned ", mob.displayName));
	        	mob.spawnMob(player.getLocation());
	        	
	        	return CommandResult.success();
	        })
	        .build();
	
	public static CommandSpec seedMobsList = CommandSpec.builder()
		    .description(Text.of("List Mobs"))
		    .permission("rpgs.mobs.list")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	String list = MobConfig.mobs.keySet().toString();
	        	src.sendMessage(Text.of(TextColors.GREEN, "Mob List:"));
	        	src.sendMessage(Text.of(list.substring(1, list.length() - 1)));
	        	
	        	return CommandResult.success();
	        })
	        .build();

	public static CommandSpec seedMobsCommand = CommandSpec.builder()
		    .description(Text.of("Mobs Commands"))
		    .permission("rpgs.mobs")
		    .child(seedMobsReload, "Reload")
		    .child(seedMobsSpawn, "Spawn")
		    .child(seedMobsList, "List")

	        .executor((CommandSource src, CommandContext args) -> {
	        	return CommandResult.success();
	        })
	        .build();
}
