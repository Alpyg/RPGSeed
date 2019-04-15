package io.alpyg.rpg.gameplay.shop.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ShopDataBuilder extends AbstractDataBuilder<ShopData> implements DataManipulatorBuilder<ShopData, ImmutableShopData> {

	public ShopDataBuilder() {
		super(ShopData.class, 2);
	}
	
	@Override
	public ShopData create() {
		return new ShopData("", "");
	}
	
	@Override
	public Optional<ShopData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<ShopData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}

}
