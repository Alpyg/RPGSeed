package io.alpyg.rpg.mobs.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.text.Text;

public class ImmutableMobData extends AbstractImmutableData<ImmutableMobData, MobData> {

	private String internalName;
	private Text displayName;
	private double health;
	private double level;
	private double damage;
	private double defence;
	
	public ImmutableMobData(String internalName, Text displayName, double health, double level, double damage, double defence) {
		this.internalName = internalName;
		this.displayName = displayName;
		this.health = health;
		this.level = level;
		this.damage = damage;
		this.defence = defence;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(MobKeys.ID, () -> this.internalName);
		registerFieldGetter(MobKeys.DISPLAY_NAME, () -> this.displayName);
		registerFieldGetter(MobKeys.HEALTH, () -> this.health);
		registerFieldGetter(MobKeys.LEVEL, () -> this.level);
		registerFieldGetter(MobKeys.DAMAGE, () -> this.damage);
		registerFieldGetter(MobKeys.DEFENCE, () -> this.defence);

		registerKeyValue(MobKeys.ID, this::internalName);
		registerKeyValue(MobKeys.DISPLAY_NAME, this::displayName);
		registerKeyValue(MobKeys.HEALTH, this::health);
		registerKeyValue(MobKeys.LEVEL, this::level);
		registerKeyValue(MobKeys.DAMAGE, this::damage);
		registerKeyValue(MobKeys.DEFENCE, this::defence);
	}
	
	public ImmutableValue<String> internalName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.ID, internalName).asImmutable();
    }
	
	public ImmutableValue<Text> displayName() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.DISPLAY_NAME, displayName).asImmutable();
    }
	
	public ImmutableValue<Double> health() {
        return Sponge.getRegistry().getValueFactory().createValue(MobKeys.HEALTH, health).asImmutable();
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
		return new MobData(this.internalName, this.displayName, this.health, this.level, this.damage, this.defence);
	}

    @Override
    public int getContentVersion() {
        return 1;
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
