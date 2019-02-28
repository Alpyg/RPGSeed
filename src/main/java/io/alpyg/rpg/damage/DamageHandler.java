package io.alpyg.rpg.damage;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public class DamageHandler {

	@Listener
	public void onDamage(DamageEntityEvent e) {
		if (e.getTargetEntity() instanceof ArmorStand) {
			e.setCancelled(true);
			return;
		}
		
		if (e.getTargetEntity() instanceof Player) {
			
			// Fall Damage
			if (e.getSource().equals(DamageSources.FALLING))
				if (EnvironmentalDamage.roll((Player) e.getTargetEntity()))
					e.setCancelled(true);
//			else if (e.getSource().equals(DamageSources.DROWNING)) return;
//			else if (e.getSource().equals(DamageSources.FIRE_TICK)) return;
//			else if (e.getSource().equals(DamageSources.GENERIC)) return;
//			else if (e.getSource().equals(DamageSources.MAGIC)) return;
//			else if (e.getSource().equals(DamageSources.MELTING)) return;
//			else if (e.getSource().equals(DamageSources.POISON)) return;
//			else if (e.getSource().equals(DamageSources.STARVATION)) return;
//			else if (e.getSource().equals(DamageSources.VOID)) return;
//			else if (e.getSource().equals(DamageSources.WITHER)) return;
		}
		
		if (e.getCause().first(EntityDamageSource.class).isPresent()) {
			Entity source = e.getCause().first(EntityDamageSource.class).get().getSource();
			
			if (source instanceof Player) {
				Player player = (Player) source;
				
				if (e.getTargetEntity() instanceof Human) {
					e.setCancelled(true);
					return;
				}
				
				// PVE
				if (e.getTargetEntity() instanceof Entity) {
					Entity target = e.getTargetEntity();
					if (!CombatDamage.playerAttackEntity(player, target))
						e.setCancelled(true);
					e.setBaseDamage(0);
				}
				
				// PVP
				else if (e.getTargetEntity() instanceof Player) {
					Player target = (Player) e.getTargetEntity();
					if (!CombatDamage.playerAttackPlayer(player, target))
						e.setCancelled(true);
					e.setBaseDamage(0);
				}
			}
			
			// EVP
			else {
				if (e.getTargetEntity() instanceof Player) {
					Player target = (Player) e.getTargetEntity();
					if (!CombatDamage.entityAttackPlayer(source, target))
						e.setCancelled(true);
					e.setBaseDamage(0);
				}
			}	
		}
	}
}
