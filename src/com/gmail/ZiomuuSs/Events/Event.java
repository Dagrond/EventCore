package com.gmail.ZiomuuSs.Events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.gmail.ZiomuuSs.EventPlayer;
import com.gmail.ZiomuuSs.Main;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Event implements Listener {
  public static enum EventStatus {
    NOT_READY, READY, IN_PROGRESS, IN_LOBBY;
  }
  public static enum EventMode {
    SPLEEF;
    public static boolean contains(String s) {
      for (EventMode c : EventMode.values()) {
          if (c.name().equals(s.toUpperCase())) {
              return true;
          }
      }
      return false;
  }
    public static String getString() {
      String x = "";
      for (EventMode e : EventMode.values()) {
        x += e.toString()+", ";
      }
      return x.substring(0, x.length() - 2);
    }
  }
  public static enum REQUIMENT {
    LOBBY, STARTPOINTS, SURFACE, MINY, MINPLAYERS, MAXPLAYERS, INVENTORY;
  }
  protected Main plugin;
  EventMode mode;
  protected String name; //name of event
  protected boolean startWhenMax; //Should event start (ignoring delay) when amount of players in lobby hit max?
  protected HashMap<UUID, EventPlayer> Players = new HashMap<>(); //Players in event
  protected Location lobby; //coordinates of lobby
  protected HashSet<Location> startPoints = new HashSet<>(); //coordinates of spawn points when event begin
  protected Inventory startInventory; //Inventory for every player when event begins
  protected int maxPlayers; //max players in event
  protected int minPlayers; //min amount of players to start event
  protected EventStatus status; //Status of event (lobby(players are in lobby, event will start soon)/during(event is in progress)/off(event disabled to use or not ready to use)/free(there is not event))
  protected int delay; //amount of time in seconds after event will start (change it's status from "lobby" to "during"). Can be bypassed by command or if StartWhenMax=true
  
  public Event (String name, Main plugin) {
    this.name = name;
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    status = EventStatus.NOT_READY;
    startWhenMax = true;
    maxPlayers = 0;
    minPlayers = 2;
    delay = 60;
  }
  
  public void start() {}
  
  public void kickPlayer (UUID uuid) {
    Players.get(uuid).quit();
    Players.remove(uuid);
  }
  
  public void addPlayer (Player player) {
    
  }
  
  public void showRequiments(CommandSender sender) {}
  
  // Getters & Setters
  public void setSurface(ProtectedRegion rg) {}
  public void setSurfaceMaterial(Material m) {}
  public void setStartInventory(Inventory inv) {
    startInventory = inv;
  }
  public boolean isRequired (REQUIMENT r) {
    return false;
  }
  public void setMaxPlayers(int mp) {
    maxPlayers = mp;
  }
  public void setMinPlayers(int mp) {
    minPlayers = mp;
  }
  @Override
  public String toString() {
    return name;
  }
  public EventMode getMode() {
    return mode;
  }
  public EventStatus getStatus() {
    return status;
  }
  public int getMaxPlayers() {
    return maxPlayers;
  }
  public int getMinPlayers() {
    return minPlayers;
  }
  public int getDelay() {
    return delay;
  }
  public Inventory getStartingInventory() {
    return startInventory;
  }
  public HashSet<Location> getStartPoints() {
    return startPoints;
  }
  public Location getLobby() {
    return lobby;
  }
  public void setLobby(Location l) {
    lobby = l;
  }
  public boolean isInEvent(UUID uuid) {
    if (Players.containsKey(uuid))
      return true;
    else
      return false;
  }
  public boolean isStartingWhenMax() {
    return startWhenMax;
  }
}
