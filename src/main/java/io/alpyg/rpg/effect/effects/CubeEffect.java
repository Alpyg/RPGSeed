package io.alpyg.rpg.effect.effects;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.effect.EffectType;

public class CubeEffect implements EffectType {

	@Override
	public Vector3d run(Vector3d v) {
		return v;
	}

}
