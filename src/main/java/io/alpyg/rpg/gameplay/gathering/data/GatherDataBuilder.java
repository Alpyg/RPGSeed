package io.alpyg.rpg.gameplay.gathering.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class GatherDataBuilder extends AbstractDataBuilder<GatherData> implements DataManipulatorBuilder<GatherData, ImmutableGatherData> {
	
	public GatherDataBuilder() {
		super(GatherData.class, 2);
	}
	
	@Override
	public GatherData create() {
		return new GatherData("", "", "", "");
	}
	
	@Override
	public Optional<GatherData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<GatherData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
}
