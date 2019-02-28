package io.alpyg.rpg.mobs.ai;

import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

public class WanderAITask extends AbstractAITask<Agent> {

	private static AITaskType TYPE;

	public static void register(final Object plugin, final GameRegistry gameRegistry) {
		TYPE = gameRegistry.registerAITaskType(plugin, "wander", "Wander", WanderAITask.class);
	}
	
	private double MOVEMENT_SPEED = 1.5;
	private float EXECUTION_CHANCE = 1F;
	private int MUTEX_FLAG_MOVE = 1;

	public WanderAITask(double movementSpeed, float executionChance) {
		super(TYPE);
		MOVEMENT_SPEED = movementSpeed;
		EXECUTION_CHANCE = executionChance;
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
		
		if(owner.getRandom().nextFloat() > EXECUTION_CHANCE) return false;
		return true;
	}

	@Override
	public void start() {
		getNavigator().tryMoveToXYZ(getOwner().get().getLocation().getX() + getRandomPos(), getOwner().get().getLocation().getY(), getOwner().get().getLocation().getZ() + getRandomPos(), MOVEMENT_SPEED);
	}

	@Override
	public boolean continueUpdating() {
		if(getNavigator().noPath()) return false;
		return true;
	}

	@Override
	public void update() {}

	@Override
	public void reset() {
	    getNavigator().clearPath();
	}

	private PathNavigate getNavigator() {
	    return ((EntityLiving) (getOwner().get())).getNavigator();
	}
	
	private double getRandomPos() {
		double pos = getOwner().get().getRandom().nextDouble() * 10;
		if(getOwner().get().getRandom().nextDouble() > 0.5) pos *= -1;
		return pos;
	}

}
