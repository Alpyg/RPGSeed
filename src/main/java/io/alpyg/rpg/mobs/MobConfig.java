package io.alpyg.rpg.mobs;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.alpyg.rpg.Seed;
import ninja.leaping.configurate.ConfigurationNode;

public class MobConfig {

	public static HashMap<String, MobConfig> mobs = new HashMap<String, MobConfig>();
	
	protected String internalName;
	protected EntityType entityType;
	protected Text displayName;
	protected double health;
	protected double damage;
	protected double defence;
	protected double level;
	protected double movementSpeed;
	protected double knockbackResist;
	protected double followRange;
	protected double attackSpeed;
	protected boolean optionAlwaysShowName;
	protected boolean optionIsBaby;
	protected boolean optionSilent;
	protected boolean optionNoAI;
	protected boolean optionGlowing;
	protected boolean optionInvincible;
	protected boolean optionNoGravity;
	protected ConfigurationNode aiGoals;
	protected ConfigurationNode aiTargets;
	protected String mainHand;
	protected String offHand;
	protected String head;
	protected String chest;
	protected String legs;
	protected String feet;
	
	public MobConfig(String internalName, ConfigurationNode config) {
		this.internalName = internalName;
        this.entityType = setEntityType(config.getNode("Type").getString());
		this.displayName = TextSerializers.FORMATTING_CODE.deserialize(config.getNode("Display").getString(internalName));
		this.health = config.getNode("Health").getDouble(10.0);
		this.damage = config.getNode("Damage").getDouble(1.0);
		this.defence = config.getNode("Defence").getDouble(0.0);
		this.level = config.getNode("Level").getDouble(0.0);
		this.movementSpeed = config.getNode("Options", "MovementSpeed").getDouble(2.0);
		this.knockbackResist = config.getNode("Options", "KnockbackResistance").getDouble(0.0);
		this.followRange = config.getNode("Options", "FollowRange", 0.0).getDouble(0.0);
		this.attackSpeed = config.getNode("Options", "AttackSpeed", 0.0).getDouble(0.0);
		
		this.optionAlwaysShowName = config.getNode("Options", "NameAlwaysVisible").getBoolean(true);
		this.optionIsBaby = config.getNode("Options", "IsBaby").getBoolean(false);
        this.optionSilent = config.getNode("Options", "Silent").getBoolean(false);
        this.optionNoAI = config.getNode("Options", "NoAI").getBoolean(false);
        this.optionGlowing = config.getNode("Options", "Glowing").getBoolean(false);
        this.optionInvincible = config.getNode("Options", "Invincible").getBoolean(false);
        this.optionNoGravity = config.getNode("Options", "NoGravity").getBoolean(false);				
        
        this.aiGoals = config.getNode("AIGoals");
        this.aiTargets = config.getNode("AITargets");
        
        this.mainHand = config.getNode("Equipment", "MainHand").getString("");
        this.offHand = config.getNode("Equipment", "OffHand").getString("");
        this.head = config.getNode("Equipment", "Helmet").getString("");
        this.chest = config.getNode("Equipment", "Chestplate").getString("");
        this.legs = config.getNode("Equipment", "Leggings").getString("");
        this.feet = config.getNode("Equipment", "Boots").getString("");
        
        if (entityType != null)
        	mobs.put(internalName, this);
	}
	
	public void spawnMob(Location<World> location) {
		new Mob(location, this);
	}
	
	private EntityType setEntityType(String type) {
		if (Sponge.getRegistry().getType(EntityType.class, type).isPresent())
			return Sponge.getRegistry().getType(EntityType.class, type).get();
		else
			Seed.getLogger().warn("Invalid Entity Type  for " + this.internalName);
		return null;
	}

	public static Supplier<Collection<String>> getMobsConfig() {
		return new Supplier<Collection<String>>() {

			@Override
			public Collection<String> get() {
				return mobs.keySet();
			}
			
		};
	}
	
	public static Function<String, MobConfig> getMobConfig() {
		return new Function<String, MobConfig>() {

			@Override
			public MobConfig apply(String t) {
				return mobs.get(t);
			}
			
		};
	}
}
