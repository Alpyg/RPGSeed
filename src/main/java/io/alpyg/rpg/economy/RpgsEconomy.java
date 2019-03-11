package io.alpyg.rpg.economy;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.context.ContextCalculator;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Sets;

import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class RpgsEconomy implements EconomyService {
	
//	private static ConfigurationLoader<CommentedConfigurationNode> uniqueAccountsLoader;
//	@SuppressWarnings("unused")
//	private static ConfigurationLoader<CommentedConfigurationNode> virtualAccountsLoader;
//	private static ConfigurationNode UAConf;
//	private static ConfigurationNode VAConf;

	@Override
	public Currency getDefaultCurrency() {
		return new RpgsCurrency();
	}

	@Override
	public Set<Currency> getCurrencies() {
		return Sets.newHashSet(new RpgsCurrency());
	}

	@Override
	public boolean hasAccount(UUID uuid) {
		return Sponge.getServer().getPlayer(uuid).get().get(AdventurerKeys.BALANCE).isPresent();
	}

	@Override
	public boolean hasAccount(String identifier) {
		return false;
	}

	@Override
	public Optional<UniqueAccount> getOrCreateAccount(UUID uuid) {
		RpgsAccount rpgsAccount = new RpgsAccount(uuid);

        return Optional.of(rpgsAccount);
	}

	@Override
	public Optional<Account> getOrCreateAccount(String identifier) {
		return null;
	}
	
//	public ConfigurationNode getAccount(UUID uuid) {
//		ConfigurationNode accountConfig = UAConf.getNode(uuid.toString());
//		return accountConfig;
//	}
//	
//	public void loadAccounts() {
//		File UAFile = new File(Seed.configDir + File.separator +  "Economy" + File.separator + "UniqueAccounts.conf");
//		File VAFile = new File(Seed.configDir + File.separator +  "Economy" + File.separator + "VirtualAccounts.conf");
//
//		try {
//			if (!UAFile.getParentFile().exists())
//				UAFile.getParentFile().mkdirs();
//			if (!UAFile.exists())
//				UAFile.createNewFile();
//			if (!VAFile.exists())
//				VAFile.createNewFile();
//			
//			uniqueAccountsLoader = HoconConfigurationLoader.builder().setFile(UAFile).build();
//			UAConf = uniqueAccountsLoader.load();
//			
//			
////			ConfigurationNode VAConf = HoconConfigurationLoader.builder().setFile(VAFile).build().load();
////			for (ConfigurationNode va : VAConf.getChildrenMap().values())
////				System.out.println(va.getNode("Balance").getInt(0));
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void saveAccounts() {
//		try {
//			uniqueAccountsLoader.save(UAConf);
////			virtualAccountsLoader.save(VAConf);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void registerContextCalculator(ContextCalculator<Account> calculator) {
		
	}
	
	public static Text calculateCurrency(int amount) {
		int gold = amount / 100;
		int silver = amount - gold * 100;
		Text goldText = Text.of();
		Text silverText = Text.of();
		
		if (gold > 0)
			goldText = Text.of(TextColors.GRAY, gold, TextColors.GOLD, "g ");
		if (silver > 0)
			silverText = Text.of(TextColors.GRAY, silver, TextColors.WHITE, "s ");
		
		return Text.of(goldText, silverText);
	}
	
}
