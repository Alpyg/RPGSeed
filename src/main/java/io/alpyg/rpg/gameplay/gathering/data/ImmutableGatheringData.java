package io.alpyg.rpg.gameplay.gathering.data;

import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableGatheringData extends AbstractImmutableData<ImmutableGatheringData, GatheringData> {

	private String internalName;
	private String type;
	private String material;
	private String tool;
	private Map<UUID, Integer> uses;
	
	public ImmutableGatheringData(String node, String type, String material, String tool, Map<UUID, Integer> uses) {
		this.internalName = node;
		this.type = type;
		this.material = material;
		this.tool = tool;
		this.uses = uses;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(GatheringKeys.ID, () -> this.internalName);
		registerFieldGetter(GatheringKeys.TYPE, () -> this.type);
		registerFieldGetter(GatheringKeys.MATERIAL, () -> this.material);
		registerFieldGetter(GatheringKeys.TOOL, () -> this.tool);
		registerFieldGetter(GatheringKeys.USES, () -> this.uses);

		registerKeyValue(GatheringKeys.ID, this::node);
		registerKeyValue(GatheringKeys.TYPE, this::type);
		registerKeyValue(GatheringKeys.MATERIAL, this::material);
		registerKeyValue(GatheringKeys.TOOL, this::tool);
		registerKeyValue(GatheringKeys.USES, this::uses);
	}
	
	public ImmutableValue<String> node() {
		return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.ID, internalName).asImmutable();
	}
	
	public ImmutableValue<String> type() {
		return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.TYPE, type).asImmutable();
	}
	
	public ImmutableValue<String> material() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.MATERIAL, material).asImmutable();
    }
	
	public ImmutableValue<String> tool() {
        return Sponge.getRegistry().getValueFactory().createValue(GatheringKeys.TOOL, tool).asImmutable();
    }
	
	public ImmutableMapValue<UUID, Integer> uses() {
		return Sponge.getRegistry().getValueFactory().createMapValue(GatheringKeys.USES, uses).asImmutable();
	}
	
	@Override
    public GatheringData asMutable() {
        return new GatheringData(this.internalName, this.type, this.material, this.tool, this.uses);
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
