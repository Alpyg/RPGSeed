package io.alpyg.rpg;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.user.UserStorageService;

import com.google.inject.Inject;

import cz.creeper.mineskinsponge.MineskinService;
import io.alpyg.rpg.adventurer.AdventurerCommands;
import io.alpyg.rpg.adventurer.AdventurerStats;
import io.alpyg.rpg.chat.WhisperCommands;
import io.alpyg.rpg.damage.DamageHandler;
import io.alpyg.rpg.data.adventurer.AdventurerBuilder;
import io.alpyg.rpg.data.adventurer.AdventurerData;
import io.alpyg.rpg.data.adventurer.AdventurerDataBuilder;
import io.alpyg.rpg.data.adventurer.AdventurerKeys;
import io.alpyg.rpg.data.adventurer.ImmutableAdventurerData;
import io.alpyg.rpg.data.backpack.BackpackData;
import io.alpyg.rpg.data.backpack.BackpackDataBuilder;
import io.alpyg.rpg.data.backpack.BackpackKeys;
import io.alpyg.rpg.data.backpack.ImmutableBackpackData;
import io.alpyg.rpg.data.item.ImmutableItemData;
import io.alpyg.rpg.data.item.ItemData;
import io.alpyg.rpg.data.item.ItemDataBuilder;
import io.alpyg.rpg.data.item.ItemKeys;
import io.alpyg.rpg.data.mob.ImmutableMobData;
import io.alpyg.rpg.data.mob.MobData;
import io.alpyg.rpg.data.mob.MobDataBuilder;
import io.alpyg.rpg.data.mob.MobKeys;
import io.alpyg.rpg.data.npc.ImmutableNpcData;
import io.alpyg.rpg.data.npc.NpcData;
import io.alpyg.rpg.data.npc.NpcDataBuilder;
import io.alpyg.rpg.data.npc.NpcKeys;
import io.alpyg.rpg.economy.EconomyCommands;
import io.alpyg.rpg.economy.RpgsEconomy;
import io.alpyg.rpg.effect.EffectCommands;
import io.alpyg.rpg.events.EntityConstructEvents;
import io.alpyg.rpg.events.EntityDropEvent;
import io.alpyg.rpg.events.InteractEntityEvents;
import io.alpyg.rpg.events.InteractEvents;
import io.alpyg.rpg.events.InventoryEvents;
import io.alpyg.rpg.events.PlayerEvents;
import io.alpyg.rpg.gameplay.backpack.BackpackCommands;
import io.alpyg.rpg.gameplay.backpack.BackpackEvents;
import io.alpyg.rpg.gameplay.fasttravel.FastTravel;
import io.alpyg.rpg.gameplay.fasttravel.FastTravelCommands;
import io.alpyg.rpg.gameplay.gathering.GatherCommands;
import io.alpyg.rpg.gameplay.gathering.data.GatherData;
import io.alpyg.rpg.gameplay.gathering.data.GatherDataBuilder;
import io.alpyg.rpg.gameplay.gathering.data.GatherKeys;
import io.alpyg.rpg.gameplay.gathering.data.ImmutableGatherData;
import io.alpyg.rpg.gameplay.mounts.MountCommands;
import io.alpyg.rpg.gameplay.mounts.MountEvents;
import io.alpyg.rpg.gameplay.shop.ShopCommands;
import io.alpyg.rpg.gameplay.shop.data.ImmutableShopData;
import io.alpyg.rpg.gameplay.shop.data.ShopData;
import io.alpyg.rpg.gameplay.shop.data.ShopDataBuilder;
import io.alpyg.rpg.gameplay.shop.data.ShopKeys;
import io.alpyg.rpg.items.ItemCommands;
import io.alpyg.rpg.mobs.MobCommands;
import io.alpyg.rpg.npcs.NpcCommands;
import io.alpyg.rpg.quests.QuestCommands;
import io.alpyg.rpg.quests.QuestManager;
import io.alpyg.rpg.utils.ConfigLoader;
import io.alpyg.rpg.utils.Reference;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, description = Reference.DESCRIPTION)
public class Rpgs {
	
	public static Rpgs plugin;
	
	@Inject	private PluginContainer container;
    
	@Inject	private Logger logger;
	
	@ConfigDir(sharedRoot = false)
    public static Path configDir;
	
	private RpgsEconomy economy;
	private UserStorageService userStorageService;
	public static MineskinService mineSkin;
    
	@Listener
	public void onServerStart(GameStartedServerEvent e) {
		plugin = this;
		configDir = Sponge.getConfigManager().getPluginConfig(this).getDirectory();
		
		economy = new RpgsEconomy();
		userStorageService = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
		
		registerEvents();
		registerCommands();
		loadConfig();
	}

	@Listener
	public void onInitialize(final GameInitializationEvent e) {
		// Create Keys
		AdventurerKeys.registerKeys();
		BackpackKeys.registerKeys();
		NpcKeys.registerKeys();
		ShopKeys.registerKeys();
		ItemKeys.registerKeys();
		MobKeys.registerKeys();
		GatherKeys.registerKeys();
		
		Sponge.getDataManager().registerBuilder(AdventurerStats.class, new AdventurerBuilder());
		
		mineSkin = Sponge.getServiceManager().provide(MineskinService.class)
		        .orElseThrow(() -> new IllegalStateException("Could not access the Mineskin service."));

		DataRegistration.builder()
			.dataName("Adventurer Data")
			.manipulatorId("adventurer_data")
			.dataClass(AdventurerData.class)
			.immutableClass(ImmutableAdventurerData.class)
			.builder(new AdventurerDataBuilder())
			.buildAndRegister(this.container);

		DataRegistration.builder()
			.dataName("Backpack Data")
			.manipulatorId("backpack_data")
			.dataClass(BackpackData.class)
			.immutableClass(ImmutableBackpackData.class)
			.builder(new BackpackDataBuilder())
			.buildAndRegister(this.container);
		
		DataRegistration.builder()
			.dataName("Npc Data")
			.manipulatorId("npc_data")
			.dataClass(NpcData.class)
			.immutableClass(ImmutableNpcData.class)
			.builder(new NpcDataBuilder())
			.buildAndRegister(this.container);
		
		DataRegistration.builder()
			.dataName("Shop Data")
			.manipulatorId("shop_data")
			.dataClass(ShopData.class)
			.immutableClass(ImmutableShopData.class)
			.builder(new ShopDataBuilder())
			.buildAndRegister(this.container);
		
		DataRegistration.builder()
			.dataName("Item Data")
			.manipulatorId("item_data")
			.dataClass(ItemData.class)
			.immutableClass(ImmutableItemData.class)
			.builder(new ItemDataBuilder())
			.buildAndRegister(this.container);
		
		DataRegistration.builder()
			.dataName("Mob Data")
			.manipulatorId("mob_data")
			.dataClass(MobData.class)
			.immutableClass(ImmutableMobData.class)
			.builder(new MobDataBuilder())
			.buildAndRegister(this.container);
		
		DataRegistration.builder()
			.dataName("Gathering Data")
			.manipulatorId("gathering_data")
			.dataClass(GatherData.class)
			.immutableClass(ImmutableGatherData.class)
			.builder(new GatherDataBuilder())
			.buildAndRegister(this.container);
	}
	
	private static void registerEvents() {
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new PlayerEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new BackpackEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new MountEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new InteractEntityEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new InventoryEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new InteractEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new EntityConstructEvents());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new EntityDropEvent());
		Sponge.getEventManager().registerListeners(Rpgs.plugin, new DamageHandler());
	}
	
	private static void registerCommands() {
		Sponge.getCommandManager().register(Rpgs.plugin, AdventurerCommands.statsCommand, "status");
		Sponge.getCommandManager().register(Rpgs.plugin, QuestCommands.questCommand, "quest");
		Sponge.getCommandManager().register(Rpgs.plugin, BackpackCommands.backpackCommand, "bp");
		Sponge.getCommandManager().register(Rpgs.plugin, MobCommands.seedMobsCommand, "sm");
		Sponge.getCommandManager().register(Rpgs.plugin, ItemCommands.seedItemsCommand, "si");
		Sponge.getCommandManager().register(Rpgs.plugin, GatherCommands.gatheringCommand, "gather");
		Sponge.getCommandManager().register(Rpgs.plugin, EffectCommands.effectCommand, "eff");
		Sponge.getCommandManager().register(Rpgs.plugin, FastTravelCommands.fasttravelCommand, "ft");
		Sponge.getCommandManager().register(Rpgs.plugin, WhisperCommands.whisperCommand, "w");
		Sponge.getCommandManager().register(Rpgs.plugin, NpcCommands.npcCommand, "npc");
		Sponge.getCommandManager().register(Rpgs.plugin, ShopCommands.shopCommand, "shop");
		Sponge.getCommandManager().register(Rpgs.plugin, MountCommands.mountCommand, "mount");

		Sponge.getCommandManager().register(Rpgs.plugin, EconomyCommands.payCommand, "pay");
		Sponge.getCommandManager().register(Rpgs.plugin, EconomyCommands.balCommand, "bal");
		Sponge.getCommandManager().register(Rpgs.plugin, EconomyCommands.setCommand, "setbal");
	}
	
	private static void loadConfig() {
		ConfigLoader.loadConfig("Npcs");
		ConfigLoader.loadConfig("Items");
		ConfigLoader.loadConfig("Mobs");
		ConfigLoader.loadConfig("Shops");
		ConfigLoader.loadConfig("Gathering");
		
		QuestManager.loadQuests();
		FastTravel.loadFastTravelLocations();
	}
	
	public static RpgsEconomy getEconomy() {
		return plugin.economy;
	}
	
	public static UserStorageService getUserStorageService() {
		return plugin.userStorageService;
	}
	
	public static Logger getLogger() {
		return plugin.logger;
	}
	
	public static PluginContainer getContainer() {
		return plugin.container;
	}
	
}

