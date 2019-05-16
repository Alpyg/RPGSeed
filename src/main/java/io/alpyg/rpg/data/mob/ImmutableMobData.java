package io.alpyg.rpg.mobs.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableMobData extends AbstractImmutableData<ImmutableMobData, MobData> {
	
	private String internalName;
	private double level;
	private double damage;
	private double defence;
	
	public ImmutableMobData(String internalName, double level, double damage, double defence) {
		this.internalName = internalName;
		this.level = level;
		this.damage = damage;
		this.defence = defence;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(MobKeys.ID, () -> this.internalName);
		registerFieldGetter(MobKeys.LEVEL, () -> this.level);
		registerFieldGetter(MobKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(MobKeys.DEFENCE, () -> this.defence);

		registerKeyValue(MobKeys.ID, this::internalName);
		registerKeyValue(MobKeys.LEVEL, this::level);
		registerKeyValue(MobKeys.DAMAGE, this::damage);
		registerKeyValue(MobKeys.DEFENCE, this::defence);
	}
	
	public ImmutableValue<String> internalName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.ID, internalName).asImmutable();
    }
	
	public ImmutableValue<Double> level() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.LEVEL, level).asImmutable();
    }
	
	public ImmutableValue<Double> damage() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DAMAGE, damage).asImmutable();
    }
	
	public ImmutableValue<Double> defence() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DEFENCE, defence).asImmutable();
	}
	
	@Override
	public MobData asMutable() {
		return new MobData(this.internalName, this.level, this.damage, this.defence);
	}
	
	@Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
				.set(MobKeys.ID.getQuery(), this.internalName)
				.set(MobKeys.LEVEL.getQuery(), this.level)
				.set(MobKeys.DAMAGE.getQuery(), this.damage)
				.set(MobKeys.DEFENCE.getQuery(), this.defence);
	}
	
}
