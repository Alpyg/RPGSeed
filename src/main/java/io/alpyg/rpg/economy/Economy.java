package io.alpyg.rpg.economy;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import io.alpyg.rpg.Seed;

public class Economy {
	
	public static void withdraw(EconomyService economy, Player player) {
		UniqueAccount account = economy.getOrCreateAccount(player.getUniqueId()).get();
		Account bank = economy.getOrCreateAccount("BANK").get();
		
		Seed.getLogger().warn(account.getBalances().toString());
		Seed.getLogger().warn(bank.getBalances().toString());
	}
	
	public static void deposit(EconomyService economy, Player player) {
//		UniqueAccount account = economy.getOrCreateAccount(player.getUniqueId()).get();
		
	}
	
	public static void transfer(EconomyService economy, Player sender, Player receiver) {
		
	}
	
}
