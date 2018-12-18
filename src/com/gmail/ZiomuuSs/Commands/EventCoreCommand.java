package com.gmail.ZiomuuSs.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.EventUtils.Lobby;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Events.EventType;
import com.gmail.ZiomuuSs.Utils.ItemsManager;
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
    		} else if (args[0].equalsIgnoreCase("item")) {
    			if (args.length > 1) {
    				if (args[1].equalsIgnoreCase("list")) {
    					sender.sendMessage(msg.ITEM_LIST.get(ItemsManager.getFormattedItemList()));
    				} else if (args.length > 2) {
    					if (args[1].equalsIgnoreCase("save")) {
    						if (!ItemsManager.itemExist(args[2])) {
    							if (sender instanceof Player) {
    								Player player = (Player) sender;
    								if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
	    								ItemsManager.saveItem(args[2], player.getInventory().getItemInMainHand());
	    								sender.sendMessage(msg.ITEM_SAVED.get(args[2]));
    								} else
    									sender.sendMessage(msg.ERROR_ITEM_IN_MAIN_HAND_AIR.get());
    							} else 
    								sender.sendMessage(msg.ERROR_MUST_BE_PLAYER.get());
    						} else
    							sender.sendMessage(msg.ITEM_ALREADY_EXIST.get(args[2]));
    					} else if (args[1].equalsIgnoreCase("load")) {
    						if (sender instanceof Player) {
  								Player player = (Player) sender;
  								if (player.getInventory().firstEmpty() > -1) {
    								player.getInventory().addItem(ItemsManager.getItem(args[2]));
    								sender.sendMessage(msg.ITEM_LOADED.get(args[2]));
  								} else
  									sender.sendMessage(msg.ERROR_NOT_ENOUGH_SPACE_IN_INVENTORY.get());
  							} else 
  								sender.sendMessage(msg.ERROR_MUST_BE_PLAYER.get());
    					} else if (args[1].equalsIgnoreCase("remove")) {
    						if (ItemsManager.itemExist(args[2])) {
    							ItemsManager.removeItem(args[2]);
    							sender.sendMessage(msg.ITEM_REMOVED.get(args[2]));
    						} else
    							sender.sendMessage(msg.ITEM_NOT_EXIST.get(args[2]));
    					} else
    						sender.sendMessage(msg.ERROR_USAGE.get("/ce item save/load/list/remove (args...)"));
    				} else
    					sender.sendMessage(msg.ERROR_USAGE.get("/ce item save/load/list/remove (args...)"));
    			} else
    				sender.sendMessage(msg.ERROR_USAGE.get("/ce item save/load/list/remove (args...)"));
    		}
    	} else
    		sender.sendMessage(msg.ERROR_USAGE.get("/ce (args)"));
    }
    return true;
  }
}