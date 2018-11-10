package com.gmail.ZiomuuSs.Listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.ZiomuuSs.EventUtils.Lobby;

public class onLeaveListener implements Listener {
	
	//kicks player from lobby when he leaves
	@EventHandler
  public void onDamage(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
    if (Lobby.isInLobby(uuid)) {
      Lobby.getPlayer(uuid).restore();
      Lobby.delPlayer(uuid);
    }
  }
}
