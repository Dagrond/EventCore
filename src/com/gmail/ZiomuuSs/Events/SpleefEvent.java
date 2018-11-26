package com.gmail.ZiomuuSs.Events;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.Utils.CountdownTimer;
import com.gmail.ZiomuuSs.Utils.msg;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class SpleefEvent extends Event {
	private static HashMap<String, SpleefEvent> events = new HashMap<>();
	private String name = "none"; //display name of event 
	private ArrayList<ProtectedRegion> regions = new ArrayList<>(); //regions to change blocks in.
	private int countdown = 5; //seconds after teleport when destroying blocks in above regions will be avaible
	private World world; //world of that event (needed for regions and WorldEdit/FAWE
	private Material floorMaterial; //material of spleef's floor
	private EditSession session;
	static {
		type = EventType.SPLEEF;
	}
	
	public SpleefEvent(Main plugin, String name, World world, ArrayList<ProtectedRegion> regions, Material floorMaterial) {
		super(plugin, name);
		this.name = name;
		this.regions = regions;
		this.world = world;
		this.floorMaterial = floorMaterial;
		session = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
	}
	
	@SuppressWarnings("static-access")
	public void startSequence() {
		super.startSequence();
		//plugin.getServer().getPluginManager().registerEvents(this, plugin);
		CountdownTimer xd = new CountdownTimer(plugin, countdown,
        () -> {
          EventQueue.setStarting(this);
        },
         () -> {
        	 BlockBreakEvent.getHandlerList().unregisterAll(this);
        	 super.broadcast(msg.EVENT_SPLEEF_START.get());
          },
        (t) -> {
          for (Player player : players.keySet()) {
          	player.sendTitle(msg.EVENT_SPLEEF_GET_READY.get(), Integer.toString(t.getSecondsLeft()), 1, 18, 1);
          }
        });
    xd.scheduleTimer();
    
	}
	
	public void setCountDown(int countdown) {
		this.countdown = countdown;
	}
	
	public static int load(Main plugin) {
		int count = 0;
		File f = new File(plugin.getDataFolder() + String.valueOf(File.separatorChar) + "Events" + String.valueOf(File.separatorChar) + "Spleef");
		if (!f.exists())
			return 0;
		for (File file : f.listFiles()) {
			Logger log = Bukkit.getLogger();
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
			String name = file.getName();
			name = name.substring(0, name.length() - 4); //remove .yml
			//loading required variables
			//loading world
			World world;
			if (fc.isString("world")) {
				world = Bukkit.getWorld(fc.getString("world"));
			} else {
				log.info(msg.ERROR_LOAD_NOT_CONFIGURED.get(name, type.toString(), "world"));
				continue;
			}
			if (world == null) {
				log.info(msg.ERROR_LOAD_WORLD_NOT_EXIST.get(name, type.toString(), fc.getString("world")));
				continue;
			}
			//loading region list
			ArrayList<ProtectedRegion> regions = new ArrayList<>();
			if (fc.isList("regions")) {
				int loadedRegions = 0;
				RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
				for (String region : fc.getStringList("regions")) {
					if (manager.hasRegion(region)) {
						regions.add(manager.getRegion(region));
						++loadedRegions;
					} else {
						log.info(msg.ERROR_LOAD_REGION_NOT_EXIST.get(region, world.getName(), name, type.toString()));
					}
				}
				if (loadedRegions < 1) {
					log.info(msg.ERROR_LOAD_NO_REGIONS.get(name, type.toString()));
					continue;
				}
			} else {
				log.info(msg.ERROR_LOAD_NOT_CONFIGURED.get(name, type.toString(), "regions"));
				continue;
			}
			//loading material
			Material floor;
			if (fc.isString("floor")) {
				floor = Material.valueOf(fc.getString("floor").toUpperCase());
			} else {
				log.info(msg.ERROR_LOAD_NOT_CONFIGURED.get(name, type.toString(), "floor"));
				continue;
			}
			if (floor == null) {
				log.info(msg.ERROR_LOAD_MATERIAL_NOT_EXIST.get(name, type.toString(), fc.getString("floor").toUpperCase()));
				continue;
			}
			if (!floor.isSolid()) {
				log.info(msg.ERROR_LOAD_MATERIAL_NOT_SOLID.get(name, type.toString(), fc.getString("floor").toUpperCase()));
				continue;
			}
			//creating SpleefEvent
			SpleefEvent event = new SpleefEvent(plugin, name, world, regions, floor);
			//loading optional variables
			//loading countdown
			if (fc.isInt("countdown")) {
				event.setCountDown(fc.getInt("countdown"));
			}
			//loading basic variables
			if(event.loadBasicVariables(plugin, fc, name, type.toString(), log, world)) {
			//if everything is fine, add event to avaible events.
			  events.put(name, event);
			  ++count;
			}
		}
		return count;
	}
	
	
	//this event is listening only when event is starting.
	@EventHandler
  public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (players.containsKey(player)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
  public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (players.containsKey(player)) {
				if (e.getCause() == DamageCause.VOID) {
					super.kick(player);
					super.checkWinCondition();
				} else
					e.setCancelled(true);
			}
		}
	}
	
	public static boolean exist(String name) {
		return events.containsKey(name);
	}
	
	public static SpleefEvent get(String name) {
		return events.get(name);
	}
}
