package io.alpyg.rpg.items.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ItemDataBuilder extends AbstractDataBuilder<ItemData> implements DataManipulatorBuilder<ItemData, ImmutableItemData> {
	
	public ItemDataBuilder() {
		super(ItemData.class, 1);
	}
	
	@Override
	public ItemData create() {
		return new ItemData("", 0, 0, "", 0);
	}
	
	@Override
	public Optional<ItemData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<ItemData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
}
