package io.alpyg.rpg.adventurer.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import io.alpyg.rpg.adventurer.AdventurerStats;

public class ImmutableAdventurerData extends AbstractImmutableData<ImmutableAdventurerData, AdventurerData> {

	private AdventurerStats stats;
	
	private double max_mana;
	private double mana;
	
	private String backpackData;
	private int backpackSize;
	
	public ImmutableAdventurerData(AdventurerStats stats, String backpackData, int backpackSize, double mana) {
		this.stats = stats;
		
		this.max_mana = AdventurerStats.getMaxMana(stats.magic);
		this.mana = mana;
		
		this.backpackData = backpackData;
		this.backpackSize = backpackSize;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(AdventurerKeys.BACKPACK_DATA, () -> this.backpackData);
		registerFieldGetter(AdventurerKeys.BACKPACK_SIZE, () -> this.backpackSize);
		registerFieldGetter(AdventurerKeys.MAX_MANA, () -> this.max_mana);
		registerFieldGetter(AdventurerKeys.MANA, () -> this.mana);
		registerFieldGetter(AdventurerKeys.STATS, () -> this.stats);

		registerKeyValue(AdventurerKeys.BACKPACK_DATA, this::backpackData);
		registerKeyValue(AdventurerKeys.BACKPACK_SIZE, this::backpackSize);
		registerKeyValue(AdventurerKeys.MAX_MANA, this::max_mana);
		registerKeyValue(AdventurerKeys.MANA, this::mana);
		registerKeyValue(AdventurerKeys.STATS, this::adv);
	}
	
	public ImmutableValue<String> backpackData() {
		return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BACKPACK_DATA, this.backpackData).asImmutable();
	}
	
	public ImmutableValue<Integer> backpackSize() {
		return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.BACKPACK_SIZE, this.backpackSize).asImmutable();
	}
	
    public ImmutableValue<Double> max_mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MAX_MANA, max_mana).asImmutable();
    }

    public ImmutableValue<Double> mana() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.MANA, mana).asImmutable();
    }

    public ImmutableValue<AdventurerStats> adv() {
        return Sponge.getRegistry().getValueFactory().createValue(AdventurerKeys.STATS, stats).asImmutable();
    }
	
	@Override
    public AdventurerData asMutable() {
        return new AdventurerData(this.stats, this.backpackData, this.backpackSize, this.mana);
    }

    @Override
    public int getContentVersion() {
        return 1;
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
