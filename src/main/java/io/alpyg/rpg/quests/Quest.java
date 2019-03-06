package io.alpyg.rpg.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import ninja.leaping.configurate.ConfigurationNode;

public class Quest {

	private String internalName;
	private String displayName;
	private int level;
	private Text description;

	private Map<Integer, QuestStage> stages = new HashMap<Integer, QuestStage>();
	private ItemStack questBook = ItemStack.of(ItemTypes.BOOK);
	
	public Quest(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
		this.displayName = config.getNode("Display").getString(internalName);
		this.level = config.getNode("Level").getInt(0);
		this.description = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Description").getString());
		for (ConfigurationNode stage : config.getNode("Stages").getChildrenMap().values())
			stages.put(Integer.parseInt(stage.getKey().toString()), new QuestStage(stage));
		
		QuestManager.quests.put(internalName, this);
		
		this.questBook = createQuestBook();
	}
	
	public Optional<QuestStage> getStage(int index) {
		return Optional.of(this.stages.get(index));
	}
	
	public void openQuestView(Player player) {
		List<Text> text = new ArrayList<Text>();
		text.add(this.description);
		BookView view = BookView.builder().addPages(text).build();
		player.sendBookView(view);
	}
	
	public ItemStack createQuestBook() {
		ItemStack questItem = ItemStack.of(ItemTypes.BOOK);
		
		questItem.offer(Keys.DISPLAY_NAME, Text.of(TextColors.WHITE, " -= ", this.displayName, " =-"));

		List<Text> lore = new ArrayList<Text>();
		if (this.level > 0)
			lore.add(Text.of(TextColors.WHITE, "Level: ", TextColors.GRAY, this.level));
		lore.add(Text.of());
		lore.add(Text.of(TextColors.GRAY, this.description));
		questItem.offer(Keys.ITEM_LORE, lore);
		
		return questItem;
	}
	
	public String getInternalName() {
		return this.internalName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public int getLevel() {
		return this.level;
	}

	public Text getDescription() {
		return this.description;
	}

	public Map<Integer, QuestStage> getStages() {
		return this.stages;
	}
	
	public ItemStack getQuestBook() {
		return this.questBook.copy();
	}
	
}
