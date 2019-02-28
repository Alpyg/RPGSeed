package io.alpyg.rpg.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import io.alpyg.rpg.npcs.Npc;
import ninja.leaping.configurate.ConfigurationNode;

public class Quest {

	protected String internalName;
	protected String displayName;
	protected Npc npc;
	protected int reqLvl;
	protected Text description;
	protected Map<Integer, QuestStage> stages = new HashMap<Integer, QuestStage>();
	
	public Quest(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
		this.displayName = config.getNode("Display").getString(internalName);
		this.reqLvl = config.getNode("RequiredLevel").getInt(0);
		this.description = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Description").getString());
		for (ConfigurationNode stage : config.getNode("Stages").getChildrenMap().values()) {
			stages.put(Integer.parseInt(stage.getKey().toString()), new QuestStage(stage));
		}

		System.out.println(internalName);
		System.out.println(displayName);
		System.out.println(description);
		for (QuestStage questStage : stages.values())
			System.out.println(questStage.objective);
		QuestManager.quests.put(internalName, this);
	}
	
	public Optional<QuestStage> getStage(int index) {
		return Optional.of(this.stages.get(index));
	}
}
