package com.gmail.ZiomuuSs.EventUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Utils.ConfigAccessor;


public class Reward {
	private static HashMap<UUID, HashSet<Reward>> rewards = new HashMap<>();
	private ItemStack item;
	private long expires = 0L;
	
	//for rewards adding
	Reward(ItemStack item, UUID uuid, Main plugin) {
		this.item = item;
		expires = System.currentTimeMillis()+(3*24*60*60*1000);
		if (rewards.containsKey(uuid))
			rewards.get(uuid).add(this);
		else
			rewards.put(uuid, new HashSet<Reward>(Arrays.asList(this)));
		save(uuid, plugin);
	}
	
	//for rewards loading
	Reward(UUID uuid, ItemStack item, Long expires) {
		this.item = item;
		this.expires = expires;
		if (rewards.containsKey(uuid))
			rewards.get(uuid).add(this);
		else
			rewards.put(uuid, new HashSet<Reward>(Arrays.asList(this)));
	}
	
	public long getExpiringDate() {
		return expires;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public static void checkExpiring(Main plugin) {
		for (UUID uuid : rewards.keySet()) {
			for (Reward reward : rewards.get(uuid))
				if (System.currentTimeMillis() >= reward.getExpiringDate())
					rewards.get(uuid).remove(reward);
			save(uuid, plugin);
		}
	}
	
	public static void save(UUID uuid, Main plugin) {
		new File(plugin.getDataFolder() + String.valueOf(File.separatorChar) + "Players" + String.valueOf(File.separatorChar) + uuid.toString(), "rewards.yml").delete();
		ConfigAccessor ca = new ConfigAccessor(plugin, "rewards.yml", "Players", uuid.toString());
		ConfigurationSection cs = ca.getConfig();
		int index = 0;
		for (Reward reward : rewards.get(uuid)) {
			cs.set("reward."+index+".expires", reward.getExpiringDate());
			cs.set("reward."+index+".item", reward.getItem());
			++index;
		}
		ca.saveConfig();
	}
	
	public static void saveAll(Main plugin) {
		for (UUID uuid : rewards.keySet()) 
			save(uuid, plugin);
	}
	
	public static int loadRewards(Main plugin) {
		int count = 0;
		for (File file : new File(plugin.getDataFolder() + String.valueOf(File.separatorChar) + "Players").listFiles()) {
			FileConfiguration cs = YamlConfiguration.loadConfiguration(new File(file.getPath() + String.valueOf(File.separatorChar) + "rewards.yml"));
			for (String s : cs.getConfigurationSection("reward").getKeys(false))  {
				new Reward(UUID.fromString(file.getName()), cs.getItemStack(s+".item"), cs.getLong(s+".expires"));
				++count;
			}
		}
		return count;
	}
	
}
