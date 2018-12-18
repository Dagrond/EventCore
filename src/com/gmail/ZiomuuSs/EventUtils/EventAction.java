package com.gmail.ZiomuuSs.EventUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.ZiomuuSs.Utils.msg;

public class EventAction {
	public enum ActionType {
		APPLY_EFFECT, //apply potion effect to all event participants
		GIVE_ITEM, //give item to all event participants
		END_EVENT, //end event
		ANNOUNCE; //announce all players in event
	}
	private ActionType type; //type of action
	private PotionEffect potion;
	private String message;
	private ItemStack item;
	private int itemAmount;
	
	
	//constructor for END_EVENT
	public EventAction() {
		type = ActionType.END_EVENT;
	}
	
	//constructor for APPLY_EFFECT
	public EventAction(PotionEffect potion) {
		type = ActionType.APPLY_EFFECT;
		this.potion = potion;
	}
	
	//constructor for ANNOUNCE
	public EventAction(String message) {
		type = ActionType.ANNOUNCE;
		this.message = message;
	}
	
	//constructor for GIVE_ITEM
	public EventAction(ItemStack item, int itemAmount) {
		type = ActionType.GIVE_ITEM;
		this.item = item;
		this.itemAmount = itemAmount;
	}
	
	public void apply(Player[] players) {
		
	}
	
	public ActionType getType() {
		return type;
	}
	
	public static HashMap<Integer, EventAction[]> loadActions(String event, FileConfiguration fc) {
		Logger log = Bukkit.getLogger();
		HashMap<Integer, List<EventAction>> map = new HashMap<>();
		if (fc.isConfigurationSection("actions")) {
			for (String section : fc.getConfigurationSection("actions").getKeys(false)) {
				if (fc.isInt(new StringBuilder(section).append(".time").toString())) {
					//TODO
					if (fc.isString(new StringBuilder(section).append(".type").toString())) {
						String atype = fc.getString(new StringBuilder(section).append(".type").toString());
						ActionType type = ActionType.valueOf(atype.toUpperCase());
						if (type != null) {
							if (type == ActionType.APPLY_EFFECT) {
								if (fc.isString(new StringBuilder(section).append(".potion").toString())) {
									String aeffect = fc.getString(new StringBuilder(section).append(".potion").toString());
									PotionEffectType effect = PotionEffectType.getByName(aeffect.toUpperCase());
									if (effect != null) {
										if (fc.isInt(new StringBuilder(section).append(".amplifier").toString())) {
											if (fc.isInt(new StringBuilder(section).append(".duration").toString())) {
												int time = fc.getInt(new StringBuilder(section).append(".time").toString());
												if (map.containsKey(time))
													map.get(time).add(new EventAction(new PotionEffect(effect,
													fc.getInt(new StringBuilder(section).append(".amplifier").toString()),
													fc.getInt(new StringBuilder(section).append(".duration").toString()))));
												else
													map.put(time, Collections.singletonList(new EventAction(new PotionEffect(effect,
													fc.getInt(new StringBuilder(section).append(".amplifier").toString()),
													fc.getInt(new StringBuilder(section).append(".duration").toString())))));
											} else
												log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "duration"));
										} else
											log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "amplifier"));
									} else
										log.info(msg.ERROR_CANNOT_LOAD_ACTION_INVALID_POTION_EFFECT.get(section, event, aeffect));
								} else
									log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "potion"));
							} else if (type == ActionType.ANNOUNCE) {
								
							} else if (type == ActionType.GIVE_ITEM) {
								
							} else if (type == ActionType.END_EVENT) {
								
							}
						} else
							log.info(msg.ERROR_CANNOT_LOAD_ACTION_INVALID_TYPE.get(section, event, atype));
					} else
						log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "type"));
				} else
					log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "time"));
			}
		}
		return map;
	}
	

}
