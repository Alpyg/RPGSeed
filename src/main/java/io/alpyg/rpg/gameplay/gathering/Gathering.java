package io.alpyg.rpg.gameplay.gathering;

public class Gathering  {
	
//	static {
//		Task.builder().execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				for (EntitySnapshot nodeSnapshot : worldNodes) {
//					
//					Entity node = nodeSnapshot.restore().get();
//					node.offer(GatheringKeys.USES, new HashMap<UUID, Integer>());
//					
//					for (Player p : Sponge.getServer().getOnlinePlayers()) {
//						p.sendBlockChange(node.getLocation().getPosition().toInt().add(new Vector3i(0, 2, 0)), BlockTypes.AIR.getDefaultState());
//						p.sendMessage(Text.of(TextColors.YELLOW, "Gathering Nodes Reset."));
//					}
//				}
//			}
//			
//		}).interval(1, TimeUnit.MINUTES).submit(Seed.plugin);
//	}
}
