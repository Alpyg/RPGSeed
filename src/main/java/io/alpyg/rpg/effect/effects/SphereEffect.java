package io.alpyg.rpg.effect.effects;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.effect.EffectType;
import io.alpyg.rpg.utils.VectorUtils;

public class SphereEffect implements EffectType {

	double radius = 1;
	
	public SphereEffect(double radius) {
		this.radius = radius;
	}

	@Override
	public Vector3d run(Vector3d v) {
        return v.add(VectorUtils.randomVector3d().mul(radius));
	}
	
}
