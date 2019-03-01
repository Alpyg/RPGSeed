package io.alpyg.rpg.mobs.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class MobData extends AbstractData<MobData, ImmutableMobData> {
	
	private String internalName;
	private double level;
	private double damage;
	private double defence;
	
	public MobData(String internalName, double level, double damage, double defence) {
		this.internalName = internalName;
		this.level = level;
		this.damage = damage;
		this.defence = defence;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MobKeys.ID, () -> this.internalName);
		registerFieldGetter(MobKeys.LEVEL, () -> this.level);
		registerFieldGetter(MobKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(MobKeys.DEFENCE, () -> this.defence);
		
		registerFieldSetter(MobKeys.ID, x -> this.internalName = x);
		registerFieldSetter(MobKeys.LEVEL, x -> this.level = x);
		registerFieldSetter(MobKeys.DAMAGE, x -> this.damage = x);
		registerFieldSetter(MobKeys.DEFENCE, x -> this.defence = x);
		
		registerKeyValue(MobKeys.ID, this::internalName);
		registerKeyValue(MobKeys.LEVEL, this::level);
		registerKeyValue(MobKeys.DAMAGE, this::damage);
		registerKeyValue(MobKeys.DEFENCE, this::defence);
	}
	
	public Value<String> internalName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.ID, internalName);
	}
	public Value<Double> level() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.LEVEL, level);
    }
	
	public Value<Double> damage() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DAMAGE, damage);
    }
	
	public Value<Double> defence() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DEFENCE, defence);
	}
	
	@Override
	public Optional<MobData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<MobData> otherData_ = dataHolder.get(MobData.class);
		if (otherData_.isPresent()) {
			MobData otherData = otherData_.get();
			MobData finalData = overlap.merge(this, otherData);
			this.internalName = finalData.internalName;
			this.level = finalData.level;
			this.damage = finalData.damage;
			this.defence = finalData.defence;
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<MobData> from(DataContainer container) {
        return from((DataView) container);
    }
	
	public Optional<MobData> from(DataView view) {
		if(!view.contains(MobKeys.ID.getQuery(),
				MobKeys.LEVEL.getQuery(),
				MobKeys.DAMAGE.getQuery(),
				MobKeys.DEFENCE.getQuery()))
			return Optional.empty();
		
		this.internalName = view.getString(MobKeys.ID.getQuery()).get();
		this.level = view.getInt(MobKeys.LEVEL.getQuery()).get();
		this.damage = view.getDouble(MobKeys.DAMAGE.getQuery()).get();
		this.defence = view.getDouble(MobKeys.DEFENCE.getQuery()).get();
		
		return Optional.of(this);
	}
	
	@Override
	public MobData copy() {
		return new MobData(this.internalName, this.level, this.damage, this.defence);
	}
	
	
	@Override
	public ImmutableMobData asImmutable() {
		return new ImmutableMobData(this.internalName, this.level, this.damage, this.defence);
	}

    @Override
    public int getContentVersion() {
        return 2;
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
