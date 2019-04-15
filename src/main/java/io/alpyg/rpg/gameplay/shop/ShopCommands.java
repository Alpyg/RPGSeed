package io.alpyg.rpg.gameplay.shop;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.utils.ConfigLoader;

public class ShopCommands {

	public static CommandSpec spawn = CommandSpec.builder()
			.description(Text.of("Spawn Shop Keeper"))
		    .permission("rpgs.shop.spawn")
			.arguments(GenericArguments.onlyOne(GenericArguments.choices(Text.of("ShopKeeper"), ShopConfig.getShops(), ShopConfig.getShop(), true)))
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	if (!(src instanceof Player)) return CommandResult.empty();
	        	
        		Player player = (Player) src;
        		ShopConfig shop = (ShopConfig) args.getOne("ShopKeeper").get();
        		shop.spawnShopKeeper(player.getLocation());
	        	player.sendMessage(Text.of(TextColors.GREEN, "Spawned Shop Keeper ", TextColors.WHITE, shop.getInternalName(), "."));
	        	
	        	return CommandResult.success();	
	        })
	        .build();

	public static CommandSpec reload = CommandSpec.builder()
			.description(Text.of("Reload Shops"))
		    .permission("rpgs.shop.reload")
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	ShopConfig.shops.clear();
	        	ConfigLoader.loadConfig("Shops");
	        	
	        	src.sendMessage(Text.of(TextColors.GREEN, "Shops Reloaded."));
	        	
	        	return CommandResult.success();	
	        })
	        .build();
	
	public static CommandSpec shopCommand = CommandSpec.builder()
			.description(Text.of("Shop Commands"))
		    .permission("rpgs.shop")
		    .child(reload, "reload")
		    .child(spawn, "spawn")
		    
	        .executor((CommandSource src, CommandContext args) -> {
	        	
	        	return CommandResult.success();	
	        })
	        .build();

}
