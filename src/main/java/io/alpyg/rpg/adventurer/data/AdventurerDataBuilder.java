package io.alpyg.rpg.adventurer.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class AdventurerDataBuilder extends AbstractDataBuilder<AdventurerData> implements DataManipulatorBuilder<AdventurerData, ImmutableAdventurerData> {
	
	public AdventurerDataBuilder() {
		super(AdventurerData.class, 2);
	}
	
	@Override
	public AdventurerData create() {
		return new AdventurerData(new AdventurerStats(), "", 3, 0);
	}
	
	@Override
	public Optional<AdventurerData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<AdventurerData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
	
}
