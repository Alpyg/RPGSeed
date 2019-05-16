package io.alpyg.rpg.data.item;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class ItemData extends AbstractData<ItemData, ImmutableItemData> {

	private String id;
	private double damage;
	private double defence;
	private String materialType;
	private int materialTier;
	
	public ItemData(String id, double damage, double defence, String materialType, int materialTier) {
		this.id = id;
		this.damage = damage;
		this.defence = defence;
		this.materialType = materialType;
		this.materialTier = materialTier;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(ItemKeys.ID, () -> this.id);
		registerFieldGetter(ItemKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(ItemKeys.DEFENCE, () -> this.defence);
		registerFieldGetter(ItemKeys.MATERIAL_TYPE, () -> this.materialType);
		registerFieldGetter(ItemKeys.MATERIAL_TIER, () -> this.materialTier);

		registerFieldSetter(ItemKeys.ID, x -> this.id = x);
		registerFieldSetter(ItemKeys.DAMAGE, x -> this.damage = x);
		registerFieldSetter(ItemKeys.DEFENCE, x -> this.defence = x);
		registerFieldSetter(ItemKeys.MATERIAL_TYPE, x -> this.materialType = x);
		registerFieldSetter(ItemKeys.MATERIAL_TIER, x -> this.materialTier = x);
		
		registerKeyValue(ItemKeys.ID, this::id);
		registerKeyValue(ItemKeys.DAMAGE, this::damage);
		registerKeyValue(ItemKeys.DEFENCE, this::defence);
		registerKeyValue(ItemKeys.MATERIAL_TYPE, this::materialType);
		registerKeyValue(ItemKeys.MATERIAL_TIER, this::materialTier);
	}
	
	public Value<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.ID, id);
    }
	
	public Value<Double> damage() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.DAMAGE, damage);
    }
	
	public Value<Double> defence() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.DEFENCE, defence);
    }
	
	public Value<String> materialType() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.MATERIAL_TYPE, materialType);
    }
	
	public Value<Integer> materialTier() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.MATERIAL_TIER, materialTier);
    }

    @Override
    public Optional<ItemData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<ItemData> otherData_ = dataHolder.get(ItemData.class);
        if (otherData_.isPresent()) {
        	ItemData otherData = otherData_.get();
        	ItemData finalData = overlap.merge(this, otherData);
            this.id = finalData.id;
            this.damage = finalData.damage;
            this.defence = finalData.defence;
            this.materialType = finalData.materialType;
            this.materialTier = finalData.materialTier;
        }
        return Optional.of(this);
    }

    // the double method isn't strictly necessary but makes implementing the builder easier
    @Override
    public Optional<ItemData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<ItemData> from(DataView view) {
        if (!view.contains(ItemKeys.ID.getQuery())
        		&& view.contains(ItemKeys.DAMAGE.getQuery())
        		&& view.contains(ItemKeys.DEFENCE.getQuery())
        		&& view.contains(ItemKeys.MATERIAL_TYPE.getQuery())
        		&& view.contains(ItemKeys.MATERIAL_TIER.getQuery()))
        	return Optional.empty();
        	
        this.id = view.getString(ItemKeys.ID.getQuery()).get();
        this.damage = view.getDouble(ItemKeys.DAMAGE.getQuery()).get();
        this.defence = view.getDouble(ItemKeys.DEFENCE.getQuery()).get();
        this.materialType = view.getString(ItemKeys.MATERIAL_TYPE.getQuery()).get();
        this.materialTier = view.getInt(ItemKeys.MATERIAL_TIER.getQuery()).get();
        
        return Optional.of(this);
    }
    
    @Override
    public ItemData copy() {
        return new ItemData(this.id, this.damage, this.defence, this.materialType, this.materialTier);
    }

    @Override
    public ImmutableItemData asImmutable() {
        return new ImmutableItemData(this.id, this.damage, this.defence, this.materialType, this.materialTier);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(ItemKeys.ID.getQuery(), this.id)
				.set(ItemKeys.DAMAGE.getQuery(), this.damage)
				.set(ItemKeys.DEFENCE.getQuery(), this.defence)
				.set(ItemKeys.MATERIAL_TYPE.getQuery(), this.materialType)
				.set(ItemKeys.MATERIAL_TIER.getQuery(), this.materialTier);
	}
}
