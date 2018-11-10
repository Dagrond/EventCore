package com.gmail.ZiomuuSs.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.gmail.ZiomuuSs.EventUtils.Lobby;

public class onDamageListener implements Listener {
	
	//cancel damage if player is in lobby.
	@EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player && Lobby.isInLobby(e.getEntity().getUniqueId())) {
      e.setCancelled(true);
    }
  }
}
