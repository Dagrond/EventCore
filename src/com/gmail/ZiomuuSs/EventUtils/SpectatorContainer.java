package com.gmail.ZiomuuSs.EventUtils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.ZiomuuSs.Main;

public class SpectatorContainer implements Listener {
	private HashMap<UUID, EventPlayer> players = new HashMap<>(); //players in spectator mode
	
	public SpectatorContainer(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void add(UUID uuid, EventPlayer ep) {
		players.put(uuid, ep);
	}
	
	public void del(UUID uuid) {
		players.remove(uuid);
	}
	
	//Listeners
	@EventHandler
  public void onTeleport(PlayerTeleportEvent e) {
		if (players.containsKey(e.getPlayer().getUniqueId())) {
			if (e.getCause() == TeleportCause.SPECTATE) {
				if (!EventQueue.getCurrent().getPlayers().contains(e.getPlayer().getSpectatorTarget())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
}
