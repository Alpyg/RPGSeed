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
		
		short points = container.getShort(AdventurerStats.POINTS).get();
		short vitality = container.getShort(AdventurerStats.VITALITY).get();
		short strength = container.getShort(AdventurerStats.STRENGHT).get();
		short defence = container.getShort(AdventurerStats.DEFENCE).get();
		short agility = container.getShort(AdventurerStats.AGILITY).get();
		short magic = container.getShort(AdventurerStats.MAGIC).get();
		
		return Optional.of(new AdventurerStats(points, vitality, strength, defence, agility, magic));
	}

}
