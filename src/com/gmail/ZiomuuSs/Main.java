package com.gmail.ZiomuuSs;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ZiomuuSs.Commands.EventCommand;
import com.gmail.ZiomuuSs.Commands.EventCoreCommand;
import com.gmail.ZiomuuSs.Utils.msg;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.dadus33.chatitem.api.ChatItemAPI;

public final class Main extends JavaPlugin {
	private ChatItemAPI ciApi;
	private Logger log = Bukkit.getLogger();
	private WorldGuardPlugin worldGuard;
	
  public void onEnable() {
    //ciApi = Bukkit.getServicesManager().getRegistration(ChatItemAPI.class).getProvider();
    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
    		plugin = null;
        log.info(msg.ERROR_LOAD_PLUGIN_NOT_FOUND.get("WorldGuard"));
    } else
    	worldGuard = (WorldGuardPlugin) plugin;
    getCommand("EventCore").setExecutor(new EventCoreCommand(this));
    getCommand("Event").setExecutor(new EventCommand(this));
    //Event.loadAll(this);
  }
  
  public void onDisable() {
  }
  
  public WorldGuardPlugin getWorldGuard() {
  	return worldGuard;
  }
  
  public ChatItemAPI getCI() {
  	return ciApi;
  }
}
