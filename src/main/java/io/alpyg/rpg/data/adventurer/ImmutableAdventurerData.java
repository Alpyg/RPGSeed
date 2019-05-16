package io.alpyg.rpg.data.adventurer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class ImmutableAdventurerData extends AbstractImmutableData<ImmutableAdventurerData, AdventurerData> {

	private AdventurerStats stats;
	private double max_mana;
	private double mana;
	
	private int balance;
	
	public ImmutableAdventurerData(AdventurerStats stats, int balance, double mana) {
		this.stats = stats;
		this.max_mana = AdventurerStats.getMaxMana(stats.magic);
		this.mana = mana;
		
		this.balance = balance;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(AdventurerKeys.STATS, () -> this.stats);
		registerFieldGetter(AdventurerKeys.MAX_MANA, () -> this.max_mana);
		registerFieldGetter(AdventurerKeys.MANA, () -> this.mana);
		registerFieldGetter(AdventurerKeys.BALANCE, () -> this.balance);

		registerKeyValue(AdventurerKeys.STATS, this::stats);
		registerKeyValue(AdventurerKeys.MAX_MANA, this::max_mana);
		registerKeyValue(AdventurerKeys.MANA, this::mana);
		registerKeyValue(AdventurerKeys.BALANCE, this::balance);
	}

    public ImmutableValue<AdventurerStats> stats() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.STATS, stats).asImmutable();
    }
	
    public ImmutableValue<Double> max_mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MAX_MANA, max_mana).asImmutable();
    }

    public ImmutableValue<Double> mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MANA, mana).asImmutable();
    }
	
	public ImmutableValue<Integer> balance() {
		return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BALANCE, this.balance).asImmutable();
	}
	
	@Override
    public AdventurerData asMutable() {
        return new AdventurerData(this.stats, this.balance, this.mana);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(AdventurerKeys.STATS.getQuery(), this.stats)
                .set(AdventurerKeys.MAX_MANA.getQuery(), this.max_mana)
                .set(AdventurerKeys.MANA.getQuery(), this.mana)
                .set(AdventurerKeys.BALANCE.getQuery(), this.balance);
	}
	
}
