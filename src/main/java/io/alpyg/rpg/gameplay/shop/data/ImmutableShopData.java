package io.alpyg.rpg.gameplay.shop.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableShopData extends AbstractImmutableData<ImmutableShopData, ShopData> {

	private String id;
	private String shopData;
	
	public ImmutableShopData(String id, String shopData) {
		this.id = id;
		this.shopData = shopData;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(ShopKeys.SHOP_ID, () -> this.id);
		registerFieldGetter(ShopKeys.SHOP_DATA, () -> this.shopData);

		registerKeyValue(ShopKeys.SHOP_ID, this::id);
		registerKeyValue(ShopKeys.SHOP_DATA, this::shopData);
	}
	
	public ImmutableValue<String> id() {
		return Sponge.getRegistry().getValueFactory().createValue(ShopKeys.SHOP_ID, this.id).asImmutable();
	}
	
	public ImmutableValue<String> shopData() {
		return Sponge.getRegistry().getValueFactory().createValue(ShopKeys.SHOP_DATA, this.shopData).asImmutable();
	}
	
	@Override
	public ShopData asMutable() {
		return new ShopData(this.id, this.shopData);
	}

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(ShopKeys.SHOP_ID.getQuery(), this.id)
                .set(ShopKeys.SHOP_DATA.getQuery(), this.shopData);
	}
	
}
