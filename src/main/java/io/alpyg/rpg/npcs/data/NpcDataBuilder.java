package io.alpyg.rpg.npcs.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class NpcDataBuilder extends AbstractDataBuilder<NpcData> implements DataManipulatorBuilder<NpcData, ImmutableNpcData> {

	public NpcDataBuilder() {
		super(NpcData.class, 2);
	}
	
	@Override
	public NpcData create() {
		return new NpcData("");
	}
	
	@Override
	public Optional<NpcData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<NpcData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
	
}
