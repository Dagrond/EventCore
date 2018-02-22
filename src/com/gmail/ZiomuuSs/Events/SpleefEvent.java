package com.gmail.ZiomuuSs.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.ZiomuuSs.EventPlayer;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Utils.Msg;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class SpleefEvent extends Event {
  
  protected ProtectedRegion surface;
  protected Material surfaceMaterial;
  protected World surfaceWorld;
  protected int minY = -1;
  
  public SpleefEvent(String name, Main plugin) {
    super(name, plugin);
    mode = EventMode.SPLEEF;
  }
  
  public EventStatus getStatus() {
    if (status == EventStatus.NOT_READY) {
      if (lobby != null && startPoints != null && surface != null && minY >= 0 && surfaceMaterial != null)
        status = EventStatus.READY;
    }
    return status;
  }
  
  public void setSurfaceMaterial(Material m) {
    surfaceMaterial = m;
  }
  public void setSurface(ProtectedRegion rg, World world) {
    surface = rg;
    surfaceWorld = world;
  }
  
  @Override
  public void start() {
    //start event code...
    plugin.getData().setCurrentEvent(this);
    status = EventStatus.IN_LOBBY;
    Bukkit.broadcastMessage(Msg.get("event_start_broadcast", true, name, Integer.toString(delay)));
    //3
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        if (status == EventStatus.IN_LOBBY)
          Bukkit.broadcastMessage(Msg.get("event_start_broadcast", true, name, "3"));
      }
    }, 20*delay-3);
    //2
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        if (status == EventStatus.IN_LOBBY)
          Bukkit.broadcastMessage(Msg.get("event_start_broadcast", true, name, "2"));
      }
    }, 20*delay-2);
    //1
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        if (status == EventStatus.IN_LOBBY)
          Bukkit.broadcastMessage(Msg.get("event_start_broadcast", true, name, "1"));
      }
    }, 20*delay-1);
    //start
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        if (status == EventStatus.IN_LOBBY) {
          if (players.size()>=minPlayers) {
            Bukkit.broadcastMessage(Msg.get("event_started_broadcast", true, name));
            status = EventStatus.IN_PROGRESS;
            startArena();
          } else {
            Bukkit.broadcastMessage(Msg.get("error_not_enough_players", true, name));
            status = EventStatus.READY;
          }
        }
      }
    }, 20*delay);
}

  public void startArena() {
    plugin.getData().giveEventInventory(startInventory);
    plugin.getData().teleportToStartLocation(startPoints);
  }
  
  public void setSurface() throws MaxChangedBlocksException {
    plugin.getData().getWorldEdit().getWorldEdit().getEditSessionFactory().getEditSession((com.sk89q.worldedit.world.World) surfaceWorld, -1).setBlocks((Region) surface, new BaseBlock(BlockID.SNOW_BLOCK));
  }
  
  public void setMinY(int miny) {
    this.minY = miny;
  }
  
  @Override
  public void showRequiments(CommandSender sender) {
    sender.sendMessage(Msg.get("show_requiments", false, name));
    sender.sendMessage(Msg.get("type", true, "SPLEEF"));
    if (lobby != null)
      sender.sendMessage(Msg.get("lobby", false, Msg.get("check", false), Msg.get("coordinates", false, Integer.toString(lobby.getBlockX()), Integer.toString(lobby.getBlockY()), Integer.toString(lobby.getBlockZ()), lobby.getWorld().toString())));
    else
      sender.sendMessage(Msg.get("lobby", false, Msg.get("error_check", false)));
    if (startPoints != null)
      sender.sendMessage(Msg.get("start_points", false, Msg.get("check", false), Integer.toString(startPoints.size())));
    else
      sender.sendMessage(Msg.get("start_points", false, Msg.get("error_check", false), Msg.get("none", false)));
    if (surface != null && surfaceMaterial != null) {
      sender.sendMessage(Msg.get("surface", false, Msg.get("check", false), surfaceMaterial.toString()));
    } else if (surface != null && surfaceMaterial == null) {
      sender.sendMessage(Msg.get("surface", false, Msg.get("check", false), Msg.get("error_check", false)));
    } else if (surface == null && surfaceMaterial != null) {
      sender.sendMessage(Msg.get("surface", false, Msg.get("error_check", false), surfaceMaterial.toString()));
    } else {
      sender.sendMessage(Msg.get("surface", false, Msg.get("error_check", false), Msg.get("error_check", false)));
    }
    if (minY >= 0)
      sender.sendMessage(Msg.get("miny", false, Integer.toString(minY)));
    else
      sender.sendMessage(Msg.get("miny", false, Msg.get("error_check", false)));
    sender.sendMessage(Msg.get("status", true, getStatus().toString()));
    
  }
  
  @Override
  public boolean isRequired (REQUIMENT r) {
    if (r == REQUIMENT.LOBBY || r == REQUIMENT.MINY || r == REQUIMENT.STARTPOINTS || r == REQUIMENT.SURFACE || r == REQUIMENT.MINPLAYERS || r == REQUIMENT.MAXPLAYERS || r == REQUIMENT.INVENTORY)
      return true;
    return false;
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    if (e.getPlayer().getLocation().getY() < minY && players.containsKey(e.getPlayer().getUniqueId())) {
      super.kickPlayer(e.getPlayer().getUniqueId());
      if (players.keySet().size() == 1) {
        for (EventPlayer pl : players.values()) {
          pl.won(name);
        }
      }
    }
  }
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    if (players.containsKey(e.getPlayer().getUniqueId())) {
      players.get(e.getPlayer().getUniqueId()).quit();
      if (players.keySet().size() == 1) {
        for (EventPlayer pl : players.values()) {
          pl.won(name);
        }
      }
    }
  }
}
