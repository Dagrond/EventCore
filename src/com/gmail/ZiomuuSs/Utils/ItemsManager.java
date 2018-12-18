package com.gmail.ZiomuuSs.Utils;

import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.gmail.ZiomuuSs.Main;

public class ItemsManager {
	private static ConfigAccessor config;
	private static FileConfiguration fc;
	
	public void setConfigAccessor(Main plugin) {
		config = new ConfigAccessor(plugin, "items.yml");
		fc = config.getConfig();
	}
	
	public static void saveItem(String name, ItemStack item) {
		fc.set(new StringBuilder("item.").append(name).toString(), item);
		config.saveConfig();
	}
	
	public static void saveInventory(String name, ItemStack[] items) {
		fc.set(new StringBuilder("inventory.").append(name).toString(), Arrays.asList(items));
		config.saveConfig();
	}
	
	public static String getFormattedItemList() {
		String s = "";
		for (String name : fc.getConfigurationSection("item").getKeys(false)) {
			s += new StringBuilder(name).append(", ").toString();
		}
		if (!s.equals(""))
			s = s.substring(0, s.length()-2);
		else
			s = msg.NONE.get();
		return s;
	}
	
	public static String getFormattedInventoryList() {
		String s = "";
		for (String name : fc.getConfigurationSection("inventory").getKeys(false)) {
			s += new StringBuilder(name).append(", ").toString();
		}
		if (!s.equals(""))
			s = s.substring(0, s.length()-2);
		else
			s = msg.NONE.get();
		return s;
	}
	
	public static void removeItem(String name) {
		fc.set(new StringBuilder("item.").append(name).toString(), null);
		config.saveConfig();
	}
	
	public static void removeInventory(String name) {
		fc.set(new StringBuilder("inventory.").append(name).toString(), null);
		config.saveConfig();
	}
	
	public static ItemStack getItem(String name) {
		return fc.getItemStack(new StringBuilder("item.").append(name).toString());
	}
	
	public static ItemStack[] getInventory(String name) {
		return fc.getList(new StringBuilder("inventory.").append(name).toString()).toArray(new ItemStack[0]);
	}
	
	public static boolean itemExist(String name) {
		return fc.isItemStack(new StringBuilder("item.").append(name).toString());
	}
	
	public static boolean inventoryExist(String name) {
		return fc.isList(new StringBuilder("inventory.").append(name).toString());
	}
}
