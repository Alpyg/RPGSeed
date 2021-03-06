package io.alpyg.rpg.data.mob;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class MobDataBuilder extends AbstractDataBuilder<MobData> implements DataManipulatorBuilder<MobData, ImmutableMobData> {
	
	public MobDataBuilder() {
		super(MobData.class, 2);
	}
	
	@Override
	public MobData create() {
		return new MobData("", 0, 0, 0);
	}
	
	@Override
	public Optional<MobData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<MobData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
	
}
