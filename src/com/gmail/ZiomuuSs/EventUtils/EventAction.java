package com.gmail.ZiomuuSs.EventUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.ZiomuuSs.Utils.ItemsManager;
import com.gmail.ZiomuuSs.Utils.msg;

public class EventAction {
	public enum ActionType {
		APPLY_EFFECT, //apply potion effect to all event participants
		GIVE_ITEM, //give item to all event participants
		END_EVENT, //end event
		ANNOUNCE, //announce all players in event
		RESET_FLOOR; //reset (set to air or restore default material) floor on event. Only possible in event types: Spleef, tntrun. TODO in loadActions()
	}
	private ActionType type; //type of action
	private PotionEffect potion;
	private String imsg; //message if ActionType is ANNOUNCE, or item name (from ItemsManager) if ActionType is GIVE_ITEM
	private int amount; //item amount if ActionType is GIVE_ITEM or floor level if ActionType is RESET_FLOOR
	private boolean restore = true; //used in ActionType RESET_FLOOR. If true, floor is restored. if false floor is changed to air.
	
	
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
	public EventAction(String imsg) {
		type = ActionType.ANNOUNCE;
		this.imsg = ChatColor.translateAlternateColorCodes('&', imsg);
	}
	
	//constructor for GIVE_ITEM
	public EventAction(String imsg, int amount) {
		type = ActionType.GIVE_ITEM;
		this.imsg = imsg;
		this.amount = amount;
	}
	
	public void apply(Set<Player> players) {
		
	}
	
	public ActionType getType() {
		return type;
	}
	
	public static HashMap<Integer, HashSet<EventAction>> loadActions(String event, FileConfiguration fc) {
		Logger log = Bukkit.getLogger();
		HashMap<Integer, HashSet<EventAction>> map = new HashMap<>();
		if (fc.isConfigurationSection("actions")) {
			for (String section : fc.getConfigurationSection("actions").getKeys(false)) {
				if (fc.isInt(new StringBuilder("actions.").append(section).append(".time").toString())) {
					int time = fc.getInt(new StringBuilder("actions.").append(section).append(".time").toString());
					if (time > 0) {
						if (fc.isString(new StringBuilder("actions.").append(section).append(".type").toString())) {
							String atype = fc.getString(new StringBuilder("actions.").append(section).append(".type").toString());
							ActionType type = ActionType.valueOf(atype.toUpperCase());
							if (type != null) {
								if (type == ActionType.APPLY_EFFECT) {
									if (fc.isString(new StringBuilder("actions.").append(section).append(".potion").toString())) {
										String aeffect = fc.getString(new StringBuilder("actions.").append(section).append(".potion").toString());
										PotionEffectType effect = PotionEffectType.getByName(aeffect.toUpperCase());
										if (effect != null) {
											if (fc.isInt(new StringBuilder("actions.").append(section).append(".amplifier").toString())) {
												int amplifier = fc.getInt(new StringBuilder("actions.").append(section).append(".amplifier").toString());
												if (amplifier > 0) {
													if (fc.isInt(new StringBuilder("actions.").append(section).append(".duration").toString())) {
														int duration = fc.getInt(new StringBuilder("actions.").append(section).append(".duration").toString());
														if (duration > 0) {
															if (map.containsKey(time))
																map.get(time).add(new EventAction(new PotionEffect(effect, amplifier, duration)));
															else
																map.put(time, new HashSet<EventAction>(Arrays.asList(new EventAction(new PotionEffect(effect, amplifier, duration)))));
														} else
															log.info(msg.ERROR_CANNOT_LOAD_ACTION_MUST_BE_POSITIVE.get(section, event, "duration"));
													} else
														log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "duration"));
												} else
													log.info(msg.ERROR_CANNOT_LOAD_ACTION_MUST_BE_POSITIVE.get(section, event, "amplifier"));
											} else
												log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "amplifier"));
										} else
											log.info(msg.ERROR_CANNOT_LOAD_ACTION_INVALID_POTION_EFFECT.get(section, event, aeffect));
									} else
										log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "potion"));
								} else if (type == ActionType.ANNOUNCE) {
									if (fc.isString(new StringBuilder("actions.").append(section).append(".message").toString())) {
										String msg = fc.getString(new StringBuilder("actions.").append(section).append(".message").toString());
											if (map.containsKey(time))
												map.get(time).add(new EventAction(msg));
											else
												map.put(time, new HashSet<EventAction>(Arrays.asList(new EventAction(msg))));
									} else
										log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "message"));
								} else if (type == ActionType.GIVE_ITEM) {
									if (fc.isString(new StringBuilder("actions.").append(section).append(".item").toString())) {
										String item = fc.getString(new StringBuilder("actions.").append(section).append(".item").toString());
										if (ItemsManager.itemExist(item)) {
											if (fc.isInt(new StringBuilder("actions.").append(section).append(".amount").toString())) {
												int amount = fc.getInt(new StringBuilder("actions.").append(section).append(".amount").toString());
												if (amount > 0) {
													if (map.containsKey(time))
														map.get(time).add(new EventAction(item, amount));
													else
														map.put(time, new HashSet<EventAction>(Arrays.asList(new EventAction(item, amount))));
												} else
													log.info(msg.ERROR_CANNOT_LOAD_ACTION_MUST_BE_POSITIVE.get(section, event, "amount"));
											} else
												log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "amount"));
										} else
											log.info(msg.ERROR_CANNOT_LOAD_ACTION_INVALID_ITEM.get(section, event, item));
									} else
										log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "material"));
								} else if (type == ActionType.END_EVENT) {
									if (map.containsKey(time))
										map.get(time).add(new EventAction());
									else
										map.put(time, new HashSet<EventAction>(Arrays.asList(new EventAction())));
								}
							} else
								log.info(msg.ERROR_CANNOT_LOAD_ACTION_INVALID_TYPE.get(section, event, atype));
						} else
							log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "type"));
					} else
						log.info(msg.ERROR_CANNOT_LOAD_ACTION_MUST_BE_POSITIVE.get(section, event, "time"));
				} else
					log.info(msg.ERROR_CANNOT_LOAD_ACTION_MISSING.get(section, event, "time"));
			}
		}
		return map;
	}
	

}
