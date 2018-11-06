package com.gmail.ZiomuuSs.EventUtils;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Events.EventType;
import com.gmail.ZiomuuSs.Events.SpleefEvent;

@SuppressWarnings("unused")
public class EventQueue {
	private static HashMap<String, EventQueue> savedQueues = new HashMap<>(); //list of saved queues
	private static Event current; //event that is currently in progress (null if none is in progress)
	private static Event starting; //event that is starting currently (null if no event is currently starting)
	private HashMap<Event, Reward> queue = new HashMap<>(); //queue of events
	private int delay = 60; //delay between events in queue
	
	
  public static boolean startEvent(EventType type, String name, ItemStack reward) {
  	if (type == EventType.SPLEEF) {
  		if (SpleefEvent.exist(name)) {
  			SpleefEvent.get(name).start(reward);
  		} else
  			return false;
  	} else {
  		return false;
  	}
  	return true;
  }
  
  public static void setCurrent(Event e) {
  	current = e;
  }
  
  public static void setStarting(Event e) {
  	starting = e;
  }
  
  public static Event getStarting() {
    return starting;
  }
  
  public static Event getCurrent() {
  	return current;
  }
  
  //check if there's any event to start soon. If not, returns true.
  public static boolean canInstantStart() {
  	return (current == null && starting == null);
  }
}
