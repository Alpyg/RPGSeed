package io.alpyg.rpg.effect;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.effect.effects.SphereEffect;

public class EffectCommands {

	public static CommandSpec effectCommand = CommandSpec.builder()
			.description(Text.of("Effect Commands"))
		    .permission("rpgs.effect")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	Player player = (Player) src;
	        	Effect.builder().effect(new SphereEffect(1)).particle(ParticleTypes.CRITICAL_HIT).location(player.getLocation()).build();
	        	
	        	return CommandResult.success();	
	        })
	        .build();
}
