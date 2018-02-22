package com.gmail.ZiomuuSs.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.gmail.ZiomuuSs.EventPlayer;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Reward;
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
  protected HashMap<UUID, EventPlayer> players = new HashMap<>(); //Players in event
  protected Location lobby; //coordinates of lobby
  protected ArrayList<Location> startPoints = new ArrayList<>(); //coordinates of spawn points when event begin
  protected Inventory startInventory; //Inventory for every player when event begins
  protected int maxPlayers; //max players in event
  protected Reward reward; //reward for winner(s)
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
  
  public void kickPlayer (UUID uuid) {
    players.get(uuid).quit();
    players.remove(uuid);
  }
  
  public void showRequiments(CommandSender sender) {}
  
  // Getters & Setters
  public HashMap<UUID, EventPlayer> getPlayers() {
    return players;
  }
  
  //returns false if an start point was added, or returns true if an start point was edited
  public boolean setStartPoints(Location loc, int index) {
    if (index > 0) {
      --index; //player counts from 1, Java from 0
      if (startPoints.size()>= index+1) {
        startPoints.set(index, loc);
        return true;
      } else {
        startPoints.add(loc);
        return false;
      }
    } else {
      startPoints.add(loc);
      return false;
    }
  }
  
  public void start() {}
  public void setSurface(ProtectedRegion rg, World world) {}
  public void setSurfaceMaterial(Material m) {}
  public void setStartInventory(Inventory inv) {
    startInventory = inv;
  }
  public boolean isRequired (REQUIMENT r) {
    return false;
  }
  public Reward getReward() {
    return reward;
  }
  public void setMinY(int miny) {}
  public void setMaxPlayers(int mp) {
    maxPlayers = mp;
  }
  public void setMinPlayers(int mp) {
    minPlayers = mp;
  }
  public int getMinPlayes() {
    return minPlayers;
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
  public ArrayList<Location> getStartPoints() {
    return startPoints;
  }
  public Location getLobby() {
    return lobby;
  }
  public void setLobby(Location l) {
    lobby = l;
  }
  public boolean isInEvent(UUID uuid) {
    if (players.containsKey(uuid))
      return true;
    else
      return false;
  }
  public boolean isStartingWhenMax() {
    return startWhenMax;
  }
}
