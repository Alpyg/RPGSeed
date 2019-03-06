package io.alpyg.rpg.quests;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class QuestCommands {

	public static CommandSpec questJournal = CommandSpec.builder()
			.description(Text.of("Open Quest Journal"))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
	        	Player player = (Player) src;
	        	QuestManager.openQuestList(player);
	        	player.getInventory().offer(QuestManager.journal);
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec questReload = CommandSpec.builder()
			.description(Text.of("Reload Quests"))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	QuestManager.loadQuests();
	        	src.sendMessage(Text.of(TextColors.GREEN, "Quests reloaded."));
	        	
	        	return CommandResult.success();	
	        })
	        .build();
	
	
	public static CommandSpec questCommand = CommandSpec.builder()
			.description(Text.of("Quest Commands"))
			.child(questReload, "reload")
			.child(questJournal, "journal")
			
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	return CommandResult.success();	
	        })
	        .build();

}
