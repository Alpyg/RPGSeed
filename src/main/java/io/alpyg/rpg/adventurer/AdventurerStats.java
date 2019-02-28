package io.alpyg.rpg.adventurer;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class AdventurerStats implements DataSerializable {

    public static final DataQuery TOTAL_POINTS = DataQuery.of("Total Points");
    public static final DataQuery POINTS = DataQuery.of("Points");
    public static final DataQuery VITALITY = DataQuery.of("Vitality");
    public static final DataQuery STRENGHT = DataQuery.of("Strength");
    public static final DataQuery DEFENCE = DataQuery.of("Defence");
    public static final DataQuery AGILITY = DataQuery.of("Agility");
    public static final DataQuery MAGIC = DataQuery.of("Magic");

    public int total_points = 0;
    public int points = 0;
    public int vitality = 0;
    public int strength = 0;
    public int defence = 0;
    public int agility = 0;
    public int magic = 0;

    public AdventurerStats() { }
    
    public AdventurerStats(int points, int vitality, int strength, int defence, int agility, int magic) {
    	this.points = points;
    	this.vitality = vitality;
    	this.strength = strength;
    	this.defence = defence;
    	this.agility = agility;
    	this.magic = magic;
    }
    
    public void totalPoints() {
    	total_points = points + vitality + strength + defence + agility + magic;
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

    @Override
    public DataContainer toContainer() {
        return DataContainer.createNew()
        		.set(TOTAL_POINTS, this.total_points)
                .set(POINTS, this.points)
                .set(VITALITY, this.vitality)
                .set(STRENGHT, this.strength)
                .set(DEFENCE, this.defence)
                .set(AGILITY, this.agility)
                .set(MAGIC, this.magic)
                .set(Queries.CONTENT_VERSION, 2);
    }
    
	public static double getMaxHealth(int vitality) {
		return vitality * 5 + 20;
	}
	
	public static double getMaxMana(int magic) {
		return magic * 5;
	}
	
	public static void setStat(Player p, String stat, int amount) {
		if (stat == "Points")
			p.get(AdventurerKeys.STATS).get().points = amount;
		else if (stat == "Vitality")
			p.get(AdventurerKeys.STATS).get().vitality = amount;
		else if (stat == "Strength")
			p.get(AdventurerKeys.STATS).get().strength = amount;
		else if (stat == "Defence")
			p.get(AdventurerKeys.STATS).get().defence = amount;
		else if (stat == "Agility")
			p.get(AdventurerKeys.STATS).get().agility = amount;
		else if (stat == "Magic")
			p.get(AdventurerKeys.STATS).get().magic = amount;
		
		updatePlayerStats(p);
	}

	public static void updatePlayerStats(Player p) {
		p.get(AdventurerKeys.STATS).get().totalPoints();
		p.offer(Keys.MAX_HEALTH, getMaxHealth(p.get(AdventurerKeys.STATS).get().vitality));
		p.offer(Keys.HEALTH, p.get(Keys.MAX_HEALTH).get());
		p.offer(AdventurerKeys.MAX_MANA, getMaxMana(p.get(AdventurerKeys.STATS).get().magic));
		p.offer(AdventurerKeys.MANA, p.get(AdventurerKeys.MAX_MANA).get());
		if (AdventurerUI.gui.containsKey(p.getUniqueId()))
			AdventurerUI.gui.get(p.getUniqueId()).stop();
		AdventurerUI.gui.put(p.getUniqueId(), new AdventurerUI(p));
	}
	
	public static void deathPenalty(Player p) {
//		if (p.get(AdventurerKeys.STATS).get().total_points < 1)
//				return;
		
//		int i = new Random().nextInt(6);
//		
//		if (i == 1)
//			key = AdventurerKeys.VITALITY;
//		else if (i == 2)
//			key = AdventurerKeys.STRENGTH;
//		else if (i == 3)
//			key = AdventurerKeys.DEFENCE;
//		else if (i == 4)
//			key = AdventurerKeys.AGILITY;
//		else if (i == 5)
//			key = AdventurerKeys.MAGIC;
//		
//		if (p.get(key).get() > 0) {
//			p.offer(key, p.get(key).get() - 1);
//		} else {
//			deathPenalty(p);
//		}
	}
	
}
