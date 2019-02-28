package io.alpyg.rpg.adventurer.data;

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
	
	private String backpackData;
	private int backpackSize;
	
	public AdventurerData(AdventurerStats stats, String backpackData, int backpackSize, double mana) {
		this.stats = stats;
		
		this.max_mana = AdventurerStats.getMaxMana(stats.magic);
		this.mana = mana;
		
		this.backpackData = backpackData;
		this.backpackSize = backpackSize;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(AdventurerKeys.BACKPACK_DATA, () -> this.backpackData);
		registerFieldGetter(AdventurerKeys.BACKPACK_SIZE, () -> this.backpackSize);
		registerFieldGetter(AdventurerKeys.MAX_MANA, () -> this.max_mana);
		registerFieldGetter(AdventurerKeys.MANA, () -> this.mana);
		registerFieldGetter(AdventurerKeys.STATS, () -> this.stats);

		registerFieldSetter(AdventurerKeys.BACKPACK_DATA, x -> this.backpackData = x);
		registerFieldSetter(AdventurerKeys.BACKPACK_SIZE, x -> this.backpackSize = x);
		registerFieldSetter(AdventurerKeys.MAX_MANA, x -> this.max_mana = x);
		registerFieldSetter(AdventurerKeys.MANA, x -> this.mana = x);
		registerFieldSetter(AdventurerKeys.STATS, x -> this.stats = x);
		
		registerKeyValue(AdventurerKeys.BACKPACK_DATA, this::backpackData);
		registerKeyValue(AdventurerKeys.BACKPACK_SIZE, this::backpackSize);
		registerKeyValue(AdventurerKeys.MAX_MANA, this::max_mana);
		registerKeyValue(AdventurerKeys.MANA, this::mana);
		registerKeyValue(AdventurerKeys.STATS, this::adv);
	}

    public Value<String> backpackData() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BACKPACK_DATA, backpackData);
    }

    public Value<Integer> backpackSize() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BACKPACK_SIZE, backpackSize);
    }

    public Value<Double> max_mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MAX_MANA, max_mana);
    }

    public Value<Double> mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MANA, mana);
    }

    public Value<AdventurerStats> adv() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.STATS, stats);
    }

    @Override
    public Optional<AdventurerData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<AdventurerData> otherData_ = dataHolder.get(AdventurerData.class);
        if (otherData_.isPresent()) {
        	AdventurerData otherData = otherData_.get();
        	AdventurerData finalData = overlap.merge(this, otherData);
            this.backpackData = finalData.backpackData;
            this.backpackSize = finalData.backpackSize;
            this.max_mana = finalData.max_mana;
            this.mana = finalData.mana;
            this.stats = finalData.stats;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<AdventurerData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<AdventurerData> from(DataView view) {
        if (!view.contains(AdventurerKeys.BACKPACK_DATA.getQuery(),
        		AdventurerKeys.BACKPACK_SIZE.getQuery(),
        		AdventurerKeys.MAX_MANA.getQuery(),
        		AdventurerKeys.MANA.getQuery(),
        		AdventurerKeys.STATS.getQuery()))
        	return Optional.empty();
        
        this.backpackData = view.getString(AdventurerKeys.BACKPACK_DATA.getQuery()).get();
        this.backpackSize = view.getInt(AdventurerKeys.BACKPACK_SIZE.getQuery()).get();
        this.max_mana = view.getDouble(AdventurerKeys.MAX_MANA.getQuery()).get();
        this.mana = view.getDouble(AdventurerKeys.MANA.getQuery()).get();
        
        this.stats = view.getSerializable(AdventurerKeys.STATS.getQuery(), AdventurerStats.class).get();
        
        return Optional.of(this);
    }
    
    @Override
    public AdventurerData copy() {
        return new AdventurerData(this.stats, this.backpackData, this.backpackSize, this.mana);
    }

    @Override
    public ImmutableAdventurerData asImmutable() {
        return new ImmutableAdventurerData(this.stats, this.backpackData, this.backpackSize, this.mana);
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(AdventurerKeys.BACKPACK_DATA.getQuery(), this.backpackData)
                .set(AdventurerKeys.BACKPACK_SIZE.getQuery(), this.backpackSize)
                .set(AdventurerKeys.MAX_MANA.getQuery(), this.max_mana)
                .set(AdventurerKeys.MANA.getQuery(), this.mana)
                .set(AdventurerKeys.STATS.getQuery(), this.stats);
	}
}
