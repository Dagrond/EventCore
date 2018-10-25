package com.gmail.ZiomuuSs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class EventPlayer {
	private UUID uuid;
  private Location loc;
  private double Exp;
  private double Health;
  private double MaxHealth;
  private float Saturation;
  private int Hunger;
  private GameMode Gamemode;
  private boolean Fly;
  private ArrayList<PotionEffect> potions = new ArrayList<PotionEffect>();
  private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
  
  public EventPlayer() {
  	
  }
}
