package io.alpyg.rpg.damage;

import java.util.Random;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.alpyg.rpg.adventurer.AdventurerUI;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class EnvironmentalDamage {
	
	private static Text roll = Text.of(TextColors.AQUA, "Rolled");
	
	public static boolean roll(Player player) {
		Random r = new Random();
		if (r.nextDouble() < player.get(AdventurerKeys.STATS).get().agility / 500) {
			AdventurerUI.gui.get(player.getUniqueId()).display(roll);
			return true;
		}
		return false;
	}

}
