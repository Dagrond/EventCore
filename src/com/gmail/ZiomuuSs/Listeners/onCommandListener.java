package com.gmail.ZiomuuSs.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.ZiomuuSs.EventUtils.Lobby;
import com.gmail.ZiomuuSs.Utils.msg;

public class onCommandListener implements Listener {
	
	//cancel commands different than /e or /event when player is in lobby
	@EventHandler
  public void onCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
    if (Lobby.isInLobby(player.getUniqueId()) && !(message.toLowerCase().startsWith("/e ") && !message.toLowerCase().startsWith("/event ")) && !player.isOp()) {
      e.setCancelled(true);
      player.sendMessage(msg.ERROR_LOBBY_COMMAND.get());
    }
  }
}
