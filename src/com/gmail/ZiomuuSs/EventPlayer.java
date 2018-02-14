package com.gmail.ZiomuuSs;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class EventPlayer {
  protected UUID uuid; //player's uuid
  protected Inventory inv; //player's inventory
  protected Location loc; //player's location
  protected int lvl; //player's level
  
  public EventPlayer(UUID uuid, Inventory inv, Location loc, int lvl, Location lobby) {
    this.uuid = uuid;
    this.inv = inv;
    this.loc = loc;
    this.lvl = lvl;
    Player player = Bukkit.getPlayer(uuid);
    if (player != null) {
      player.getInventory().clear();
      player.setLevel(0);
      player.setTotalExperience(0);
      player.teleport(lobby);
    }
  }

  public boolean startEvent(Inventory i, Location l) {
    Player player = Bukkit.getPlayer(uuid);
    if (player != null) {
      player.getInventory().clear();
      player.getInventory().setContents(i.getContents());
      player.teleport(l);
      return true;
    } else {
      return false;
    }
  }
  
  public boolean quit() {
    Player player = Bukkit.getPlayer(uuid);
    if (player != null) {
      player.getInventory().clear();
      player.getInventory().setContents(inv.getContents());
      player.setLevel(lvl);
      player.teleport(loc);
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public String toString() {
    return uuid.toString();
    //ironic...
  }
}
