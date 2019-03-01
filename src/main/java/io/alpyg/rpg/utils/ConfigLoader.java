package io.alpyg.rpg.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;

import io.alpyg.rpg.Seed;
import io.alpyg.rpg.gameplay.gathering.GatherConfig;
import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.mobs.MobConfig;
import io.alpyg.rpg.npcs.NpcConfig;
import io.alpyg.rpg.quests.Quest;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigLoader {

	private static ConfigurationLoader<CommentedConfigurationNode> configLoader;
	
	public static void loadConfig(String folder) {
		File configDir = new File(Seed.configDir + File.separator +  folder);
		
		File[] configFiles = configDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".conf");
			}
		});

		if(configFiles == null) return;
		
		for(File configFile : configFiles)
			readConfig(configFile);
	}
	
	private static void readConfig(File _configFile) {
		configLoader = HoconConfigurationLoader.builder().setPath(Paths.get(_configFile.getAbsolutePath())).build();
		try {
			ConfigurationNode configFile = configLoader.load();
			for(ConfigurationNode configNode : configFile.getChildrenMap().values()) {
				if (_configFile.getParentFile().getName().equals("Npcs"))
					new NpcConfig(configNode.getKey().toString(), configNode);
				else if (_configFile.getParentFile().getName().equals("Mobs"))
					new MobConfig(configNode.getKey().toString(), configNode);
				else if (_configFile.getParentFile().getName().equals("Items"))
					new ItemConfig(configNode.getKey().toString(), configNode);
				else if (_configFile.getParentFile().getName().equals("Quests"))
					new Quest(configNode.getKey().toString(), configNode);
				else if (_configFile.getParentFile().getName().equals("Gathering"))
					new GatherConfig(configNode.getKey().toString(), configNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
