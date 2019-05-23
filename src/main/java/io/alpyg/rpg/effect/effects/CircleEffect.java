package io.alpyg.rpg.effect.effects;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.effect.EffectType;
import io.alpyg.rpg.utils.VectorUtils;

public class CircleEffect implements EffectType {
	
	double radius = 1;
	boolean hollow = true;
	
	public CircleEffect(double radius, boolean hollow) {
		this.radius = radius;
		this.hollow = hollow;
	}
	
	@Override
	public Vector3d run(Vector3d v) {
		if (hollow)
			return hollow(v);
		else 
			return full(v);
	}
	
	Vector3d hollow(Vector3d v) {
		return v.add(VectorUtils.randomVector3d().mul(1,0,1).normalize().mul(radius));
	}
	
	Vector3d full(Vector3d v) {
		return v.add(VectorUtils.randomVector3d().mul(1,0,1).mul(radius));
	}

}
