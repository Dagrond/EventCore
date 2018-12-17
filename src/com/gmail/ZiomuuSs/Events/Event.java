package com.gmail.ZiomuuSs.Events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.EventUtils.EventPlayer;
import com.gmail.ZiomuuSs.EventUtils.EventQueue;
import com.gmail.ZiomuuSs.EventUtils.Lobby;
import com.gmail.ZiomuuSs.EventUtils.Reward;
import com.gmail.ZiomuuSs.Utils.CountdownTimer;
import com.gmail.ZiomuuSs.Utils.msg;

public class Event implements Listener {
	protected static EventType type = EventType.BASIC;
  protected Main plugin;
  protected String name; //display name of event
  protected HashMap<Player, EventPlayer> players = new HashMap<>(); //Players in event
  protected ArrayList<Location> startPoints = new ArrayList<>(); //coordinates of spawn points when event begin
  protected Inventory startInventory; //Inventory for every player when event begins
  protected int maxPlayers = -1; //max players in event, when <0 unlimited
  protected int minPlayers = 2; //min amount of players to start event
  protected int delay = 60;
  protected long started = 0L; //time in milis when this event was started
  protected String[] description = {"&cOpis tego eventu nie zosta³ ustawiony!",  "&cJeœli uwa¿asz to za b³¹d, zg³oœ to administratorowi serwera."}; //description of event
  //options for Teams
  protected Team.OptionStatus	nameTagVisibility = Team.OptionStatus.ALWAYS;
  protected Team.OptionStatus collisionRule = Team.OptionStatus.ALWAYS;
  protected boolean friendlyFire = false;
  protected boolean seeFriendlyInvisibles = false;
  protected Team.OptionStatus deathMessageVisibility = Team.OptionStatus.NEVER;
  protected String prefix = "";
  protected ItemStack reward;
  protected boolean cancelled = false; //defines if event is cancelled (needed to cancel event if is stating)
  private int toStart = 0; //in how many seconds event will start. 0 if started
  protected int timer = 0; //time, in seconds, how long event is in progress, does not count 'delay'
  protected BossBar bar;
  
  public Event(Main plugin, String name) {
  	this.plugin = plugin;
  	this.name = name;
  	bar = Bukkit.createBossBar(msg.EVENT_STARTING_SOON.get(name, type.toString(), Integer.toString(delay)), BarColor.GREEN, BarStyle.SOLID);
  }
  
  
  public static void loadAll(Main plugin) {
  	int total = 0;
  	int temp = 0;
  	Logger log = Bukkit.getLogger();
  	StringBuilder including = new StringBuilder("");
  	if(Lobby.load(plugin))
  		log.info(msg.LOADED_LOBBY.get());
  	else
  		log.info(msg.ERROR_LOAD_LOBBY_NOT_SET.get());
  	if (plugin.getWorldGuard() != null) {
  		temp = SpleefEvent.load(plugin);
  		total += temp;
  		including.append(new StringBuilder(Integer.toString(temp)).append("x Spleef, "));
  	} else 
  		log.info(msg.ERROR_LOAD_CANNOT_LOAD_EVENT_NO_PLUGIN_FOUND.get("Spleef", "WorldGuard"));
  	//another event...
  	log.info(msg.LOADED.get(Integer.toString(total), including.substring(0, including.length()-2)));
  }
  
	public void startSequence() {
		//teleport players to startpoints
		int index = 0;
		for (UUID uuid : Lobby.getAllPlayers().keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				player.teleport(startPoints.get(index));
				if (index == startPoints.size()-1)
					index = 0;
				else
					++index;
				players.put(player, Lobby.getPlayer(uuid));
			}
    }
		Lobby.clearPlayers();
		//register events
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	
	}
  
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (players.containsKey(player)) {
			kick(player);
			if (!checkWinCondition())
				broadcast(msg.EVENT_QUIT.get(e.getPlayer().getName(), Integer.toString(players.size())));
		}
	}
	
	public String getName() {
	  return name;
	}
	
	public void addToBossBar(Player player) {
		bar.addPlayer(player);
	}
	
	public Collection<Player> getPlayers() {
		return players.keySet();
	}
	
	//boolean lobby - if true, player is throw back to lobby. If false, player is returned (location, inventory etc before even /e lobby)
	public void removePlayer(Player player, boolean lobby) {
		if (players.containsKey(player)) {
			if (lobby)
				players.get(player).returnToLobby();
			else
				players.get(player).restore();
			players.remove(player);
			checkWinCondition();
		}
	}
	
	public void broadcast(String message) {
		for (Player player : players.keySet()) {
			player.sendMessage(message); //no need to check if online cuz when he gets offline, he is automatically removed from this list
		}
	}
	
	//why? Cuz different events can have teams etc so message when someone leaves can be different.
	public void announceLeaved(UUID uuid) {
		broadcast(msg.EVENT_PLAYER_LEAVED.get(Bukkit.getOfflinePlayer(uuid).getName(), Integer.toString(players.size())));
	}
	
  public void start(ItemStack reward) {
  	CountdownTimer timer = new CountdownTimer(plugin, delay,
        () -> {
        	this.reward = reward;
          bar.setProgress(0);
          bar.setVisible(true);
          for (Player player : Bukkit.getOnlinePlayers())
          	bar.addPlayer(player);
          beforeStart();
          EventQueue.setStarting(this);
        },
         () -> {
        	 bar.setVisible(false);
        	 if (Lobby.size() < minPlayers) {
             EventQueue.setStarting(null);
             Bukkit.broadcastMessage(msg.ERROR_STARTING_NOT_ENOUGH_PLAYERS.get(name, Integer.toString(Lobby.size()), Integer.toString(minPlayers)));
             kickAll();
             return;
        	 }
        	 //get players in lobby and transfer them to event
        	 EventQueue.setCurrent(this);
        	 EventQueue.setStarting(null);
        	 startSequence();
        	 started = System.currentTimeMillis();
          },
        (t) -> {
          if (cancelled) {
            t.cancel();
            this.reward = null;
            bar.setVisible(false);
            EventQueue.setStarting(null);
            Bukkit.broadcastMessage(msg.ERROR_STARTING_CANCELLED.get(name));
            kickAll();
            return;
          }
          toStart = t.getSecondsLeft();
          bar.setProgress((double) toStart/delay);
          bar.setTitle(msg.EVENT_STARTING_SOON.get(name, type.toString(), Integer.toString(toStart)));
          if (toStart == delay/2)
          	Bukkit.broadcastMessage(msg.EVENT_STARTING_SOON.get(type.toString()));
          else if (toStart == 10)
          	Bukkit.broadcastMessage(msg.EVENT_STARTING_SOON.get(type.toString()));
        });
    timer.scheduleTimer();
  }
  
  
  //some events need to run some code before event starts, at beggining of delay
  public void beforeStart() {}
  
  public void stopSequence() {
    started = 0L;
    if (EventQueue.getCurrent().getName().equals(getName())) {
    	EventQueue.setCurrent(null);
    } else if (EventQueue.getStarting().getName().equals(getName())) {
    	EventQueue.setStarting(null);
    }
    HandlerList.unregisterAll(this);
    kickAll();
  }
  
  public long getStarted() {
    return started;
  }
  
  public int getToStart() {
  	return toStart;
  }
  
  public void kickAll() {
  	for (EventPlayer ep : players.values()) {
  		ep.returnToLobby();
  	}
  	players.clear();
  }
  
  public void kick(Player player) {
  	if (players.containsKey(player)) {
  		players.get(player).returnToLobby();
  		players.remove(player);
  	}
  }
  
  public EventType getType() {
  	return type;
  }
  
  //by default check if there is only 1 player left and handle winning
	protected boolean checkWinCondition() {
		if (players.size() == 1) {
			for (Player player : players.keySet()) {
				Bukkit.broadcastMessage(msg.EVENT_WIN.get(name, player.getName()));
				new Reward(reward, player.getUniqueId(), plugin);
				player.sendMessage(msg.REWARD_ADDED.get());
				players.get(player).returnToLobby();
				stopSequence();
				return true;
			}
		}
		return false;
	}
  
  public boolean loadBasicVariables(Main plugin, FileConfiguration fc, String name, String type, Logger log, World world) {
  	//loading needed variables
  	//loading startpoints
		if (fc.isConfigurationSection("startpoints")) {
			int loadedPoints = 0;
			for (String point : fc.getConfigurationSection("startpoints").getKeys(false)) {
				String x = new StringBuilder("startpoints.").append(point).append(".x").toString();
				String y = new StringBuilder("startpoints.").append(point).append(".y").toString();
				String z = new StringBuilder("startpoints.").append(point).append(".z").toString();
				String yaw = new StringBuilder("startpoints.").append(point).append(".yaw").toString();
				String pitch = new StringBuilder("startpoints.").append(point).append(".pitch").toString();
				if (fc.isDouble(x) && fc.isDouble(y) && fc.isDouble(z)) {
					Location loc;
					if (fc.isDouble(yaw) && fc.isDouble(pitch))
						loc = new Location(world, fc.getDouble(x), fc.getDouble(y), fc.getDouble(z), (float) fc.getDouble(yaw), (float) fc.getDouble(pitch));
					else
						loc = new Location(world, fc.getDouble(x), fc.getDouble(y), fc.getDouble(z));
					startPoints.add(loc);
					++loadedPoints;
				} else {
					log.info(msg.ERROR_LOAD_BAD_STARTPOINT.get(point, name, type));
				}
			}
			if (loadedPoints < 1) {
				log.info(msg.ERROR_LOAD_NO_STARTPOINTS.get(name, type));
				return false;
			}
		} else {
			log.info(msg.ERROR_LOAD_NOT_CONFIGURED.get(name, type, "startpoints"));
			return false;
		}
		//loading optional variables
		//loading maxPlayers
		if (fc.isInt("maxplayers"))
			maxPlayers = fc.getInt("maxplayers");
		//loading minPlayers
		if (fc.isInt("minplayers"))
			minPlayers = fc.getInt("minplayers");
		//loading description
		if (fc.isList("description"))
			description = fc.getStringList("description").toArray(description);
		//loading startInventory
		//TODO
		return true;
  }
  
}
