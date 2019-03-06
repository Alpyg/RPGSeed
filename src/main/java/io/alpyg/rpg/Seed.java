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
import io.alpyg.rpg.adventurer.data.AdventurerBuilder;
import io.alpyg.rpg.adventurer.data.AdventurerData;
import io.alpyg.rpg.adventurer.data.AdventurerDataBuilder;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;
import io.alpyg.rpg.adventurer.data.ImmutableAdventurerData;
import io.alpyg.rpg.chat.WhisperCommands;
import io.alpyg.rpg.damage.DamageHandler;
import io.alpyg.rpg.economy.SeedEconomy;
import io.alpyg.rpg.economy.EconomyCommands;
import io.alpyg.rpg.effect.EffectCommands;
import io.alpyg.rpg.events.EntityInteractionEvents;
import io.alpyg.rpg.events.InteractionEvents;
import io.alpyg.rpg.events.InventoryEvents;
import io.alpyg.rpg.events.PlayerEvents;
import io.alpyg.rpg.gameplay.backpack.BackpackCommands;
import io.alpyg.rpg.gameplay.fasttravel.FastTravel;
import io.alpyg.rpg.gameplay.fasttravel.FastTravelCommands;
import io.alpyg.rpg.gameplay.gathering.GatherCommands;
import io.alpyg.rpg.gameplay.gathering.data.GatherData;
import io.alpyg.rpg.gameplay.gathering.data.GatherDataBuilder;
import io.alpyg.rpg.gameplay.gathering.data.GatherKeys;
import io.alpyg.rpg.gameplay.gathering.data.ImmutableGatherData;
import io.alpyg.rpg.gameplay.mounts.Mount;
import io.alpyg.rpg.gameplay.mounts.MountCommands;
import io.alpyg.rpg.items.ItemCommands;
import io.alpyg.rpg.items.data.ImmutableItemData;
import io.alpyg.rpg.items.data.ItemData;
import io.alpyg.rpg.items.data.ItemDataBuilder;
import io.alpyg.rpg.items.data.ItemKeys;
import io.alpyg.rpg.mobs.MobCommands;
import io.alpyg.rpg.mobs.data.ImmutableMobData;
import io.alpyg.rpg.mobs.data.MobData;
import io.alpyg.rpg.mobs.data.MobDataBuilder;
import io.alpyg.rpg.mobs.data.MobKeys;
import io.alpyg.rpg.npcs.NpcCommands;
import io.alpyg.rpg.npcs.data.ImmutableNpcData;
import io.alpyg.rpg.npcs.data.NpcData;
import io.alpyg.rpg.npcs.data.NpcDataBuilder;
import io.alpyg.rpg.npcs.data.NpcKeys;
import io.alpyg.rpg.quests.QuestCommands;
import io.alpyg.rpg.quests.QuestManager;
import io.alpyg.rpg.utils.ConfigLoader;
import io.alpyg.rpg.utils.Reference;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, description = Reference.DESCRIPTION)
public class Seed {
	
	public static Seed plugin;
	
	@Inject	private PluginContainer container;
    
	@Inject	private Logger logger;
	
	@ConfigDir(sharedRoot = false)
    public static Path configDir;
	
	private SeedEconomy economy;
	private UserStorageService userStorageService;
	public static MineskinService mineSkin;
    
	@Listener
	public void onServerStart(GameStartedServerEvent e) {
		plugin = this;
		configDir = Sponge.getConfigManager().getPluginConfig(this).getDirectory();
		
		economy = new SeedEconomy();
		userStorageService = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
		
		registerEvents();
		registerCommands();
		loadConfig();
	}

	@Listener
	public void onInitialize(final GameInitializationEvent e) {
		// Create Keys
		AdventurerKeys.registerKeys();
		NpcKeys.registerKeys();
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
			.dataName("Npc Data")
			.manipulatorId("npc_data")
			.dataClass(NpcData.class)
			.immutableClass(ImmutableNpcData.class)
			.builder(new NpcDataBuilder())
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
		Sponge.getEventManager().registerListeners(Seed.plugin, new PlayerEvents());
		Sponge.getEventManager().registerListeners(Seed.plugin, new EntityInteractionEvents());
		Sponge.getEventManager().registerListeners(Seed.plugin, new InventoryEvents());
		Sponge.getEventManager().registerListeners(Seed.plugin, new InteractionEvents());
		Sponge.getEventManager().registerListeners(Seed.plugin, new DamageHandler());
		Sponge.getEventManager().registerListeners(Seed.plugin, new Mount());
	}
	
	private static void registerCommands() {
		Sponge.getCommandManager().register(Seed.plugin, AdventurerCommands.statsCommand, "status");
		Sponge.getCommandManager().register(Seed.plugin, QuestCommands.questCommand, "quest");
		Sponge.getCommandManager().register(Seed.plugin, BackpackCommands.backpackCommand, "bp");
		Sponge.getCommandManager().register(Seed.plugin, MobCommands.seedMobsCommand, "sm");
		Sponge.getCommandManager().register(Seed.plugin, ItemCommands.seedItemsCommand, "si");
		Sponge.getCommandManager().register(Seed.plugin, GatherCommands.gatheringCommand, "gather");
		Sponge.getCommandManager().register(Seed.plugin, EffectCommands.effectCommand, "eff");
		Sponge.getCommandManager().register(Seed.plugin, FastTravelCommands.fasttravelCommand, "ft");
		Sponge.getCommandManager().register(Seed.plugin, WhisperCommands.whisperCommand, "w");
		Sponge.getCommandManager().register(Seed.plugin, NpcCommands.npcCommand, "npc");
		Sponge.getCommandManager().register(Seed.plugin, MountCommands.mountCommand, "mount");

		Sponge.getCommandManager().register(Seed.plugin, EconomyCommands.payCommand, "pay");
		Sponge.getCommandManager().register(Seed.plugin, EconomyCommands.balCommand, "bal");
	}
	
	private static void loadConfig() {
		ConfigLoader.loadConfig("Npcs");
		ConfigLoader.loadConfig("Mobs");
		ConfigLoader.loadConfig("Items");
		ConfigLoader.loadConfig("Gathering");
		
		QuestManager.loadQuests();
		FastTravel.loadFastTravelLocations();
	}
	
	public static SeedEconomy getEconomy() {
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

