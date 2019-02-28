package io.alpyg.rpg.npcs.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableNpcData extends AbstractImmutableData<ImmutableNpcData, NpcData> {

	private String quest;
	
	public ImmutableNpcData(String quest) {
		this.quest = quest;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(NpcKeys.QUEST, () -> this.quest);

		registerKeyValue(NpcKeys.QUEST, this::quest);
	}

    public ImmutableValue<String> quest() {
        return Sponge.getRegistry().getValueFactory().createValue(NpcKeys.QUEST, quest).asImmutable();
    }
	
    @Override
    public NpcData asMutable() {
        return new NpcData(this.quest);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(NpcKeys.QUEST.getQuery(), this.quest);
	}
	
}
