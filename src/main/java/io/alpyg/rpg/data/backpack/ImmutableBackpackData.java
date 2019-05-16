package io.alpyg.rpg.data.backpack;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableBackpackData extends AbstractImmutableData<ImmutableBackpackData, BackpackData> {


	private int size;
	private String data;
	
	public ImmutableBackpackData(int size, String data) {
		this.size = size;
		this.data = data;

		registerGetters();
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(BackpackKeys.SIZE, () -> this.size);
		registerFieldGetter(BackpackKeys.DATA, () -> this.data);
		
		registerKeyValue(BackpackKeys.SIZE, this::size);
		registerKeyValue(BackpackKeys.DATA, this::data);
	}

    public ImmutableValue<Integer> size() {
        return Sponge.getRegistry().getValueFactory().createValue(BackpackKeys.SIZE, this.size).asImmutable();
    }

    public ImmutableValue<String> data() {
        return Sponge.getRegistry().getValueFactory().createValue(BackpackKeys.DATA, this.data).asImmutable();
    }
	
	@Override
    public BackpackData asMutable() {
        return new BackpackData(this.size, this.data);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(BackpackKeys.SIZE.getQuery(), this.size)
                .set(BackpackKeys.DATA.getQuery(), this.data);
	}

}
