package io.alpyg.rpg.adventurer;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class AdventurerCommands {
	
	public static CommandSpec getStats = CommandSpec.builder()
		    .description(Text.of("Get Stats"))
		    .permission("rpgs.stats.get")
		    .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player p = (Player) args.getOne("Player").get();
	        	
	        	if (p.get(AdventurerKeys.STATS).isPresent()) {
	        		src.sendMessage(Text.of(TextColors.AQUA, "-----=====   ", TextColors.DARK_AQUA, p.getName(), TextColors.AQUA, "   =====-----"));
	        		src.sendMessage(Text.of(TextColors.WHITE, "Total Points - " , p.get(AdventurerKeys.STATS).get().total_points));
	        		src.sendMessage(Text.of(TextColors.WHITE, "Points - " , p.get(AdventurerKeys.STATS).get().points));
	        		src.sendMessage(Text.of(TextColors.DARK_RED, "Vitality - " , p.get(AdventurerKeys.STATS).get().vitality));
	        		src.sendMessage(Text.of(TextColors.GOLD, "Strength - " , p.get(AdventurerKeys.STATS).get().strength));
	        		src.sendMessage(Text.of(TextColors.DARK_GRAY, "Defence - " , p.get(AdventurerKeys.STATS).get().defence));
	        		src.sendMessage(Text.of(TextColors.GRAY, "Agility - " , p.get(AdventurerKeys.STATS).get().agility));
	        		src.sendMessage(Text.of(TextColors.DARK_AQUA, "Magic - " , p.get(AdventurerKeys.STATS).get().magic));
	        	}
	        	
	        	return CommandResult.success();
	        })
	        .build();

	
	public static CommandSpec setStats = CommandSpec.builder()
		    .description(Text.of("Set Stats"))
		    .permission("rpgs.stats.set")
		    .arguments(GenericArguments.player(Text.of("Player")),
		    		GenericArguments.choices(Text.of("Stat"), AdventurerStatsMenu.getStats(), AdventurerStatsMenu.getStat(), false),
		    		GenericArguments.integer(Text.of("Amount")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player p = (Player) args.getOne("Player").get();
	        	String stat = (String) args.getOne("Stat").get();
	        	int amount = (int) args.getOne("Amount").get();
	        	
	        	src.sendMessage(Text.of(TextColors.DARK_AQUA, p.getName(), "'s ",TextColors.AQUA,
	        			stat, TextColors.DARK_AQUA, " stat has been set to ", TextColors.AQUA,
	        			amount, TextColors.DARK_AQUA, "."));
	        	
	        	AdventurerStats.setStat(p, stat, amount);
	        	return CommandResult.success();
	        })
	        .build();

	
	public static CommandSpec resetStats = CommandSpec.builder()
		    .description(Text.of("Reset Stats"))
		    .permission("rpgs.stats.reset")
		    .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player p = (Player) args.getOne("Player").get();
	        	
	        	p.get(AdventurerKeys.STATS).get().total_points = 0;
	        	p.get(AdventurerKeys.STATS).get().points = 0;
	        	p.get(AdventurerKeys.STATS).get().vitality = 0;
	        	p.get(AdventurerKeys.STATS).get().strength = 0;
	        	p.get(AdventurerKeys.STATS).get().defence = 0;
	        	p.get(AdventurerKeys.STATS).get().agility = 0;
	        	p.get(AdventurerKeys.STATS).get().magic = 0;
	        	
	        	p.sendMessage(Text.of(TextColors.DARK_AQUA, "Your stats have been reset."));
	        	AdventurerStats.updatePlayerStats(p);
	        	return CommandResult.success();
	        })
	        .build();

	
	public static CommandSpec reallocateStats = CommandSpec.builder()
		    .description(Text.of("Reallocate Stats"))
		    .permission("rpgs.stats.reallocate")
		    .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player p = (Player) args.getOne("Player").get();
	        	
	        	p.get(AdventurerKeys.STATS).get().points = p.get(AdventurerKeys.STATS).get().total_points;
	        	p.get(AdventurerKeys.STATS).get().vitality = 0;
	        	p.get(AdventurerKeys.STATS).get().strength = 0;
	        	p.get(AdventurerKeys.STATS).get().defence = 0;
	        	p.get(AdventurerKeys.STATS).get().agility = 0;
	        	p.get(AdventurerKeys.STATS).get().magic = 0;
	        	
	        	p.sendMessage(Text.of(TextColors.DARK_AQUA, "Your stats have been reallocated."));
	        	AdventurerStats.updatePlayerStats(p);
	        	return CommandResult.success();
	        })
	        .build();

	
	public static CommandSpec statsCommand = CommandSpec.builder()
		    .description(Text.of("Status Commands"))
		    .permission("rpgs.stats")
		    .child(setStats, "set")
		    .child(getStats, "get")
		    .child(resetStats, "reset")
		    .child(reallocateStats, "reallocate")
		    
	        .executor((CommandSource src, CommandContext args) -> {
	        	return CommandResult.success();
	        })
	        .build();
}
