package io.alpyg.rpg.data.backpack;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class BackpackDataBuilder extends AbstractDataBuilder<BackpackData> implements DataManipulatorBuilder<BackpackData, ImmutableBackpackData> {
	
	public BackpackDataBuilder() {
		super(BackpackData.class, 2);
	}
	
	@Override
	public BackpackData create() {
		return new BackpackData(1, "");
	}
	
	@Override
	public Optional<BackpackData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<BackpackData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
	
}
