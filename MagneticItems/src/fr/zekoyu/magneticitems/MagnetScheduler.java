package fr.zekoyu.magneticitems;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Item;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.Vector;
//
public class MagnetScheduler/* extends BukkitRunnable */{
//
//	private MagneticItems main;
//	private double radius = 7.25;
//	private double radiusSquared = Math.pow(radius, 2);
//	private ArrayList<Item> itemListClone;
//	private double waterVelocity = 0.07;
//	
//	public MagnetScheduler(MagneticItems main) {
//		this.main = main;
//		this.runTaskTimer(main, 0, 1);
//		itemListClone = new ArrayList<>(main.getItemList());
//	}
//	
//	@Override
//	public void run() {
//		itemListClone.clear();
//		itemListClone.addAll(main.getItemList());
//		for(Item item : itemListClone) {
//			if(!item.isValid()) main.removeItemFromList(item);
//			
//			List<Entity> nearbyEntities = item.getNearbyEntities(radius, radius, radius);
//			if(nearbyEntities != null) {
//				for (Entity entity : nearbyEntities) {
//					if(entity instanceof Player) {
//						double relativeDistance;
//						Vector resultant = null;
//						Player player = (Player) entity;
//						Block block = item.getLocation().getBlock();
//						if(!item.isOnGround() && block.getType() != Material.WATER) continue;
//						double distanceItemPlayer = item.getLocation().distanceSquared(player.getLocation());
//						if(distanceItemPlayer > radiusSquared) continue;
//																		
//						Vector playerPos = player.getLocation().toVector();
//						Vector itemPos = item.getLocation().toVector();
//						double x = item.getLocation().distanceSquared(player.getLocation());
//						
//						if(x <= 0.8) continue;
//						
//						item.setPickupDelay(2);
//						
//						//if(block.getType() == Material.WATER || block.getType() == Material.LAVA) {
////							Vector itemVelocity = item.getVelocity();
////							if(Math.abs(itemVelocity.getX()) >= waterVelocity || Math.abs(itemVelocity.getY()) >= waterVelocity || Math.abs(itemVelocity.getZ()) >= waterVelocity) continue;
////							relativeDistance = Math.pow(Math.E, -0.1 * x) * 0.25;
//							
//							
//							//resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1).add(item.getVelocity());
//							//resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1);
////						} else {
////							relativeDistance = Math.pow(Math.E, -0.05 * x) * 2;
////							Vector resultant1 = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1)/*.add((item.getVelocity()).multiply(0.2))*/;
//////							//float angle = (float) (resultant1.angle(player.getVelocity()) * 180 / Math.PI);
////							
////							double angle = resultant1.add(item.getVelocity()).dot(item.getVelocity());
////							//player.sendMessage("" + angle);
////							if(angle > 0) {
////								resultant = item.getVelocity()/*.add(resultant.multiply(0.00001))*/;
////								player.sendMessage(angle + " >0");
////								//resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1);
////							} else {
////								resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1);
////							}
////							//resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1);
////							//player.sendMessage("angle: " + angle);
////						}
//							
//						//} else {
//						Vector itemVelocity = item.getVelocity();
//						relativeDistance = Math.pow(Math.E, -0.05 * x) * 2;
//						Vector vectorToPlayer = player.getLocation().toVector().subtract(item.getLocation().toVector()).normalize().multiply(relativeDistance * 0.1);
//						Vector otherVec = player.getLocation().toVector().subtract(item.getLocation().toVector().normalize());
//						double angle = vectorToPlayer.dot(itemVelocity);
//							if(angle < 0) {
//								resultant = item.getVelocity().add(otherVec.multiply(0.001));
//							} else {
//								resultant = vectorToPlayer;
//							}
//							player.sendMessage("" + relativeDistance);
//						
//						
//						item.setVelocity(resultant);
//						
//					}
//				}
//			}
//		}
//	}
//
}
