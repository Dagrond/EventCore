package com.gmail.ZiomuuSs.EventUtils;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.ZiomuuSs.Utils.msg;

public class EventAction {
	public enum ActionType {
		APPLY_EFFECT, //apply potion effect to all event participants
		GIVE_ITEM, //give item to all event participants
		END_EVENT, //end event
		ANNOUNCE; //announce all players in event
	}
	private ActionType type; //type of action
	
	public EventAction(ActionType type) {
		this.type = type;
	}
	
	public ActionType getType() {
		return type;
	}
	
	public static HashMap<Integer, EventAction[]> loadActions(String event, FileConfiguration fc) {
		Logger log = Bukkit.getLogger();
		HashMap<Integer, EventAction[]> map = new HashMap<>();
		for (String section : fc.getConfigurationSection("actions").getKeys(false)) {
			if (fc.isInt(section)) {
				//TODO
			} else
				log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "time"));
		}
		return map;
	}
	

}
