package com.gmail.ZiomuuSs.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.EventUtils.Lobby;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Events.EventType;
import com.gmail.ZiomuuSs.Utils.msg;

public class EventCoreCommand implements CommandExecutor {
  Main plugin;
  
  public EventCoreCommand (Main instance) {
    plugin = instance;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("EventCore") || cmd.getName().equalsIgnoreCase("ce")) {
			if (!sender.hasPermission("EventCore.admin")) {
				sender.sendMessage(msg.ERROR_PERMISSION.get());
				return true;
			}
    	if (args.length > 0) {
    		if (args[0].equalsIgnoreCase("lobby")) {
    			if (sender instanceof Player) {
    				Lobby.setLobbyLocation(((Player) sender).getLocation(), plugin);
    				sender.sendMessage(msg.LOBBY_LOCATION_SET.get());
    			} else
    				sender.sendMessage(msg.ERROR_MUST_BE_PLAYER.get());
    		} else if (args[0].equalsIgnoreCase("reload")) {
    			Event.loadAll(plugin);
    		} else if (args[0].equalsIgnoreCase("start") ) {
    			if (sender instanceof Player) {
	    			//for starting 1 event
	    			if (args.length > 2) {
	    				if (EventQueue.canInstantStart()) {
	    					if (EventType.isValid(args[1])) {
	    						if (EventQueue.startEvent(EventType.valueOf(args[1].toUpperCase()), args[2], ((Player) sender).getInventory().getItemInMainHand()))
	    							sender.sendMessage(msg.STARTED.get(args[2], args[1]));
	    						else
	    							sender.sendMessage(msg.ERROR_EVENT_NOT_EXIST.get(args[1], args[2]));
	    					} else
	    						sender.sendMessage(msg.ERROR_EVENT_TYPE_NOT_EXIST.get(args[1]));
	    				} else
	    					sender.sendMessage(msg.ERROR_ALREADY_IN_PROGRESS.get());
	    			//for starting earlier defined event queue
	    			} else {
	    				sender.sendMessage("hehe kiedyœ mo¿e...");
	    			}
    			} else
    			  sender.sendMessage(msg.ERROR_MUST_BE_PLAYER.get());
    		}
    	} else
    		sender.sendMessage(msg.ERROR_USAGE.get("/ce (args)"));
    }
    return true;
  }
}