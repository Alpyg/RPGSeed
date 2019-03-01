package io.alpyg.rpg.gameplay.gathering.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableGatherData extends AbstractImmutableData<ImmutableGatherData, GatherData> {

	private String internalName;
	private String type;
	private String material;
	private String tool;
	
	public ImmutableGatherData(String node, String type, String material, String tool) {
		this.internalName = node;
		this.type = type;
		this.material = material;
		this.tool = tool;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(GatherKeys.ID, () -> this.internalName);
		registerFieldGetter(GatherKeys.TYPE, () -> this.type);
		registerFieldGetter(GatherKeys.MATERIAL, () -> this.material);
		registerFieldGetter(GatherKeys.TOOL, () -> this.tool);

		registerKeyValue(GatherKeys.ID, this::node);
		registerKeyValue(GatherKeys.TYPE, this::type);
		registerKeyValue(GatherKeys.MATERIAL, this::material);
		registerKeyValue(GatherKeys.TOOL, this::tool);
	}
	
	public ImmutableValue<String> node() {
		return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.ID, internalName).asImmutable();
	}
	
	public ImmutableValue<String> type() {
		return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.TYPE, type).asImmutable();
	}
	
	public ImmutableValue<String> material() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.MATERIAL, material).asImmutable();
    }
	
	public ImmutableValue<String> tool() {
        return Sponge.getRegistry().getValueFactory().createValue(GatherKeys.TOOL, tool).asImmutable();
    }
	
	@Override
    public GatherData asMutable() {
        return new GatherData(this.internalName, this.type, this.material, this.tool);
    }

    @Override
    public int getContentVersion() {
        return 1;
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
