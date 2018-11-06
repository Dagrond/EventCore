package com.gmail.ZiomuuSs.EventUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Utils.ConfigAccessor;
import com.gmail.ZiomuuSs.Utils.TimeUtilities;
import com.gmail.ZiomuuSs.Utils.msg;

public class Lobby {
	private static HashMap<UUID, EventPlayer> players = new HashMap<>(); //players in lobby
	private static Location loc; //location of lobby
	
	public static void broadcast(String message) {
		for (UUID uuid : players.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null)
				player.sendMessage(message);
		}
	}
	
	public static int size() {
		return players.size();
	}
	
	public static void load(Main plugin) {
		File f = new File(plugin.getDataFolder() + String.valueOf(File.separatorChar) + "lobby.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (fc.isString("location.world") && fc.isDouble("location.x") && fc.isDouble("location.y") && fc.isDouble("location.z") && fc.isDouble("location.yaw") && fc.isDouble("location.pitch")) {
			World world = Bukkit.getWorld(fc.getString("location.world"));
			if (world != null)
				loc = new Location(world, fc.getDouble("location.x"), fc.getDouble("location.y"), fc.getDouble("location.z"), (float) fc.getDouble("location.yaw"), (float) fc.getDouble("location.pitch"));
		}
	}
	
	public static void setLobbyLocation(Location location, Main plugin) {
		loc = location;
	  ConfigAccessor ca = new ConfigAccessor(plugin, "lobby.yml");
	  ConfigurationSection cs = ca.getConfig();
	  cs.set("location.world", loc.getWorld().getName());
	  cs.set("location.x", loc.getX());
	  cs.set("location.y", loc.getY());
	  cs.set("location.z", loc.getZ());
	  cs.set("location.yaw", (double) loc.getYaw());
	  cs.set("location.pitch", (double) loc.getPitch());
	  ca.saveConfig();
	}
	
	public static void addPlayer(UUID uuid, EventPlayer ep) {
		players.put(uuid, ep);
	}
	
	public static void delPlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	public static Location getLocation() {
		return loc;
	}
	
	public static String[] getMotd() {
	  String[] message = new String[5];
	  message[0] = msg.LOBBY_MESSAGE_HEADER.get();
	  if (EventQueue.getCurrent() != null) {
	    Event e = EventQueue.getCurrent();
	    message[1] = msg.LOBBY_MESSAGE_CURRENT.get(e.getName(), e.getType().toString());
	    message[2] = msg.LOBBY_MESSAGE_PLAYERS_IN_LOBBY.get(Integer.toString(players.size()));
	    message[3] = msg.LOBBY_MESSAGE_TIME.get(TimeUtilities.formatTime(e.getStarted()));
	    message[4] = msg.LOBBY_MESSAGE_SPECTATE_INVITE.get();
	  } else if (EventQueue.getStarting() != null) {
	  } else {
	    
	  }
	  return message;
	}
	 
	public static Collection<UUID> getPlayers() {
		return players.keySet();
	}
	
	public static boolean isInLobby(UUID uuid) {
		return players.containsKey(uuid);
	}
	
}