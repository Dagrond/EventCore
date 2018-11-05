package com.gmail.ZiomuuSs.Commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.combatlogx.utility.CombatUtil;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventPlayer;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Utils.msg;

public class EventCommand implements CommandExecutor {
  Main plugin;
  
  public EventCommand (Main instance) {
    plugin = instance;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("Event") || cmd.getName().equalsIgnoreCase("e")) {
    	//todo
    	if (sender instanceof Player) {
    		Player player = (Player) sender;
    		UUID uuid = player.getUniqueId();
	    	if (args.length > 0) {
	    		if (args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("loby") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j") || args[0].equalsIgnoreCase("dolacz") || args[0].equalsIgnoreCase("d")) {
	    		  if (EventPlayer.isInEvent(uuid)) {
	    		  	if (!(args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("loby"))) {
	    		  		player.sendMessage(msg.ERROR_ALREADY_IN_EVENT.get());
		    		  	return true;
	    		  	}
	    		  	Event e = EventQueue.getCurrent(); //no need to check if null cuz EventPlayer will never pass when EventQueue.getCurrent() returns null
	    		  	e.removePlayer(uuid);
	    		  	e.announceLeaved(uuid);
	    		  	
	    		  	
	    		  } else if (EventPlayer.isInLobby(uuid)) {
	    		  	
	    		  }
	    		  if (CombatUtil.isInCombat(player)) {
	    		  	
	    		  }
	    		} else if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("w") || args[0].equalsIgnoreCase("wyjdz")) {
	    			//TODO
	    		} else if (args[0].equalsIgnoreCase("info") ) {
	    			//TODO
	    		} else if (args[0].equalsIgnoreCase("spectate") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("ogladaj") || args[0].equalsIgnoreCase("o")) {
	    			
	    		} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("pomoc") || args[0].equalsIgnoreCase("p")) {
	    			
	    		} else
	    			sender.sendMessage(msg.ERROR_USAGE.get("/e pomoc"));
	    	} else
	    		sender.sendMessage(msg.ERROR_USAGE.get("/e pomoc"));
			} else
				sender.sendMessage(msg.ERROR_MUST_BE_PLAYER.get());
    }
    return true;
  }
}