package io.alpyg.rpg.data.npc;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableNpcData extends AbstractImmutableData<ImmutableNpcData, NpcData> {

	private String id;
	private String quest;
	
	public ImmutableNpcData(String id, String quest) {
		this.id = id;
		this.quest = quest;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(NpcKeys.ID, () -> this.id);
		registerFieldGetter(NpcKeys.QUEST, () -> this.quest);

		registerKeyValue(NpcKeys.ID, this::id);
		registerKeyValue(NpcKeys.QUEST, this::quest);
	}
	
    public ImmutableValue<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(NpcKeys.ID, id).asImmutable();
    }

    public ImmutableValue<String> quest() {
        return Sponge.getRegistry().getValueFactory().createValue(NpcKeys.QUEST, quest).asImmutable();
    }
	
    @Override
    public NpcData asMutable() {
        return new NpcData(this.id, this.quest);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(NpcKeys.ID.getQuery(), this.id)
                .set(NpcKeys.QUEST.getQuery(), this.quest);
	}
	
}
