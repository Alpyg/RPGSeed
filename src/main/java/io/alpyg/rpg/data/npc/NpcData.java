package io.alpyg.rpg.data.npc;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class NpcData extends AbstractData<NpcData, ImmutableNpcData> {

	private String id;
	private String quest;
	
	public NpcData(String id, String quest) {
		this.id = id;
		this.quest = quest;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(NpcKeys.ID, () -> this.id);
		registerFieldGetter(NpcKeys.QUEST, () -> this.quest);

		registerFieldSetter(NpcKeys.ID, x -> this.id = x);
		registerFieldSetter(NpcKeys.QUEST, x -> this.quest = x);

		registerKeyValue(NpcKeys.ID, this::id);
		registerKeyValue(NpcKeys.QUEST, this::quest);
	}

    public Value<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(NpcKeys.ID, id);
    }
    
    public Value<String> quest() {
        return Sponge.getRegistry().getValueFactory().createValue(NpcKeys.QUEST, quest);
    }

    @Override
    public Optional<NpcData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<NpcData> otherData_ = dataHolder.get(NpcData.class);
        if (otherData_.isPresent()) {
        	NpcData otherData = otherData_.get();
        	NpcData finalData = overlap.merge(this, otherData);
        	this.id = finalData.id;
            this.quest = finalData.quest;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<NpcData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<NpcData> from(DataView view) {
        if (!view.contains(NpcKeys.QUEST.getQuery()))
        	return Optional.empty();
        
        this.id = view.getString(NpcKeys.ID.getQuery()).get();
        this.quest = view.getString(NpcKeys.QUEST.getQuery()).get();
        
        return Optional.of(this);
    }
    
    @Override
    public NpcData copy() {
        return new NpcData(this.id, this.quest);
    }

    @Override
    public ImmutableNpcData asImmutable() {
        return new ImmutableNpcData(this.id, this.quest);
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(NpcKeys.ID.getQuery(), this.id)
                .set(NpcKeys.QUEST.getQuery(), this.quest);
	}
	
}
