package io.alpyg.rpg.gameplay.gathering;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.Seed;
import io.alpyg.rpg.adventurer.AdventurerUI;
import io.alpyg.rpg.gameplay.gathering.data.GatheringData;
import io.alpyg.rpg.gameplay.gathering.data.GatheringKeys;
import io.alpyg.rpg.items.ItemConfig;
import io.alpyg.rpg.utils.VectorUtils;

public class GatheringNode {
	
	public GatheringNode(Location<World> location, GatheringConfig config) {
		Entity entity = location.getExtent().createEntity(EntityTypes.ARMOR_STAND, location.getPosition().sub(0, 1.502, 0));
		
		// Node Data
		entity.offer(entity.getOrCreate(GatheringData.class).get());
		entity.offer(GatheringKeys.ID, config.internalName);
		entity.offer(GatheringKeys.MATERIAL, config.material);
		entity.offer(GatheringKeys.TOOL, config.tool);
		entity.offer(GatheringKeys.USES, new HashMap<UUID, Integer>());
		
		// ArmorStand Data
		entity.offer(Keys.DISPLAY_NAME, config.displayName);
		entity.offer(Keys.ARMOR_STAND_HAS_BASE_PLATE, false);
		entity.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		entity.offer(Keys.HAS_GRAVITY, false);
		entity.offer(Keys.INVISIBLE, true);
		
		// ArmorStand Rotation
		entity.offer(Keys.HEAD_ROTATION, new Vector3d(340, 40, 0));
		entity.offer(Keys.RIGHT_ARM_ROTATION, new Vector3d(190, 100, 10));
		entity.offer(Keys.LEFT_ARM_ROTATION, new Vector3d(330, 90, 60));
		entity.setRotation(new Vector3d(0, new Random().nextFloat() * 360, 0));

		// ArmorStand Equipment
		Equipable entityEquipment = (Equipable) entity;
		entityEquipment.equip(EquipmentTypes.HEADWEAR, ItemStack.of(config.type));
		entityEquipment.equip(EquipmentTypes.MAIN_HAND, ItemStack.of(config.type));
		entityEquipment.equip(EquipmentTypes.OFF_HAND, ItemStack.of(config.type));

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(entity);
		}
	}
	
	public static void gatherNode(Player p, Entity node) {
		Map<UUID, Integer> nodeData = node.get(GatheringKeys.USES).get();
		int nodeUses;
		
		if (nodeData.containsKey(p.getUniqueId())) {
			nodeUses = nodeData.get(p.getUniqueId());
			
			if (nodeUses >= 1) {
				node.get(GatheringKeys.USES).get().put(p.getUniqueId(), nodeUses - 1);
				gatherEffect(p, node.getLocation().getPosition());
				offerItemStack(p, node);

				p.playSound(SoundTypes.BLOCK_ANVIL_PLACE, node.getLocation().getPosition(), 0.2, 5);
				if (nodeUses == 1)
					p.playSound(SoundTypes.ENTITY_PLAYER_ATTACK_CRIT, node.getLocation().getPosition(), 1, 0.75);
			}
			
			else
				GatheringNode.gatherFail(p, node.getLocation());
		}
		
		else {
			nodeData.put(p.getUniqueId(), 2);
			p.playSound(SoundTypes.BLOCK_ANVIL_PLACE, node.getLocation().getPosition(), 0.2, 5);
			gatherEffect(p, node.getLocation().getPosition());
			offerItemStack(p, node);
		}
	}
	
	private static void gatherFail(Player p, Location<World> location) {
		AdventurerUI.gui.get(p.getUniqueId()).display(Text.of(TextColors.RED, "This node is empty."), 2000);
		p.playSound(SoundTypes.BLOCK_STONE_BREAK, location.getPosition(), 1, 3);
	}
	
	private static void gatherEffect(Player p, Vector3d position) {
		for (int i = 0; i < 30; i++)
			p.spawnParticles(ParticleEffect.builder().type(ParticleTypes.CRITICAL_HIT).build(),
					position.add(VectorUtils.randomVector3d().mul(0.6)).add(0, 1.75, 0));
	}
	
	private static void offerItemStack(Player p, Entity node) {
		String material = node.get(GatheringKeys.MATERIAL).get();
		if (ItemConfig.items.containsKey(material))
			p.getInventory().offer(ItemConfig.items.get(material).getItemStack());
		else if (Sponge.getRegistry().getType(ItemType.class, material).isPresent())
			p.getInventory().offer(ItemStack.of(Sponge.getRegistry().getType(ItemType.class, material).get()));
		else
			Seed.getLogger().warn("Invalid Material Type for Gathering Node " + node.get(GatheringKeys.ID).get());
	}
}
