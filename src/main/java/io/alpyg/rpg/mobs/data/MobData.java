package io.alpyg.rpg.mobs.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.text.Text;

public class MobData extends AbstractData<MobData, ImmutableMobData> {

	private String internalName;
	private Text displayName;
	private double health;
	private double level;
	private double damage;
	private double defence;
	
	public MobData(String internalName, Text displayName, double health, double level, double damage, double defence) {
		this.internalName = internalName;
		this.displayName = displayName;
		this.health = health;
		this.level = level;
		this.damage = damage;
		this.defence = defence;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MobKeys.ID, () -> this.internalName);
		registerFieldGetter(MobKeys.DISPLAY_NAME, () -> this.displayName);
		registerFieldGetter(MobKeys.HEALTH, () -> this.health);
		registerFieldGetter(MobKeys.LEVEL, () -> this.level);
		registerFieldGetter(MobKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(MobKeys.DEFENCE, () -> this.defence);
		
		registerFieldSetter(MobKeys.ID, x -> this.internalName = x);
		registerFieldSetter(MobKeys.DISPLAY_NAME, x -> this.displayName = x);
		registerFieldSetter(MobKeys.HEALTH, x -> this.health = x);
		registerFieldSetter(MobKeys.LEVEL, x -> this.level = x);
		registerFieldSetter(MobKeys.DAMAGE, x -> this.damage = x);
		registerFieldSetter(MobKeys.DEFENCE, x -> this.defence = x);
		
		registerKeyValue(MobKeys.ID, this::internalName);
		registerKeyValue(MobKeys.DISPLAY_NAME, this::displayName);
		registerKeyValue(MobKeys.HEALTH, this::health);
		registerKeyValue(MobKeys.LEVEL, this::level);
		registerKeyValue(MobKeys.DAMAGE, this::damage);
		registerKeyValue(MobKeys.DEFENCE, this::defence);
	}
	
	public Value<String> internalName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.ID, internalName);
    }
	
	public Value<Text> displayName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DISPLAY_NAME, displayName);
    }
	
	public Value<Double> health() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.HEALTH, health);
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
			this.displayName = finalData.displayName;
			this.health = finalData.health;
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
				MobKeys.DISPLAY_NAME.getQuery(),
				MobKeys.HEALTH.getQuery(),
				MobKeys.LEVEL.getQuery(),
				MobKeys.DAMAGE.getQuery(),
				MobKeys.DEFENCE.getQuery()))
			return Optional.empty();
		
		this.internalName = view.getString(MobKeys.ID.getQuery()).get();
		this.displayName = (Text) view.get(MobKeys.DISPLAY_NAME.getQuery()).get();
		this.health = view.getInt(MobKeys.HEALTH.getQuery()).get();
		this.level = view.getInt(MobKeys.LEVEL.getQuery()).get();
		this.damage = view.getDouble(MobKeys.DAMAGE.getQuery()).get();
		this.defence = view.getDouble(MobKeys.DEFENCE.getQuery()).get();
		
		return Optional.of(this);
	}
	
	@Override
	public MobData copy() {
		return new MobData(this.internalName, this.displayName, this.health, this.level, this.damage, this.defence);
	}
	
	
	@Override
	public ImmutableMobData asImmutable() {
		return new ImmutableMobData(this.internalName, this.displayName, this.health, this.level, this.damage, this.defence);
	}

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
				.set(MobKeys.ID.getQuery(), this.internalName)
				.set(MobKeys.DISPLAY_NAME.getQuery(), this.displayName)
				.set(MobKeys.HEALTH.getQuery(), this.health)
				.set(MobKeys.LEVEL.getQuery(), this.level)
				.set(MobKeys.DAMAGE.getQuery(), this.damage)
				.set(MobKeys.DEFENCE.getQuery(), this.defence);
	}
}
