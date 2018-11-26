package com.gmail.ZiomuuSs.Listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventPlayer;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.Events.Event;

import fr.xephi.authme.events.LoginEvent;

public class AuthMeListener implements Listener{
	Main plugin;
	
	public AuthMeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
  public void onLogin(LoginEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (EventPlayer.isSaved(uuid)) {
			EventPlayer.load(uuid, plugin).restore();
		}
		Event ev = EventQueue.getStarting();
		if (ev != null)
			ev.addToBossBar(e.getPlayer());
	}
	
}
