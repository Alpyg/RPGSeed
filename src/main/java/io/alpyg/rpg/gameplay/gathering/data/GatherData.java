package io.alpyg.rpg.gameplay.gathering.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class GatherData extends AbstractData<GatherData, ImmutableGatherData> {
	
	private String internalName;
	private String type;
	private String material;
	private String tool;
	
	public GatherData(String node, String type, String material, String tool) {
		this.internalName = node;
		this.type = type;
		this.material = material;
		this.tool = tool;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(GatherKeys.ID, () -> this.internalName);
		registerFieldGetter(GatherKeys.TYPE, () -> this.type);
		registerFieldGetter(GatherKeys.MATERIAL, () -> this.material);
		registerFieldGetter(GatherKeys.TOOL, () -> this.tool);

		registerFieldSetter(GatherKeys.ID, x -> this.internalName = x);
		registerFieldSetter(GatherKeys.TYPE, x -> this.type = x);
		registerFieldSetter(GatherKeys.MATERIAL, x -> this.material = x);
		registerFieldSetter(GatherKeys.TOOL, x -> this.tool = x);
		
		registerKeyValue(GatherKeys.ID, this::node);
		registerKeyValue(GatherKeys.TYPE, this::type);
		registerKeyValue(GatherKeys.MATERIAL, this::material);
		registerKeyValue(GatherKeys.TOOL, this::tool);
	}
	
	public Value<String> node() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.ID, internalName);
    }
	
	public Value<String> type() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.TYPE, type);
    }
	
	public Value<String> material() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.MATERIAL, material);
    }
	
	public Value<String> tool() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.TOOL, tool);
    }

    @Override
    public Optional<GatherData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<GatherData> otherData_ = dataHolder.get(GatherData.class);
        if (otherData_.isPresent()) {
        	GatherData otherData = otherData_.get();
        	GatherData finalData = overlap.merge(this, otherData);
            this.internalName = finalData.internalName;
            this.type = finalData.type;
            this.material = finalData.material;
            this.tool = finalData.tool;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<GatherData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<GatherData> from(DataView view) {
        if (!(view.contains(GatherKeys.ID.getQuery())
        		&& view.contains(GatherKeys.TYPE.getQuery())
        		&& view.contains(GatherKeys.MATERIAL.getQuery())
        		&& view.contains(GatherKeys.TOOL.getQuery())))
            return Optional.empty();
        
        this.internalName = view.getString(GatherKeys.ID.getQuery()).get();
        this.type = view.getString(GatherKeys.TYPE.getQuery()).get();
        this.material = view.getString(GatherKeys.MATERIAL.getQuery()).get();
        this.tool = view.getString(GatherKeys.TOOL.getQuery()).get();
        
        return Optional.of(this);
    }
    
    @Override
    public GatherData copy() {
        return new GatherData(this.internalName, this.type, this.material, this.tool);
    }

    @Override
    public ImmutableGatherData asImmutable() {
        return new ImmutableGatherData(this.internalName, this.type, this.material, this.tool);
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
				.set(GatherKeys.ID.getQuery(), this.internalName)
				.set(GatherKeys.TYPE.getQuery(), this.type)
                .set(GatherKeys.MATERIAL.getQuery(), this.material)
                .set(GatherKeys.TOOL.getQuery(), this.tool);
	}
}
