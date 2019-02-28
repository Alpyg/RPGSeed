package io.alpyg.rpg.gameplay.gathering.data;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

public class GatheringData extends AbstractData<GatheringData, ImmutableGatheringData> {
	
	private String internalName;
	private String type;
	private String material;
	private String tool;
	private Map<UUID, Integer> uses;
	
	public GatheringData(String node, String type, String material, String tool, Map<UUID, Integer> uses) {
		this.internalName = node;
		this.type = type;
		this.material = material;
		this.tool = tool;
		this.uses = uses;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(GatheringKeys.ID, () -> this.internalName);
		registerFieldGetter(GatheringKeys.TYPE, () -> this.type);
		registerFieldGetter(GatheringKeys.MATERIAL, () -> this.material);
		registerFieldGetter(GatheringKeys.TOOL, () -> this.tool);
		registerFieldGetter(GatheringKeys.USES, () -> this.uses);

		registerFieldSetter(GatheringKeys.ID, x -> this.internalName = x);
		registerFieldSetter(GatheringKeys.TYPE, x -> this.type = x);
		registerFieldSetter(GatheringKeys.MATERIAL, x -> this.material = x);
		registerFieldSetter(GatheringKeys.TOOL, x -> this.tool = x);
		registerFieldSetter(GatheringKeys.USES, x -> this.uses = x);
		
		registerKeyValue(GatheringKeys.ID, this::node);
		registerKeyValue(GatheringKeys.TYPE, this::type);
		registerKeyValue(GatheringKeys.MATERIAL, this::material);
		registerKeyValue(GatheringKeys.TOOL, this::tool);
		registerKeyValue(GatheringKeys.USES, this::uses);
	}
	
	public Value<String> node() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.ID, internalName);
    }
	
	public Value<String> type() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.TYPE, type);
    }
	
	public Value<String> material() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.MATERIAL, material);
    }
	
	public Value<String> tool() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.TOOL, tool);
    }
    
    public MapValue<UUID, Integer> uses() {
    	return Sponge.getRegistry().getValueFactory().createMapValue(GatheringKeys.USES, uses);
    }

    @Override
    public Optional<GatheringData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<GatheringData> otherData_ = dataHolder.get(GatheringData.class);
        if (otherData_.isPresent()) {
        	GatheringData otherData = otherData_.get();
        	GatheringData finalData = overlap.merge(this, otherData);
            this.internalName = finalData.internalName;
            this.type = finalData.type;
            this.material = finalData.material;
            this.tool = finalData.tool;
            this.uses = finalData.uses;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<GatheringData> from(DataContainer container) {
        return from((DataView) container);
    }

	@SuppressWarnings("unchecked")
	public Optional<GatheringData> from(DataView view) {
        if (!(view.contains(GatheringKeys.ID.getQuery())
        		&& view.contains(GatheringKeys.TYPE.getQuery())
        		&& view.contains(GatheringKeys.MATERIAL.getQuery())
        		&& view.contains(GatheringKeys.TOOL.getQuery())
        		&& view.contains(GatheringKeys.USES.getQuery())))
            return Optional.empty();
        
        this.internalName = view.getString(GatheringKeys.ID.getQuery()).get();
        this.type = view.getString(GatheringKeys.TYPE.getQuery()).get();
        this.material = view.getString(GatheringKeys.MATERIAL.getQuery()).get();
        this.tool = view.getString(GatheringKeys.TOOL.getQuery()).get();
        this.uses = (Map<UUID, Integer>) view.getMap(GatheringKeys.USES.getQuery()).get();
        
        return Optional.of(this);
    }
    
    @Override
    public GatheringData copy() {
        return new GatheringData(this.internalName, this.type, this.material, this.tool, this.uses);
    }

    @Override
    public ImmutableGatheringData asImmutable() {
        return new ImmutableGatheringData(this.internalName, this.type, this.material, this.tool, this.uses);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
				.set(GatheringKeys.ID.getQuery(), this.internalName)
				.set(GatheringKeys.TYPE.getQuery(), this.type)
                .set(GatheringKeys.MATERIAL.getQuery(), this.material)
                .set(GatheringKeys.TOOL.getQuery(), this.tool)
                .set(GatheringKeys.USES.getQuery(), this.uses);
	}
}
