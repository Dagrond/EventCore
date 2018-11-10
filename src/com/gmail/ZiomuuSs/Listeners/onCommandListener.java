package com.gmail.ZiomuuSs.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.ZiomuuSs.EventUtils.Lobby;
import com.gmail.ZiomuuSs.Utils.msg;

public class onCommandListener implements Listener {
	
	//cancel commands different than /e or /event when player is in lobby
	@EventHandler
  public void onDamage(PlayerCommandPreprocessEvent e) {
    if (Lobby.isInLobby(e.getPlayer().getUniqueId()) && !(e.getMessage().toLowerCase().startsWith("/e ") && e.getMessage().toLowerCase().startsWith("/event "))) {
      e.setCancelled(true);
      e.getPlayer().sendMessage(msg.ERROR_LOBBY_COMMAND.get());
    }
  }
}
