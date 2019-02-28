package io.alpyg.rpg.damage;

import java.util.Random;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.flowpowered.math.vector.Vector3d;

import io.alpyg.rpg.adventurer.AdventurerUI;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;
import io.alpyg.rpg.items.data.ItemKeys;
import io.alpyg.rpg.mobs.data.MobKeys;

public class CombatDamage {
	
	private static Text critHitText = Text.of(TextColors.GOLD, "Critical Hit");
	private static Text dodgeText = Text.of(TextColors.AQUA, "Dodged");

	public static boolean playerAttackEntity(Player player, Entity entity) {
		double attack = player.get(AdventurerKeys.STATS).get().strength
				+ player.getItemInHand(HandTypes.MAIN_HAND).get().getOrElse(ItemKeys.DAMAGE, 0.0);
		double defence = entity.get(MobKeys.DEFENCE).orElse(0.0);
		double finalDamage = 1;

		if (criticalHit()) {
			finalDamage = calculateDamage(attack, defence / 2);
			playCriticalHit(player, entity.getLocation().getPosition());
			AdventurerUI.gui.get(player.getUniqueId()).display(critHitText);
		} else
			finalDamage = calculateDamage(attack, defence);
		if (finalDamage < 1) finalDamage = 1;

		damage(entity, finalDamage);
		new DamageIndicator(entity.getLocation(), finalDamage);
		return true;
	}
	
	public static boolean playerAttackPlayer(Player player, Player target) {
		if (dodge(target.get(AdventurerKeys.STATS).get().agility)) {
			AdventurerUI.gui.get(target.getUniqueId()).display(dodgeText);
			return false;
		}
		
		double attack = player.get(AdventurerKeys.STATS).get().strength
				+ player.getItemInHand(HandTypes.MAIN_HAND).get().getOrElse(ItemKeys.DAMAGE, 0.0);
		double defence = target.get(AdventurerKeys.STATS).get().defence;
		double finalDamage = 1;

		if (criticalHit()) {
			finalDamage = calculateDamage(attack, defence / 2);
			playCriticalHit(target, target.getLocation().getPosition());
			AdventurerUI.gui.get(player.getUniqueId()).display(critHitText);
		} else
			finalDamage = calculateDamage(attack, defence);
		if (finalDamage < 1) finalDamage = 1;
		
		damage(target, finalDamage);
		new DamageIndicator(target.getLocation(), finalDamage);
		return true;
	}
	
	public static boolean entityAttackPlayer(Entity entity, Player player) {
		if (dodge(player.get(AdventurerKeys.STATS).get().agility)) {
			AdventurerUI.gui.get(player.getUniqueId()).display(dodgeText);
			return false;
		}
		
		double attack = entity.get(MobKeys.DAMAGE).orElse(1.0);
		double defence = player.get(AdventurerKeys.STATS).get().defence;
		double finalDamage = 1;
		
		if (criticalHit()) {
			finalDamage = calculateDamage(attack, defence / 2);
			playCriticalHit(player, player.getLocation().getPosition());
			AdventurerUI.gui.get(player.getUniqueId()).display(critHitText);
		} else
			finalDamage = calculateDamage(attack, defence);
		if (finalDamage < 1) finalDamage = 1;

		damage(player, finalDamage);
		return true;
	}
	
	private static double calculateDamage(double att, double def) {
		Random r = new Random();
		double damage = att * att / (att + def);
		return damage * 0.8 + 0.2 * r.nextDouble();
	}
	
	private static boolean dodge(int agi) {
		Random r = new Random();
		if (r.nextDouble() < agi / 500)
			return true;
		return false;
	}
	
	private static boolean criticalHit() {
		Random r = new Random();
		if (r.nextDouble() < 0.05)
			return true;
		return false;
	}
	
	private static void playCriticalHit(Player player, Vector3d position) {
		Random r = new Random();
		for (int i = 0; i < 50; i++)
			player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.CRITICAL_HIT).build(),
					position.add(r.nextDouble(), r.nextDouble() + 1.2, r.nextDouble()));
		player.playSound(SoundTypes.ENTITY_PLAYER_ATTACK_CRIT, position, 5);
	}
	
	public static void damage(Entity entity, double damage) {
		entity.offer(Keys.HEALTH, entity.get(Keys.HEALTH).get() - damage);
	}
	
	public static boolean willCauseDeath(Entity entity, double damage) {
		if (entity.get(Keys.HEALTH).get() - damage < 0)
			return true;
		return false;
	}

}
