package com.gmail.ZiomuuSs.Events;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Utils.Msg;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class SpleefEvent extends Event {
  
  protected ProtectedRegion surface;
  protected Material surfaceMaterial;
  protected int minY = -1;
  
  public SpleefEvent(String name, Main plugin) {
    super(name, plugin);
    mode = EventMode.SPLEEF;
  }
  
  public EventStatus getStatus() {
    if (status == EventStatus.NOT_READY) {
      if (lobby != null && startPoints != null && surface != null && minY >= 0)
        status = EventStatus.READY;
    }
    return status;
  }
  
  @Override
  public void showRequiments(CommandSender sender) {
    sender.sendMessage(Msg.get("show_requiments", false, name));
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
    
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    if (e.getPlayer().getLocation().getY() < minY) {
      super.kickPlayer(e.getPlayer().getUniqueId());
    }
  }
}
