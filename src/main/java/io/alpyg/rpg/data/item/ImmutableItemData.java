package io.alpyg.rpg.data.item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableItemData extends AbstractImmutableData<ImmutableItemData, ItemData> {

	private String id;
	private double damage;
	private double defence;
	private String materialType;
	private int materialTier;
	
	public ImmutableItemData(String id, double damage, double defence, String materialType, int materialTier) {
		this.id = id;
		this.damage = damage;
		this.defence = defence;
		this.materialType = materialType;
		this.materialTier = materialTier;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(ItemKeys.ID, () -> this.id);
		registerFieldGetter(ItemKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(ItemKeys.DEFENCE, () -> this.defence);
		registerFieldGetter(ItemKeys.MATERIAL_TYPE, () -> this.materialType);
		registerFieldGetter(ItemKeys.MATERIAL_TIER, () -> this.materialTier);

		registerKeyValue(ItemKeys.ID, this::id);
		registerKeyValue(ItemKeys.DAMAGE, this::damage);
		registerKeyValue(ItemKeys.DEFENCE, this::defence);
		registerKeyValue(ItemKeys.MATERIAL_TYPE, this::materialType);
		registerKeyValue(ItemKeys.MATERIAL_TIER, this::materialTier);
	}
	
	public ImmutableValue<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.ID, id).asImmutable();
    }
	
	public ImmutableValue<Double> damage() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.DAMAGE, damage).asImmutable();
    }
	
	public ImmutableValue<Double> defence() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.DEFENCE, defence).asImmutable();
    }
	
	public ImmutableValue<String> materialType() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.MATERIAL_TYPE, materialType).asImmutable();
    }
	
	public ImmutableValue<Integer> materialTier() {
        return Sponge.getRegistry().getValueFactory().createValue(ItemKeys.MATERIAL_TIER, materialTier).asImmutable();
    }
	
	@Override
    public ItemData asMutable() {
        return new ItemData(this.id, this.damage, this.defence, this.materialType, this.materialTier);
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
