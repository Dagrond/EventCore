package com.gmail.ZiomuuSs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.ZiomuuSs.Utils.Msg.msg;

public class EventCoreCommand implements CommandExecutor {
  Main plugin;
  
  public EventCoreCommand (Main instance) {
    plugin = instance;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("EventCore") || cmd.getName().equalsIgnoreCase("ce")) {
    	//todo
    	sender.sendMessage(msg.DAS);
    }
    return true;
  }
}