package com.gmail.ZiomuuSs.EventUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Utils.ConfigAccessor;

public class EventPlayer {
	private static HashSet<UUID> savedPlayers = new HashSet<>(); //players that are saved in files, for performance better not search for player's file every join
	private Main plugin;
	private UUID uuid;
  private Location loc;
  private float exp;
  private double health;
  private double maxHealth;
  private float saturation;
  private float hunger;
  private GameMode gamemode;
  private boolean fly;
  private Collection<PotionEffect> potions = new HashSet<PotionEffect>();
  private ItemStack[] items;
  
  //for online players
  public EventPlayer(Player player, Main plugin, Location dest) {
  	//some bug prevention first
  	player.closeInventory();
  	player.getPassengers().clear();
  	player.getVehicle().getPassengers().clear();
  	//place for removing combat mode from player to prevent bugs
  	//then save player...
  	this.plugin = plugin;
  	uuid = player.getUniqueId();
  	loc = player.getLocation();
  	exp = player.getExp();
  	health = player.getHealth();
  	maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
  	saturation = player.getSaturation();
  	hunger = player.getExhaustion();
  	gamemode = player.getGameMode();
  	fly = player.isFlying();
  	potions = player.getActivePotionEffects();
  	items = player.getInventory().getContents();
  	//then clear him...
  	player.setExp(0);
  	player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
  	player.setHealth(20);
  	player.setSaturation(5);
  	player.setExhaustion(20);
  	player.setGameMode(GameMode.SURVIVAL);
  	player.setFlying(false);
  	player.getActivePotionEffects().clear();
  	player.getInventory().clear();
  	//and finally, teleport him
  	player.teleport(dest);
  	save();
  }
  
  //for loading from files
  public EventPlayer(Main plugin,UUID uuid, Location loc, float exp, double health, double maxHealth, float saturation, float hunger, GameMode gamemode, boolean fly, Collection<PotionEffect> potions, ItemStack[] items) {
  	this.plugin = plugin;
    this.uuid = uuid;
  	this.loc = loc;
  	this.exp = exp;
  	this.health = health;
  	this.maxHealth = maxHealth;
  	this.saturation = saturation;
  	this.hunger = hunger;
  	this.gamemode = gamemode;
  	this.fly = fly;
  	this.potions = potions;
  	this.items = items;
  }
  
  public void returnToLobby() {
  	Player player = Bukkit.getPlayer(uuid);
  	if (player != null && player.isOnline()) {
    	player.setExp(0);
    	player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    	player.setHealth(20);
    	player.setSaturation(5);
    	player.setExhaustion(20);
    	player.setGameMode(GameMode.SURVIVAL);
    	player.setFlying(false);
    	player.getActivePotionEffects().clear();
    	player.getInventory().clear();
    	player.teleport(Lobby.getLocation());
    	Lobby.addPlayer(uuid, this);
  	}
  }
  
  public void restore() {
  	Player player = Bukkit.getPlayer(uuid);
  	if (player != null && player.isOnline()) {
  		player.closeInventory();
  		player.setExp(exp);
  		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
  		player.setHealth(health);
  		player.setSaturation(saturation);
  		player.setExhaustion(hunger);
  		player.setGameMode(gamemode);
  		player.setFlying(fly);
  		player.getActivePotionEffects().clear();
  		player.getActivePotionEffects().addAll(potions);
  		player.getInventory().setContents(items);
  		player.teleport(loc);
  	}
  }
  
  public static boolean isInEvent(UUID uuid) {
  	Event e = EventQueue.getCurrent();
  	if (e == null)
  		return false;
  	return e.getPlayers().contains(uuid);
  }
  
  public static boolean isInLobby(UUID uuid) {
  	return Lobby.getPlayers().contains(uuid);
  }
  
  public static boolean isSaved(UUID uuid) {
  	return savedPlayers.contains(uuid);
  }
  
  public static void loadSaved(Main plugin) {
  	File f = new File((new StringBuilder(plugin.getDataFolder().toString())
  			.append(String.valueOf(File.separatorChar))
  			.append("Players")).toString());
  	if (f.exists()) {
  		for (File saved : f.listFiles()) {
  			String name = saved.getName();
  			savedPlayers.add(UUID.fromString(name.substring(0, name.length()-4)));
  		}
  	}
  }
  
  private void save() {
    new File((new StringBuilder(plugin.getDataFolder().toString())
        .append(String.valueOf(File.separatorChar))
        .append("Players"))
        .append(String.valueOf(File.separatorChar))
        .append(new StringBuilder(uuid.toString()).append(".yml").toString()).toString()).delete();
    ConfigAccessor ca = new ConfigAccessor(plugin, new StringBuilder(uuid.toString()).append(".yml").toString(), "Players");
    ConfigurationSection cs = ca.getConfig();
    cs.set("location.world", loc.getWorld().getName());
    cs.set("location.x", loc.getX());
    cs.set("location.y", loc.getY());
    cs.set("location.z", loc.getZ());
    cs.set("location.yaw", loc.getYaw());
    cs.set("location.pitch", loc.getPitch());
    cs.set("exp", exp);
    cs.set("health", health);
    cs.set("maxhealth", maxHealth);
    cs.set("saturation", saturation);
    cs.set("hunger", hunger);
    cs.set("gamemode", gamemode.toString());
    cs.set("fly", fly);
    for (PotionEffect pe : potions) {
      cs.set(new StringBuilder("potions.").append(pe).append(".type").toString(), pe.getType().toString());
      cs.set(new StringBuilder("potions.").append(pe).append(".duration").toString(), Integer.toString(pe.getDuration()));
      cs.set(new StringBuilder("potions.").append(pe).append(".amplifier").toString(), Integer.toString(pe.getAmplifier()));
    }
    cs.set("inventory", Arrays.asList(items));
  }
  
  @SuppressWarnings("unchecked")
  public static EventPlayer load(UUID uuid, Main plugin) {
  	File f = new File((new StringBuilder(plugin.getDataFolder().toString())
  			.append(String.valueOf(File.separatorChar))
  			.append("Players"))
  			.append(String.valueOf(File.separatorChar))
  			.append(new StringBuilder(uuid.toString()).append(".yml").toString()).toString());
  	if (f.exists()) {
  		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
  		HashSet<PotionEffect> potions = new HashSet<>();
  		for (String potion : fc.getConfigurationSection("potions").getKeys(false))
  		  potions.add(new PotionEffect(PotionEffectType.getByName(fc.getString(new StringBuilder("potions.").append(potion).append(".type").toString())), fc.getInt(new StringBuilder("potions.").append(potion).append(".duration").toString()), fc.getInt(new StringBuilder("potions.").append(potion).append(".amplifier").toString())));
  		HashSet<ItemStack> it = new HashSet<>();
  		for(ItemStack item : (List<ItemStack>) fc.getList("inventory"))
  		  it.add(item);
  		ItemStack[] items = it.toArray(new ItemStack[it.size()]);
  		return new EventPlayer(plugin, uuid,
  				new Location(Bukkit.getWorld(fc.getString("location.world")), fc.getDouble("location.x"), fc.getDouble("location.y"), fc.getDouble("location.z"), (float) fc.getDouble("location.yaw"), (float) fc.getDouble("location.pitch")),
  				(float) fc.getDouble("exp"),
  				fc.getDouble("health"),
  				fc.getDouble("maxhealth"),
  				(float) fc.getDouble("saturaion"),
  				(float) fc.getDouble("hunger"),
  				GameMode.valueOf(fc.getString("gamemode")),
  				fc.getBoolean("fly"),
  				potions,
  				items);
  	}
  	return null;
  }
  
  public UUID getUUID() {
    return uuid;
  } 
  
}
