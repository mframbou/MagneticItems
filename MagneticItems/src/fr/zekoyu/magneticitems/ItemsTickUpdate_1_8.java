package fr.zekoyu.magneticitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;


public class ItemsTickUpdate_1_8 extends ItemsTickUpdate {

	private MagneticItems main;
	private ArrayList<Item> itemListClone;
	
	private double radius;
	private double radiusSquared;
	
	private double strengthMultiplicator;
	private boolean reverse;
	
	private double accelerationCurve;
	private double minSpeed;
	
	private double defaultMultiplier;
	private double waterMultiplier;
	private double iceMultiplier;
	
	private double multiplier;
	
	public void initValues() {
		FileConfiguration config = main.getConfig();
		
		this.strengthMultiplicator = config.getDouble("magnetism.strength");
		this.reverse = config.getBoolean("magnetism.reverse");
		
		this.radius = config.getDouble("magnetism.radius");
		this.radiusSquared  = Math.pow(radius, 2);
		this.accelerationCurve = config.getDouble("magnetism.acceleration") / 100;
		this.minSpeed = config.getDouble("magnetism.minimum_speed") / 10;
		
		this.waterMultiplier = config.getDouble("smoothness.water") / 10;
		this.iceMultiplier = config.getDouble("smoothness.ice") / 10;
		this.defaultMultiplier = config.getDouble("smoothness.other") / 10;
		 
	}
	
	public ItemsTickUpdate_1_8(MagneticItems main) {
		this.main = main;
		this.runTaskTimer(main, 0, 1);
		this.itemListClone = new ArrayList<>(main.getItemList());
		
		initValues();
	}
		
	@Override
	public void run() {
		this.itemListClone.clear();
		this.itemListClone.addAll(main.getItemList());
		for(Item item : this.itemListClone) {
			if(!item.isValid()) this.main.removeItemFromList(item);
			
			List<Entity> nearbyEntities = item.getNearbyEntities(this.radius, this.radius, this.radius);
			
			if(nearbyEntities != null) {
				nearbyentitiesLoop: 
				for (Entity entity : nearbyEntities) {
					if(entity instanceof Player && !entity.isDead()) {
						
						Player player = (Player) entity;
						
						if(!player.hasPermission("magneticitems.use")) continue;
						
						double relativeDistance;						
						Vector resultant;
						
						Location itemLoc = item.getLocation();
						Location playerLoc = player.getLocation();
						
						Block block = itemLoc.getBlock();
						
						if(!item.isOnGround() && block.getType() != Material.WATER && block.getType() != Material.LAVA) continue;
						
						double distanceItemPlayer = itemLoc.distanceSquared(playerLoc);
						if(distanceItemPlayer > this.radiusSquared) continue;															
						
						Vector playerPos = playerLoc.toVector();
						Vector itemPos = itemLoc.toVector();
						
						double itemDistanceFromPlayer = itemLoc.distanceSquared(playerLoc);
						
						if(itemDistanceFromPlayer <= 0.7) continue;
						
						int itemPlayerDist = (int) itemLoc.distance(playerLoc);
						
						if(itemPlayerDist != 0) {
							BlockIterator iterator = new BlockIterator(player.getWorld(), itemPos, playerPos.clone().subtract(itemPos.clone()).normalize(), 0.2, itemPlayerDist);
							while(iterator.hasNext()) {
								Block bloc = iterator.next();
								if(bloc.getType() != Material.AIR && !isPassableBlock(bloc)) {
									continue nearbyentitiesLoop;
								}
							}
						}
						
						if(this.reverse == false) {
							relativeDistance = Math.pow(Math.E, -this.accelerationCurve * itemDistanceFromPlayer) * 2 + this.minSpeed;
						} else {
							relativeDistance = Math.pow(Math.E, -this.accelerationCurve * itemDistanceFromPlayer) * -2 - this.minSpeed;
						}
						
						
						resultant = playerPos.subtract(itemPos).normalize().multiply(relativeDistance * 0.1);

						float angle =  (float) resultant.clone().dot(item.getVelocity());
						
						if(block.getType() == Material.WATER) {
							this.multiplier = this.waterMultiplier;
						}
						
						switch(block.getRelative(BlockFace.DOWN).getType()) {
							case WATER:
								break;
							case ICE:
							case PACKED_ICE:
								this.multiplier = this.iceMultiplier;
								break;
							default:
								this.multiplier = this.defaultMultiplier;
								break;
						
						}

						if(angle < 0) {
							item.setVelocity(item.getVelocity().add(resultant.clone().multiply(this.multiplier).multiply(this.strengthMultiplicator)));
						} else {
							item.setVelocity(resultant.clone().multiply(this.strengthMultiplicator));
						}
					}
				}
			}
		}
	}
	
	private boolean isPassableBlock(Block bloc) {
		Material blocType = bloc.getType();
		if(!blocType.isSolid()) {
			return true;
		} else {
			if(blocType == Material.valueOf("STANDING_BANNER")) {
				return true;
			} else {	
				return false;
			}
		}
	}
	
	
}
