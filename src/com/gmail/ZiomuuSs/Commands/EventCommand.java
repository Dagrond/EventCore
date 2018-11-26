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
import com.gmail.ZiomuuSs.EventUtils.Lobby;
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
	    		if (args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("j")) {
	    		  if (EventPlayer.isInEvent(player)) {
	    		  	Event e = EventQueue.getCurrent();
	    		  	e.removePlayer(player, true);
	    		  	e.announceLeaved(uuid);
	    		  	player.sendMessage(Lobby.getMotd());
	    		  	return true;
	    		  } else if (EventPlayer.isInLobby(uuid)) {
	    		  	player.sendMessage(msg.ERROR_ALREADY_IN_LOBBY.get());
	    		  	return true;
	    		  }
	    		  if (CombatUtil.isInCombat(player)) {
	    		  	player.sendMessage(msg.ERROR_COMBAT.get());
	    		  	return true;
	    		  }
	    		  Lobby.addPlayer(uuid, new EventPlayer(player, plugin, Lobby.getLocation()));
	    		  player.sendMessage(Lobby.getMotd());
	    		  return true;
	    		} else if (args[0].equalsIgnoreCase("wyjdz") || args[0].equalsIgnoreCase("l")) {
	    			if (EventPlayer.isInEvent(player)) {
	    				Event e = EventQueue.getCurrent();
	    		  	e.removePlayer(player, false);
	    		  	e.announceLeaved(uuid);
	    		  	player.sendMessage(msg.EVENT_LEAVE.get());
	    			} else if (EventPlayer.isInLobby(uuid)) {
	    				Lobby.getPlayer(uuid).restore();
	    				Lobby.delPlayer(uuid);
	    				player.sendMessage(msg.LOBBY_LEAVE.get());
	    			} else {
	    				player.sendMessage(msg.ERROR_EVENT_NOT_IN_EVENT_OR_LOBBY.get());
	    			}
	    			return true;
	    		} else if (args[0].equalsIgnoreCase("info")) {
	    			//TODO
	    		} else if (args[0].equalsIgnoreCase("ogladaj") || args[0].equalsIgnoreCase("s")) {
	    			
	    		} else if (args[0].equalsIgnoreCase("pomoc") || args[0].equalsIgnoreCase("help")) {
	    			
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