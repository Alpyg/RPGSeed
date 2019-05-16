package io.alpyg.rpg.adventurer;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;

import io.alpyg.rpg.Rpgs;
import io.alpyg.rpg.data.adventurer.AdventurerKeys;
import io.alpyg.rpg.utils.VectorUtils;

public class AdventurerUI {
	
	public static HashMap<UUID, AdventurerUI> gui = new HashMap<UUID, AdventurerUI>();
	
	private ServerBossBar healthBar = ServerBossBar.builder().color(BossBarColors.RED).name(Text.of("HP")).overlay(BossBarOverlays.NOTCHED_10).build();
	private ServerBossBar manaBar = ServerBossBar.builder().color(BossBarColors.BLUE).name(Text.of("MP")).overlay(BossBarOverlays.NOTCHED_10).build();
	
	private final Player player;
	private Task barsTask;
	private Task titleTask;
	
	public AdventurerUI(Player player) {
		this.player = player;
		if (player.get(Keys.HEALTH).get() > 0)
			this.healthBar.addPlayer(player);
		if (player.get(AdventurerKeys.STATS).get().magic > 0)
			this.manaBar.addPlayer(player);
		
		this.barsTask = Task.builder().execute(new Consumer<Task>() {

			@Override
			public void accept(Task t) {
				if (player.isOnline())
					updateUI(player);
				else
					t.cancel();
			}
			
		}).interval(200, TimeUnit.MILLISECONDS).submit(Rpgs.plugin);
		
		this.titleTask = Task.builder().execute(new Runnable() { @Override public void run() {  }}).submit(Rpgs.plugin);
	}
	
	private void updateUI(Player player) {
		if (player.get(Keys.HEALTH).get() >= 0.0 && player.get(Keys.MAX_HEALTH).get() != 0) {
			this.healthBar.setName(Text.of(TextColors.RED, Math.round(player.get(Keys.HEALTH).get()), "/", Math.round(player.get(Keys.MAX_HEALTH).get()), " HP"));
			this.healthBar.setPercent(player.get(Keys.HEALTH).get().floatValue() / player.get(Keys.MAX_HEALTH).get().floatValue());
		}
		
		if (player.get(AdventurerKeys.MANA).get() >= 0.0 && player.get(AdventurerKeys.MAX_MANA).get() != 0) {
			this.manaBar.setName(Text.of(TextColors.AQUA, Math.round(player.get(AdventurerKeys.MANA).get()), "/", Math.round(player.get(AdventurerKeys.MAX_MANA).get()), " MP"));
			this.manaBar.setPercent((float) (player.get(AdventurerKeys.MANA).get() / player.get(AdventurerKeys.MAX_MANA).get()));
		}
		
		player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.WHITE, player.getLocation().getBlockX(),
				"   ", VectorUtils.getDirection(VectorUtils.roundYaw(Math.abs(player.getRotation().getY()))),
				"   ", player.getLocation().getBlockZ()));
		
		player.offer(Keys.FOOD_LEVEL, 20);
	}
	
	public void display(Text text) {
		player.sendTitle(Title.of(Text.of(), text));
		clear(500);
	}
	
	public void display(Text text, int duration) {
		player.sendTitle(Title.of(Text.of(), text));
		clear(duration);
	}
	
	private void clear(int duration) {
		this.titleTask.cancel();
		this.titleTask = Task.builder().delay(duration, TimeUnit.MILLISECONDS).execute(new Runnable() {
			
			@Override
			public void run() {
				player.sendTitle(Title.of(Text.of(), Text.of()));
			}
			
		}).submit(Rpgs.plugin);
	}
	
	public void stop() {
		this.barsTask.cancel();
		this.healthBar.removePlayer(player);
		this.manaBar.removePlayer(player);
		gui.remove(player.getUniqueId());
	}
}
