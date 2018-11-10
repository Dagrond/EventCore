package com.gmail.ZiomuuSs.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.gmail.ZiomuuSs.EventUtils.Lobby;

public class onDropListener implements Listener {
	
	//cancel item drop if player is in lobby.
	@EventHandler
  public void onDamage(PlayerDropItemEvent e) {
    if (Lobby.isInLobby(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
    }
  }
}
