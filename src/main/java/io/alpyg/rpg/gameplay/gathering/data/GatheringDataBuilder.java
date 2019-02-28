package io.alpyg.rpg.gameplay.gathering.data;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class GatheringDataBuilder extends AbstractDataBuilder<GatheringData> implements DataManipulatorBuilder<GatheringData, ImmutableGatheringData> {
	
	public GatheringDataBuilder() {
		super(GatheringData.class, 1);
	}
	
	@Override
	public GatheringData create() {
		return new GatheringData("", "", "", "", new HashMap<UUID, Integer>());
	}
	
	@Override
	public Optional<GatheringData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<GatheringData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
}
