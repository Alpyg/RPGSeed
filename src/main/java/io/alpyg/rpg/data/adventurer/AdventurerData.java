package io.alpyg.rpg.data.adventurer;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class AdventurerData extends AbstractData<AdventurerData, ImmutableAdventurerData> {

	private AdventurerStats stats;
	private double max_mana;
	private double mana;
	
	private int balance;
	
	public AdventurerData(AdventurerStats stats, int balance, double mana) {
		this.stats = stats;
		this.max_mana = AdventurerStats.getMaxMana(stats.magic);
		this.mana = mana;
		
		this.balance = balance;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(AdventurerKeys.STATS, () -> this.stats);
		registerFieldGetter(AdventurerKeys.MAX_MANA, () -> this.max_mana);
		registerFieldGetter(AdventurerKeys.MANA, () -> this.mana);
		registerFieldGetter(AdventurerKeys.BALANCE, () -> this.balance);

		registerFieldSetter(AdventurerKeys.STATS, x -> this.stats = x);
		registerFieldSetter(AdventurerKeys.MAX_MANA, x -> this.max_mana = x);
		registerFieldSetter(AdventurerKeys.MANA, x -> this.mana = x);
		registerFieldSetter(AdventurerKeys.BALANCE, x -> this.balance = x);

		registerKeyValue(AdventurerKeys.STATS, this::stats);
		registerKeyValue(AdventurerKeys.MAX_MANA, this::max_mana);
		registerKeyValue(AdventurerKeys.MANA, this::mana);
		registerKeyValue(AdventurerKeys.BALANCE, this::balance);
	}

    public Value<AdventurerStats> stats() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.STATS, this.stats);
    }

    public Value<Double> max_mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MAX_MANA, this.max_mana);
    }

    public Value<Double> mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MANA, this.mana);
    }

    public Value<Integer> balance() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BALANCE, this.balance);
    }

    @Override
    public Optional<AdventurerData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<AdventurerData> otherData_ = dataHolder.get(AdventurerData.class);
        if (otherData_.isPresent()) {
        	AdventurerData otherData = otherData_.get();
        	AdventurerData finalData = overlap.merge(this, otherData);
            this.stats = finalData.stats;
            this.max_mana = finalData.max_mana;
            this.mana = finalData.mana;
            this.balance = finalData.balance;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<AdventurerData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<AdventurerData> from(DataView view) {
        if (!view.contains(AdventurerKeys.STATS.getQuery(),
        		AdventurerKeys.MAX_MANA.getQuery(),
        		AdventurerKeys.MANA.getQuery(),
        		AdventurerKeys.BALANCE.getQuery()))
        	return Optional.empty();
        
        this.stats = view.getSerializable(AdventurerKeys.STATS.getQuery(), AdventurerStats.class).get();
        this.max_mana = view.getDouble(AdventurerKeys.MAX_MANA.getQuery()).get();
        this.mana = view.getDouble(AdventurerKeys.MANA.getQuery()).get();
        this.balance = view.getInt(AdventurerKeys.BALANCE.getQuery()).get();

        return Optional.of(this);
    }
    
    @Override
    public AdventurerData copy() {
        return new AdventurerData(this.stats, this.balance, this.mana);
    }

    @Override
    public ImmutableAdventurerData asImmutable() {
        return new ImmutableAdventurerData(this.stats, this.balance, this.mana);
    }

    @Override
    public int getContentVersion() {
        return 2;
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
