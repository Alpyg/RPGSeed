package io.alpyg.rpg.chat;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.utils.ChatIcons;

public class WhisperCommands {
	
	public static CommandSpec whisperCommand = CommandSpec.builder()
			.description(Text.of("Whisper"))
		    .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
		    		GenericArguments.string(Text.of("Message")))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player player = (Player) args.getOne("Player").get();
	        	Text text = Text.of(TextColors.DARK_PURPLE, " ", src.getName(), ChatIcons.WHISPER, TextColors.LIGHT_PURPLE, (String) args.getOne("Message").get());

                player.playSound(SoundTypes.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategories.PLAYER, player.getPosition(), 1, 2);
	        	player.sendMessage(text);
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
