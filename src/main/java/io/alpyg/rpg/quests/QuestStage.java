package io.alpyg.rpg.quests;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import ninja.leaping.configurate.ConfigurationNode;

public class QuestStage {
	
	public Text objective;
	
	public String[] chat;

	public QuestStage(ConfigurationNode config) {
		this.objective = TextSerializers.FORMATTING_CODE.deserialize(config.getString("Objective"));
	}
	
}
