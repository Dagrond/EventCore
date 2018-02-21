package com.gmail.ZiomuuSs;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.ZiomuuSs.Utils.Msg;

public class Reward {
  protected Main plugin;
  protected HashSet<ItemStack> items = new HashSet<>();
  protected double money = 0;
  protected int level = 0;
  
  public Reward (Main main) {
    plugin = main;
  }
  
  public void grant (Player player) {
    int givedcount = 0;
    int droppedcount = 0;
    if (!items.isEmpty()) {
      for (ItemStack item : items) {
        int count = 0;
        for (ItemStack i : player.getInventory()) {
          if (i == null) {
              count++;
          }
        }
        if (count > 0)
          player.getInventory().addItem(item);
        else
          player.getWorld().dropItem(player.getLocation(), item);
      }
      if (givedcount > 0 && droppedcount > 0)
        player.sendMessage(Msg.get("gived_and_dropped_items", true, Integer.toString(givedcount), Integer.toString(droppedcount)));
      else if (givedcount > 0)
        player.sendMessage(Msg.get("gived_items", true, Integer.toString(givedcount)));
      else
        player.sendMessage(Msg.get("dropped_items", true, Integer.toString(droppedcount)));
    }
    if (money > 0) {
      plugin.getEconomy().depositPlayer(player, money);
      player.sendMessage(Msg.get("gived_money", true, Double.toString(money)));
    }
    if (level > 0) {
      player.setLevel(level);
      player.sendMessage(Msg.get("gived_level", true, Integer.toString(level)));
    }
  }
  
  //getters & setters
  public HashSet<ItemStack> getItems() {
    return items;
  }
  public void resetItems() {
    items = new HashSet<>();
  }
  public void addItem(ItemStack item) {
    items.add(item);
  }
  public void setMoney(double money) {
    this.money = money;
  }
  public void setLevel(int level) {
    this.level = level;
  }
}
