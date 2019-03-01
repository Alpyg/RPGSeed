package io.alpyg.rpg.gameplay.gathering;

import java.util.Random;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.gameplay.gathering.data.GatherData;
import io.alpyg.rpg.gameplay.gathering.data.GatherKeys;

public class GatherNode {
	
	public GatherNode(Location<World> location, GatherConfig config) {
		Entity node = location.getExtent().createEntity(EntityTypes.ARMOR_STAND, location.getPosition().sub(0, 1.502, 0));
		
		// Node Data
		node.offer(node.getOrCreate(GatherData.class).get());
		node.offer(GatherKeys.ID, config.internalName);
		node.offer(GatherKeys.MATERIAL, config.material);
		node.offer(GatherKeys.TOOL, config.tool);
		
		// ArmorStand Data
		node.offer(Keys.DISPLAY_NAME, config.displayName);
		node.offer(Keys.ARMOR_STAND_HAS_BASE_PLATE, false);
		node.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		node.offer(Keys.HAS_GRAVITY, false);
		node.offer(Keys.INVISIBLE, true);
		
		// ArmorStand Rotation
		node.offer(Keys.HEAD_ROTATION, new Vector3d(340, 40, 0));
		node.offer(Keys.RIGHT_ARM_ROTATION, new Vector3d(190, 100, 10));
		node.offer(Keys.LEFT_ARM_ROTATION, new Vector3d(330, 90, 60));
		node.setRotation(new Vector3d(0, new Random().nextFloat() * 360, 0));

		// ArmorStand Equipment
		Equipable nodeEquipment = (Equipable) node;
		nodeEquipment.equip(EquipmentTypes.HEADWEAR, ItemStack.of(config.type));
		nodeEquipment.equip(EquipmentTypes.MAIN_HAND, ItemStack.of(config.type));
		nodeEquipment.equip(EquipmentTypes.OFF_HAND, ItemStack.of(config.type));

		try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
			location.getExtent().spawnEntity(node);
		}
	}
	
//	public static void gatherNode(Player p, Entity node) {
//		Map<UUID, Integer> nodeData = node.get(GatherKeys.USES).get();
//		int nodeUses;
//		
//		if (nodeData.containsKey(p.getUniqueId())) {
//			nodeUses = nodeData.get(p.getUniqueId());
//			
//			if (nodeUses >= 1) {
//				node.get(GatherKeys.USES).get().put(p.getUniqueId(), nodeUses - 1);
//				gatherEffect(p, node.getLocation().getPosition());
//				offerItemStack(p, node);
//
//				p.playSound(SoundTypes.BLOCK_ANVIL_PLACE, node.getLocation().getPosition(), 0.2, 5);
//				if (nodeUses == 1)
//					p.playSound(SoundTypes.ENTITY_PLAYER_ATTACK_CRIT, node.getLocation().getPosition(), 1, 0.75);
//			}
//			
//			else
//				GatherNode.gatherFail(p, node.getLocation());
//		}
//		
//		else {
//			nodeData.put(p.getUniqueId(), 2);
//			p.playSound(SoundTypes.BLOCK_ANVIL_PLACE, node.getLocation().getPosition(), 0.2, 5);
//			gatherEffect(p, node.getLocation().getPosition());
//			offerItemStack(p, node);
//		}
//	}
//	
//	private static void gatherFail(Player p, Location<World> location) {
//		AdventurerUI.gui.get(p.getUniqueId()).display(Text.of(TextColors.RED, "This node is empty."), 2000);
//		p.playSound(SoundTypes.BLOCK_STONE_BREAK, location.getPosition(), 1, 3);
//	}
//	
//	private static void gatherEffect(Player p, Vector3d position) {
//		for (int i = 0; i < 30; i++)
//			p.spawnParticles(ParticleEffect.builder().type(ParticleTypes.CRITICAL_HIT).build(),
//					position.add(VectorUtils.randomVector3d().mul(0.6)).add(0, 1.75, 0));
//	}
//	
//	private static void offerItemStack(Player p, Entity node) {
//		String material = node.get(GatherKeys.MATERIAL).get();
//		if (ItemConfig.items.containsKey(material))
//			p.getInventory().offer(ItemConfig.items.get(material).getItemStack());
//		else if (Sponge.getRegistry().getType(ItemType.class, material).isPresent())
//			p.getInventory().offer(ItemStack.of(Sponge.getRegistry().getType(ItemType.class, material).get()));
//		else
//			Seed.getLogger().warn("Invalid Material Type for Gathering Node " + node.get(GatherKeys.ID).get());
//	}
	
}
