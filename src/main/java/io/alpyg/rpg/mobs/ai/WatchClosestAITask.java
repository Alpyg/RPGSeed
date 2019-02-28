package io.alpyg.rpg.mobs.ai;

import java.util.Comparator;
import java.util.Optional;

import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

import com.flowpowered.math.vector.Vector3d;

import net.minecraft.entity.ai.EntityAIBase;

public class WatchClosestAITask extends AbstractAITask<Agent> {
	
	private static AITaskType TYPE;

	public static void register(final Object plugin, final GameRegistry gameRegistry) {
		TYPE = gameRegistry.registerAITaskType(plugin, "watch_closest", "WatchClosest", WatchClosestAITask.class);
	}

	private double DISTANCE_SQUARED = 10;
	private int MUTEX_FLAG_MOVE = 2;
	
	private Optional<Entity> optTarget;

	public WatchClosestAITask(double maxDistance) {
		super(TYPE);
		DISTANCE_SQUARED = maxDistance * maxDistance;
		((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
	}

	@Override
	public boolean canRunConcurrentWith(AITask<Agent> other) {
		return (((EntityAIBase) (Object) this).getMutexBits() & ((EntityAIBase) other).getMutexBits()) == 0;
	}

	@Override
	public boolean canBeInterrupted() {
		return true;
	}

	@Override
	public boolean shouldUpdate() {
	    final Agent owner = getOwner().get();

	    final Vector3d position = getPositionOf(owner);
	    this.optTarget = owner.getWorld()
	            .getEntities().stream()
	            .filter(e -> getPositionOf(e).distanceSquared(position) < DISTANCE_SQUARED && e != owner)
	            .min(Comparator.comparingDouble(e -> getPositionOf(e).distanceSquared(position)));
	    return this.optTarget.isPresent();
	}

	@Override
	public void start() {		
		getOwner().get().lookAt(getPositionOf(this.optTarget.get()).add(0, 1.75, 0));
	}

	@Override
	public boolean continueUpdating() {
	    if (!this.optTarget.isPresent()) {
		    System.out.println("No Target");
	        return true;
	    }
	    final Entity target = this.optTarget.get();
	    return getPositionOf(target).distanceSquared(getPositionOf(getOwner().get())) < DISTANCE_SQUARED;
	}

	@Override
	public void update() {
		start();
	}

	@Override
	public void reset() {
	    this.optTarget = Optional.empty();
	}
	
	private Vector3d getPositionOf(final Entity entity) {
	    return entity.getLocation().getPosition();
	}
}
