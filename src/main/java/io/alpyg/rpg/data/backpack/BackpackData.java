package io.alpyg.rpg.data.backpack;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class BackpackData extends AbstractData<BackpackData, ImmutableBackpackData> {

	private int size;
	private String data;
	
	public BackpackData(int size, String data) {
		this.size = size;
		this.data = data;

		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(BackpackKeys.SIZE, () -> this.size);
		registerFieldGetter(BackpackKeys.DATA, () -> this.data);

		registerFieldSetter(BackpackKeys.SIZE, x -> this.size = x);
		registerFieldSetter(BackpackKeys.DATA, x -> this.data = x);
		
		registerKeyValue(BackpackKeys.SIZE, this::size);
		registerKeyValue(BackpackKeys.DATA, this::data);
	}

    public Value<Integer> size() {
        return Sponge.getRegistry().getValueFactory().createValue(BackpackKeys.SIZE, this.size);
    }

    public Value<String> data() {
        return Sponge.getRegistry().getValueFactory().createValue(BackpackKeys.DATA, this.data);
    }

    @Override
    public Optional<BackpackData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<BackpackData> otherData_ = dataHolder.get(BackpackData.class);
        if (otherData_.isPresent()) {
        	BackpackData otherData = otherData_.get();
        	BackpackData finalData = overlap.merge(this, otherData);
            this.size = finalData.size;
            this.data = finalData.data;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<BackpackData> from(DataContainer container) {
        return from((DataView) container);
    }

	public Optional<BackpackData> from(DataView view) {
        if (!view.contains(BackpackKeys.SIZE.getQuery(),
        		BackpackKeys.DATA.getQuery()))
        	return Optional.empty();
        
        this.size = view.getInt(BackpackKeys.SIZE.getQuery()).get();
        this.data = view.getString(BackpackKeys.DATA.getQuery()).get();

        return Optional.of(this);
    }
    
    @Override
    public BackpackData copy() {
        return new BackpackData(this.size, this.data);
    }

    @Override
    public ImmutableBackpackData asImmutable() {
        return new ImmutableBackpackData(this.size, this.data);
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

	@Override
	public DataContainer toContainer() {
        return super.toContainer()
                .set(BackpackKeys.SIZE.getQuery(), this.size)
                .set(BackpackKeys.DATA.getQuery(), this.data);
	}
    
}
