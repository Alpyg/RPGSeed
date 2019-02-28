package io.alpyg.rpg.effect;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.quests.QuestManager;

public class EffectCommands {

	public static CommandSpec effectCommand = CommandSpec.builder()
			.description(Text.of("Effect Commands"))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
//	        	Player player = (Player) src;
//	        	new SphereEffect(ParticleTypes.CRITICAL_HIT, player);
	        	
//	        	Mount.summonMount(player);
	        	
	        	QuestManager.reload();
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
