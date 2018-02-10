package com.gmail.ZiomuuSs.Utils;

import java.util.HashMap;
import java.util.UUID;

import com.gmail.ZiomuuSs.EventPlayer;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Events.Event.EventMode;
import com.gmail.ZiomuuSs.Events.SpleefEvent;

public class Data {
  protected Main plugin;
  protected ConfigAccessor msgAccessor;
  protected HashMap<String, Event> loadedEvents = new HashMap<>(); //Stores all events. String is name of event
  protected HashMap<UUID, EventPlayer> loadedPlayers = new HashMap<>(); //Stores all players which are in event. (If specific player is not in this hashmap, he is not in event as well
  
  //constructor is loading all config files
  public Data (Main plugin) {
    this.plugin = plugin;
    load();
  }
  
  public boolean load() {
    msgAccessor = new ConfigAccessor(plugin, "Messages.yml");
    msgAccessor.saveDefaultConfig();
    Msg.set(msgAccessor.getConfig());
    return true;
  }
  
  public void createEvent (EventMode m, String name) {
    switch (m) {
    case SPLEEF:
      loadedEvents.put(name, new SpleefEvent(name, plugin));
    }
  }
  
  public String getPrettyEventList() {
    String list = "";
    if (loadedEvents == null)
      return Msg.get("none", false);
    for (String name : loadedEvents.keySet()) {
      list += name+", ";
    }
    if (list.equals(""))
      return Msg.get("none", false);
    else
      list = list.substring(0, list.length() - 2);
    return list;
  }
  
  public String getPrettyEventList(EventMode mode) {
    String list = "";
    if (loadedEvents == null)
      return Msg.get("none", false);
    for (String name : loadedEvents.keySet()) {
      if (loadedEvents.get(name).getMode() == mode)
        list += name+", ";
    }
    if (list.equals(""))
      return Msg.get("none", false);
    else
      list = list.substring(0, list.length() - 2);
    return list;
  }
  
  public Event getEvent(String name) {
    return loadedEvents.get(name);
  }
  public EventPlayer getPlayer(UUID uuid) {
    return loadedPlayers.get(uuid);
  }
  
  public boolean isExist(UUID uuid) {
    if (loadedPlayers.containsKey(uuid))
      return true;
    else
      return false;
  }
  public boolean isExist(String name) {
    if (loadedEvents == null)
      return false;
    if (loadedEvents.containsKey(name))
      return true;
    else
      return false;
  }
  //save specific object to file
  public void save(Event event) {
    //todo
  }
  public void save(EventPlayer player) {
    //todo
  }
}
