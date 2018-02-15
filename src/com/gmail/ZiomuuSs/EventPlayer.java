package com.gmail.ZiomuuSs;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.ZiomuuSs.Utils.Data;
import com.gmail.ZiomuuSs.Utils.Msg;


public class EventPlayer {
  protected UUID uuid; //player's uuid
  protected Inventory inv; //player's inventory
  protected Location loc; //player's location
  protected int lvl; //player's level
  protected Data data;
  
  public EventPlayer(UUID uuid, Inventory inv, Location loc, int lvl, Location lobby, Data data) {
    this.uuid = uuid;
    this.inv = inv;
    this.loc = loc;
    this.lvl = lvl;
    this.data = data;
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
      player.sendMessage(Msg.get("error_event_lose", true));
      data.broadcastToEventPlayers(Msg.get("", true, player.getDisplayName(), Integer.toString(data.getPlayerAmount())));
      return true;
    } else {
      return false;
    }
  }
  
  public boolean won(String name) {
    Player player = Bukkit.getPlayer(uuid);
    if (player != null) {
      player.getInventory().clear();
      player.getInventory().setContents(inv.getContents());
      player.setLevel(lvl);
      player.teleport(loc);
      player.sendMessage(Msg.get("event_won", true));
      Bukkit.broadcastMessage(Msg.get("event_end", true, player.getDisplayName(), name));
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
