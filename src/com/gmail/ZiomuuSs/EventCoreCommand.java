package com.gmail.ZiomuuSs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.ZiomuuSs.Events.Event.EventMode;
import com.gmail.ZiomuuSs.Utils.Data;
import com.gmail.ZiomuuSs.Utils.Msg;

public class EventCoreCommand implements CommandExecutor {
  Main plugin;
  Data data;
  
  public EventCoreCommand (Main instance, Data data) {
    plugin = instance;
    this.data = data;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("EventCore") || cmd.getName().equalsIgnoreCase("ce")) {
      if (args.length>0) {
        if (args[0].equalsIgnoreCase("help")) {
          sender.sendMessage(Msg.get("help", false));
          sender.sendMessage(Msg.get("help_1", false));
          return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
          if (sender.hasPermission("eventcore.admin")) {
            if (plugin.getData().load())
              sender.sendMessage(Msg.get("succ_reload", true));
            else
              sender.sendMessage(Msg.get("error_reload", true));
            return true;
          } else {
            sender.sendMessage(Msg.get("error_permission", true));
            return true;
          }
        } else if (args[0].equalsIgnoreCase("version")) {
          sender.sendMessage(Msg.get("version", true, plugin.getDescription().getVersion()));
          return true;
        } else if (args[0].equalsIgnoreCase("list")) {
          if (sender.hasPermission("eventcore.admin")) {
            if (args.length < 2 || args[1].equalsIgnoreCase("all")) {
              sender.sendMessage(Msg.get("event_list", true, data.getPrettyEventList()));
              return true;
            } else if (EventMode.contains(args[1])) {
              sender.sendMessage(Msg.get("specific_event_list", true, args[1].toUpperCase(), data.getPrettyEventList(EventMode.valueOf(args[1].toUpperCase()))));
              return true;
            } else {
              sender.sendMessage(Msg.get("error_invalid_EventMode", true, EventMode.getString()));
              return true;
            }
          } else {
            sender.sendMessage(Msg.get("error_permission", true));
            return true;
          }
        } else if (args[0].equalsIgnoreCase("event") || args[0].equalsIgnoreCase("e")) {
          if (sender.hasPermission("eventcore.admin")) {
            if (args.length == 2 || args.length > 2 && args[2].equalsIgnoreCase("info")) {
              //displaying info about event
            } else if (args.length>3) {
              if (args[2].equalsIgnoreCase("create")) {
                if (EventMode.contains(args[3])) {
                  if (data.isExist(args[1])) {
                    sender.sendMessage(Msg.get("error_event_already_exist", true, args[1]));
                    return true;
                  } else {
                    data.createEvent(EventMode.valueOf(args[3].toUpperCase()), args[1]);
                    sender.sendMessage(Msg.get("event_sucessfully_created", true, args[1], args[3].toUpperCase()));
                    return true;
                  }
                } else {
                  sender.sendMessage(Msg.get("error_invalid_EventMode", true, EventMode.getString()));
                  return true;
                }
              } else if (args[2].equalsIgnoreCase("requiments")) {
                if (data.isExist(args[1])) {
                  data.getEvent(args[1]).showRequiments(sender);
                  return true;
                } else {
                  sender.sendMessage(Msg.get("error_event_not_exist", true));
                  return true;
                }
              }
              //space for setting up an event
            } else {
              sender.sendMessage(Msg.get("error_use", true, "/ce e <event> (arg) (arg)"));
              return true;
            }
          } else {
            sender.sendMessage(Msg.get("error_permission", true));
            return true;
          }
        } else {
          sender.sendMessage(Msg.get("error_no_argument", true));
          return true;
        }
      } else {
        sender.sendMessage(Msg.get("error_no_argument", true));
        return true;
      }
    }
    return true;
  }
}