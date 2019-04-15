package io.alpyg.rpg.gameplay.shop.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class ShopData extends AbstractData<ShopData, ImmutableShopData> {
	
	private String id;
	private String shopData;
	
	public ShopData(String id, String shopData) {
		this.id = id;
		this.shopData = shopData;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(ShopKeys.SHOP_ID, () -> this.id);
		registerFieldGetter(ShopKeys.SHOP_DATA, () -> this.shopData);

		registerFieldSetter(ShopKeys.SHOP_ID, x -> this.id = x);
		registerFieldSetter(ShopKeys.SHOP_DATA, x -> this.shopData = x);

		registerKeyValue(ShopKeys.SHOP_ID, this::id);
		registerKeyValue(ShopKeys.SHOP_DATA, this::shopData);
	}
	
	public Value<String> id() {
		return Sponge.getRegistry().getValueFactory().createValue(ShopKeys.SHOP_ID, this.id);
	}
	
	public Value<String> shopData() {
		return Sponge.getRegistry().getValueFactory().createValue(ShopKeys.SHOP_DATA, this.shopData);
	}
	
	@Override
	public Optional<ShopData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<ShopData> otherData_ = dataHolder.get(ShopData.class);
		if (otherData_.isPresent()) {
			ShopData otherData = otherData_.get();
			ShopData finalData = overlap.merge(this, otherData);
			this.id = finalData.id;
			this.shopData = finalData.shopData;
		}
		return Optional.of(this);
	}

    @Override
    public Optional<ShopData> from(DataContainer container) {
        return from((DataView) container);
    }
	
	public Optional<ShopData> from(DataView view) {
        if (!view.contains(ShopKeys.SHOP_DATA.getQuery()))
        	return Optional.empty();
        
        this.shopData = view.getString(ShopKeys.SHOP_DATA.getQuery()).get();
        
        return Optional.of(this);
	}
	
	@Override
	public ShopData copy() {
		return new ShopData(this.id, this.shopData);
	}
	
	@Override
	public ImmutableShopData asImmutable() {
		return new ImmutableShopData(this.id, this.shopData);
	}

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(ShopKeys.SHOP_ID.getQuery(), this.id)
                .set(ShopKeys.SHOP_DATA.getQuery(), this.shopData);
	}
	
}
