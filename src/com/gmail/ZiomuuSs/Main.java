package com.gmail.ZiomuuSs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ZiomuuSs.Utils.Data;
import com.gmail.ZiomuuSs.Utils.Msg;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public final class Main extends JavaPlugin {
  protected Economy economy;
  protected Data data;
  WorldGuardPlugin worldGuard;
  WorldEditPlugin worldEdit;
  public void onEnable() {
    data = new Data(this);
    getCommand("EventCore").setExecutor(new EventCoreCommand(this, data));
    saveDefaultConfig();
    String hooks = "";
    if(Bukkit.getPluginManager().getPlugin("Vault") instanceof Vault) {
      hooks += "Vault, ";
      RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
      if (economyProvider != null) {
          economy = economyProvider.getProvider();
          Bukkit.getLogger().info(Msg.get("using_economy", true, "Vault"));
      }
    }
    Plugin plugin;
    plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    if (plugin instanceof WorldEditPlugin) {
      hooks += "WorldEdit, ";
      worldEdit = (WorldEditPlugin) plugin;
    }
    plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    if (plugin instanceof WorldGuardPlugin) {
      hooks += "WorldGuard";
      worldGuard = (WorldGuardPlugin) plugin;
    }
    Bukkit.getLogger().info(Msg.get("console_hook", true, hooks));
    
  }
  
  public void onDisable() {
    saveConfig();
  }
  
  public Data getData() {
    return data;
  }
  
  public Economy getEconomy() {
    return economy;
  }
}
