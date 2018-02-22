package com.gmail.ZiomuuSs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ZiomuuSs.Events.Event;
import com.gmail.ZiomuuSs.Events.Event.EventMode;
import com.gmail.ZiomuuSs.Events.Event.EventStatus;
import com.gmail.ZiomuuSs.Events.Event.REQUIMENT;
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
              if (data.isExist(args[1])) {
                data.getEvent(args[1]).showRequiments(sender);
                return true;
              } else {
                sender.sendMessage(Msg.get("error_event_not_exist", true, args[1]));
                return true;
              }
            } else if (args.length>2) {
              if (args[2].equalsIgnoreCase("create")) {
                if (args.length>3) {
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
                } else {
                  sender.sendMessage(Msg.get("error_use", true, "/ce e <event> create <type>"));
                  return true;
                }
              } else if (args[2].equalsIgnoreCase("requiments")) {
                if (data.isExist(args[1])) {
                  data.getEvent(args[1]).showRequiments(sender);
                  return true;
                } else {
                  sender.sendMessage(Msg.get("error_event_not_exist", true, args[1]));
                  return true;
                }
              } else if (args[2].equalsIgnoreCase("set")) {
                if (data.isExist(args[1])) {
                  if (args.length>3) {
                    if (args[3].equalsIgnoreCase("lobby")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.LOBBY)) {
                        if (sender instanceof Player) {
                          Location l = ((Player) sender).getLocation();
                          data.getEvent(args[1]).setLobby(l);
                          sender.sendMessage(Msg.get("lobby_set", true, Msg.get("coordinates", false, Integer.toString(l.getBlockX()), Integer.toString(l.getBlockY()), Integer.toString(l.getBlockZ()), l.getWorld().getName())));
                          return true;
                        } else {
                          sender.sendMessage(Msg.get("error_player_required", true));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "LOBBY", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("maxplayers")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.MAXPLAYERS)) {
                        if (args.length>4) {
                          if (args[4].matches("-?\\d+")) {
                            int max =  Integer.valueOf(args[4]);
                            if (max>1) {
                              data.getEvent(args[1]).setMaxPlayers(max);
                              sender.sendMessage(Msg.get("value_set", true, "maxPlayers", args[4]));
                              return true;
                            } else {
                              sender.sendMessage(Msg.get("error_must_be_greater_than", true, "maxPlayers", "1"));
                              return true;
                            }
                          } else if (args[4].equalsIgnoreCase("unlimited")) {
                            data.getEvent(args[1]).setMaxPlayers(0);
                            sender.sendMessage(Msg.get("value_set", true, "maxPlayers", args[4]));
                            return true;
                          } else {
                            sender.sendMessage(Msg.get("error_must_be_integer", true, "maxPlayers"));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set maxplayers <(integer)/(unlimited)>"));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "MAXPLAYERS", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("minPlayers")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.MINPLAYERS)) {
                        if (args.length>4) {
                          if (args[4].matches("-?\\d+")) {
                            int min =  Integer.valueOf(args[4]);
                            if (min>0) {
                              if (data.getEvent(args[1]).getMaxPlayers()>=min) {
                                data.getEvent(args[1]).setMinPlayers(min);
                                sender.sendMessage(Msg.get("value_set", true, "minPlayers", args[4]));
                                return true;
                              } else {
                                sender.sendMessage(Msg.get("error_must_be_greater_than", true, "maxPlayers", "minPlayers"));
                                return true;
                              }
                            } else {
                              sender.sendMessage(Msg.get("error_must_be_greater_than", true, "minPlayers", "0"));
                              return true;
                            }
                          } else {
                            sender.sendMessage(Msg.get("error_must_be_integer", true, "minPlayers"));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set minplayers <arg>"));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "MINPLAYERS", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("surfaceregion")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.SURFACE)) {
                        if (sender instanceof Player) {
                          if (args.length>4) {
                            if (data.getWorldGuard().getRegionManager(((Player) sender).getWorld()).getRegion(args[4]) != null) {
                              data.getEvent(args[1]).setSurface(data.getWorldGuard().getRegionManager(((Player) sender).getWorld()).getRegion(args[4]), ((Player) sender).getWorld());
                              sender.sendMessage(Msg.get("region_set", true, args[4]));
                              return true;
                            } else {
                              sender.sendMessage(Msg.get("error_region_not_exist", true, args[4]));
                              return true;
                            }
                          } else {
                            sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set surfaceregion <region>"));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_player_required", true));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "SURFACE", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("surfacematerial")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.SURFACE)) {
                        if (args.length>4) {
                          Material m = Material.matchMaterial(args[4]);
                          if (m != null) {
                            if (m.isBlock() && m.isSolid()) {
                              data.getEvent(args[1]).setSurfaceMaterial(m);
                              sender.sendMessage(Msg.get("material_set", true, args[4].toUpperCase()));
                              return true;
                            } else {
                              sender.sendMessage(Msg.get("error_material_not_solid", true));
                              return true;
                            }
                          } else {
                            sender.sendMessage(Msg.get("error_material_not_exist", true, args[4].toUpperCase()));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set surfacematerial <material>"));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "SURFACE", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("inventory")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.INVENTORY)) {
                        if (sender instanceof Player) {
                          data.getEvent(args[1]).setStartInventory(((Player) sender).getInventory());
                          sender.sendMessage(Msg.get("iventory_set", true, args[1]));
                          return true;
                        } else {
                          sender.sendMessage(Msg.get("error_player_required", true));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "INVENTORY", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("miny")) {
                      if (data.getEvent(args[1]).isRequired(REQUIMENT.MINY)) {
                        if (args.length>4) {
                          if (args[4].matches("-?\\d+")) {
                            int min =  Integer.valueOf(args[4]);
                            if (min>0 && min< 255) {
                              data.getEvent(args[1]).setMinY(min);
                              sender.sendMessage(Msg.get("miny_set", true, args[4]));
                              return true;
                            } else {
                              sender.sendMessage(Msg.get("error_must_be_beetween", true, "minPlayers", "0", "255"));
                              return true;
                            }
                          } else {
                            sender.sendMessage(Msg.get("error_must_be_integer", true, "minPlayers"));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set minplayers <arg>"));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_parameter_match", true, "MINY", data.getEvent(args[1]).getMode().toString()));
                        return true;
                      }
                    } else if (args[3].equalsIgnoreCase("reward")) {
                      if (args.length > 5) {
                        if (args[4].equalsIgnoreCase("money")) {
                          if (args[5].matches("(-?\\\\d+(\\\\.\\\\d+)?)(?:\\\\s(-?\\\\d+(\\\\.\\\\d+)?)){2}")) {
                            data.getEvent(args[1]).getReward().setMoney(Double.valueOf(args[5]));
                            sender.sendMessage(Msg.get("reward_money_set", true, args[1], args[5]));
                            return true;
                          } else {
                            sender.sendMessage(Msg.get("error_must_be_double", true));
                            return true;
                          }
                        } else if (args[4].equalsIgnoreCase("level")) {
                          if (args[5].matches("-?\\d+")) {
                            data.getEvent(args[1]).getReward().setLevel(Integer.valueOf(args[5]));;
                            sender.sendMessage(Msg.get("reward_level_set", true, args[1], args[5]));
                            return true;
                          } else {
                            sender.sendMessage(Msg.get("error_must_be_integer", true, "level"));
                            return true;
                          }
                        } else if (args[4].equalsIgnoreCase("item")) {
                          if (sender instanceof Player) {
                            //todo
                          } else {
                            sender.sendMessage(Msg.get("error_player_required", true));
                            return true;
                          }
                        } else {
                          sender.sendMessage(Msg.get("error_reward_type", true, "money, level, item"));
                          return true;
                        }
                      } else {
                        sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set reward <reward_type> <amount/action>"));
                        return true;
                      }
                    }
                  } else {
                    sender.sendMessage(Msg.get("error_use", true, "/ce e <event> set (arg)"));
                    return true;
                  }
                } else {
                  sender.sendMessage(Msg.get("error_event_not_exist", true, args[1]));
                  return true;
                }
              } else if (args[2].equalsIgnoreCase("start")) {
                if (data.isExist(args[1])) {
                  Event e = data.getEvent(args[1]);
                  if (e.getStatus() == EventStatus.READY) {
                    e.start();
                    sender.sendMessage(Msg.get("event_started", true, args[1]));
                    return true;
                  } else {
                    sender.sendMessage(Msg.get("error_event_status", true, args[1], e.getStatus().toString()));
                    return true;
                  }
                } else {
                  sender.sendMessage(Msg.get("error_event_not_exist", true, args[1]));
                  return true;
                }
              } else if (args[2].equalsIgnoreCase("startpoint")) {
                if (sender instanceof Player) {
                  if (data.getEvent(args[1]).isRequired(REQUIMENT.STARTPOINTS)) {
                    if (args.length > 3 && args[3].matches("-?\\d+")) {
                      int index = Integer.valueOf(args[3]);
                      if (index>0) {
                        if (data.getEvent(args[1]).setStartPoints(((Player) sender).getLocation(), index))
                          sender.sendMessage(Msg.get("startpoint_setted", true, Integer.toString(index), Integer.toString(data.getEvent(args[1]).getStartPoints().size())));
                        else
                          sender.sendMessage(Msg.get("startpoint_added", true, Integer.toString(data.getEvent(args[1]).getStartPoints().size())));
                        return true;
                      } else {
                        sender.sendMessage(Msg.get("error_must_be_greater_than", true, "startPoint", "0"));
                        return true;
                      }
                    } else {
                      data.getEvent(args[1]).setStartPoints(((Player) sender).getLocation(), 0);
                      sender.sendMessage(Msg.get("startpoint_added", true, Integer.toString(data.getEvent(args[1]).getStartPoints().size())));
                      return true;
                    }
                  } else {
                    sender.sendMessage(Msg.get("error_parameter_match", true, "STARTPOINTS", data.getEvent(args[1]).getMode().toString()));
                    return true;
                  }
                } else {
                  sender.sendMessage(Msg.get("error_player_required", true));
                  return true;
                }
              }//next e arguments
            } else {
              sender.sendMessage(Msg.get("error_use", true, "/ce e <event> (arg) (arg)"));
              return true;
            }
          } else {
            sender.sendMessage(Msg.get("error_permission", true));
            return true;
          }
        } else if(args[0].equalsIgnoreCase("join")) {
          if (sender instanceof Player) {
            if (data.getEventInProgress() != null || data.getEventInProgress().getStatus() != EventStatus.IN_LOBBY) {
              if (data.isInEvent(((Player) sender)) == false) {
                if (data.getEvent(args[1]).getPlayers().size() <= data.getEvent(args[1]).getMaxPlayers()) {
                  data.addPlayerToEvent(((Player) sender));
                  sender.sendMessage(Msg.get("event_join", true, data.getEventInProgress().toString()));
                  return true;
                } else {
                  sender.sendMessage(Msg.get("error_max_players_reached", true));
                  return true;
                }
              } else {
                sender.sendMessage(Msg.get("error_already_in_event", true));
                return true;
              }
            } else {
              sender.sendMessage(Msg.get("", true));
              return true;
            }
          } else {
            sender.sendMessage(Msg.get("error_player_required", true));
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