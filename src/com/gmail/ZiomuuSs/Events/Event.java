package com.gmail.ZiomuuSs.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.gmail.ZiomuuSs.EventPlayer;
import com.gmail.ZiomuuSs.Main;

public class Event implements Listener {
  public static enum EventStatus {
    NOT_READY, READY, IN_PROGRESS, STARTING;
  }
  protected Main plugin;
  protected String name; //name of event
  protected boolean startWhenMax; //Should event start (ignoring delay) when amount of players in lobby hit max?
  protected HashMap<UUID, EventPlayer> players = new HashMap<>(); //Players in event
  protected ArrayList<Location> startPoints = new ArrayList<>(); //coordinates of spawn points when event begin
  protected Inventory startInventory; //Inventory for every player when event begins
  protected int maxPlayers = -1; //max players in event
  protected int minPlayers = 2; //min amount of players to start event
  protected EventStatus status; //Status of event (lobby(players are in lobby, event will start soon)/during(event is in progress)/off(event disabled to use or not ready to use)/free(there is not event))
  protected int delay; //amount of time in seconds after event will start (change it's status from "lobby" to "during"). Can be bypassed by command or if StartWhenMax=true
  
  public Event () {}
  
}
