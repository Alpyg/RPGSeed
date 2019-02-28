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

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

public class MeleeAttackAITask extends AbstractAITask<Agent> {

	private static AITaskType TYPE;

	public static void register(final Object plugin, final GameRegistry gameRegistry) {
		TYPE = gameRegistry.registerAITaskType(plugin, "melee_attack", "MeleeAttack", MeleeAttackAITask.class);
	}
	
	private double MOVEMENT_SPEED = 1;
	private double DISTANCE = 2 * 2;
	private float EXECUTION_CHANCE = 1F;
	private int MUTEX_FLAG_MOVE = 1;

	private Optional<Entity> optTarget;

	public MeleeAttackAITask(double maxDistance, double movementSpeed, float executionChance) {
		super(TYPE);
		MOVEMENT_SPEED = movementSpeed;
		EXECUTION_CHANCE = executionChance;
		DISTANCE = maxDistance * maxDistance;
		((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
	}

	@Override
	public boolean canRunConcurrentWith(final AITask<Agent> other) {
	    return (((EntityAIBase) (Object) this).getMutexBits() & ((EntityAIBase) other).getMutexBits()) == 0;
	}

	@Override
	public boolean canBeInterrupted() {
	    return false;
	}
	
	@Override
	public boolean shouldUpdate() {
	    final Agent owner = getOwner().get();
	    if (owner.getRandom().nextFloat() > EXECUTION_CHANCE)
	        return false;

	    final Vector3d position = getPositionOf(owner);
	    this.optTarget = owner.getWorld()
	            .getEntities().stream()
	            .filter(e -> getPositionOf(e).distanceSquared(position) < DISTANCE && e != owner)
	            .min(Comparator.comparingDouble(e -> getPositionOf(e).distanceSquared(position)));
	    return this.optTarget.isPresent();
	}

	@Override
	public void start() {
	    getNavigator().tryMoveToEntityLiving((net.minecraft.entity.Entity) this.optTarget.get(), MOVEMENT_SPEED);
	}

	@Override
	public boolean continueUpdating() {
	    if (!this.optTarget.isPresent()) {
		    System.out.println("No Target");
	        return true;
	    }
	    final Entity target = this.optTarget.get();
	    return getPositionOf(target).distanceSquared(getPositionOf(getOwner().get())) < DISTANCE;
	}

	@Override
	public void update() {
		start();
	}

	@Override
	public void reset() {
	    getNavigator().clearPath();
	    this.optTarget = Optional.empty();
	}
	
	private Vector3d getPositionOf(final Entity entity) {
	    return entity.getLocation().getPosition();
	}

	private PathNavigate getNavigator() {
	    return ((EntityLiving) (getOwner().get())).getNavigator();
	}
}
