package io.alpyg.rpg.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.Seed;
import io.alpyg.rpg.utils.ConfigLoader;

public class QuestManager {

	public static Map<String, Quest> quests = new HashMap<String, Quest>();
	public static List<ItemStack> questBooks = new ArrayList<ItemStack>();
	public static ItemStack journal = ItemStack.of(ItemTypes.BOOK);
	public static Text journalName = Text.of(TextColors.GRAY, "Journal");
	private static Inventory questInv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
			.property(InventoryTitle.of(Text.of("Quests")))
			.build(Seed.plugin);

	public static Optional<Quest> getQuest(String quest) {
		return Optional.of(quests.get(quest));
	}
	
	public static void loadQuests() {
		questInv.clear();
		quests.clear();
		ConfigLoader.loadConfig("Quests");
		
		for (String questID : quests.keySet())
			questBooks.add(quests.get(questID).getQuestBook());
		
		for (ItemStack questBook : questBooks)
			questInv.offer(questBook);
	}
	
	public static void openQuestList(Player player) {		
		player.openInventory(questInv);
	}
	
	public static void openPlayerJournal(Player player) {
		Inventory journalInv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryTitle.of(Text.of("Quests")))
				.build(Seed.plugin);
		
		for (Quest quest : quests.values())
			if (quest.getLevel() <= player.get(Keys.EXPERIENCE_LEVEL).get())
				journalInv.offer(quest.getQuestBook());
		
		player.openInventory(journalInv);
	}
	
	static {
		journal.offer(Keys.DISPLAY_NAME, journalName);
	}
	
}
