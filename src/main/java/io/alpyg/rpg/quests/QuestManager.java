package io.alpyg.rpg.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.alpyg.rpg.utils.ConfigLoader;

public class QuestManager {

	public static Map<String, Quest> quests = new HashMap<String, Quest>();

	public static Optional<Quest> getQuest(String quest) {
		return Optional.of(quests.get(quest));
	}
	
	public static void reload() {
		quests.clear();
		ConfigLoader.loadConfig("Quests");
	}
	
}
