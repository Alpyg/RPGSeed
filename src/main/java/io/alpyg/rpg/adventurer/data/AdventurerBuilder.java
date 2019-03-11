package io.alpyg.rpg.adventurer.data;

import java.util.Optional;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class AdventurerBuilder extends AbstractDataBuilder<AdventurerStats> {

	public AdventurerBuilder() {
		super(AdventurerStats.class, 2);
	}

	@Override
	protected Optional<AdventurerStats> buildContent(DataView container) throws InvalidDataException {
		if (!container.contains(AdventurerStats.POINTS, AdventurerStats.VITALITY, AdventurerStats.STRENGHT, AdventurerStats.DEFENCE, AdventurerStats.AGILITY, AdventurerStats.MAGIC))
			return Optional.empty();
		
		int points = container.getInt(AdventurerStats.POINTS).get();
		int vitality = container.getInt(AdventurerStats.VITALITY).get();
		int strength = container.getInt(AdventurerStats.STRENGHT).get();
		int defence = container.getInt(AdventurerStats.DEFENCE).get();
		int agility = container.getInt(AdventurerStats.AGILITY).get();
		int magic = container.getInt(AdventurerStats.MAGIC).get();
		
		return Optional.of(new AdventurerStats(points, vitality, strength, defence, agility, magic));
	}

}
