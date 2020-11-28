package darkdevs.xyz;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (command.getName().equalsIgnoreCase("darkperms")) {
            if (args.length == 0){
                sender.sendMessage(ChatColor.DARK_BLUE + "Running DarkPerms v0.1");
                sender.sendMessage(ChatColor.BLUE + "/darkperms user");
                sender.sendMessage(ChatColor.BLUE + "/darkperms reload");
                sender.sendMessage(ChatColor.BLUE + "/darkperms info");
            }else if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    if (sender.hasPermission("darkperms.reload")) {
                        Main.getmain().saveConfig();
                        Main.getmain().reloadConfig();
                        sender.sendMessage(ChatColor.BLUE + "Reloaded DarkPerms v0.1.2");
                    }else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }else if (args[0].equalsIgnoreCase("info")){
                if (args.length == 1){
                    if (sender.hasPermission("darkperms.info")) {
                        sender.sendMessage(ChatColor.BLUE + "Running DarkPerms v0.1.2");
                        sender.sendMessage(ChatColor.BLUE + "DarkPerms is in beta mode!");
                        sender.sendMessage(ChatColor.BLUE + "Groups will be added in the 0.2.X series");
                        sender.sendMessage(ChatColor.BLUE + "Prefixes will be added in the 0.3.X series");
                    }else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }else if (args[0].equalsIgnoreCase("user")) {
                if (args.length == 1){
                    if (sender.hasPermission("darkperms.user")) {
                        sender.sendMessage(ChatColor.BLUE + "/darkperms user <add|remove> <Player> <Perm>");
                    }else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }else if (args[1].equalsIgnoreCase("add")) {
                if (args.length < 3){
                    sender.sendMessage(ChatColor.BLUE + "/darkperms user <add|remove> <Player> <Perm>");
                }else if (args.length == 4) {
                    if (sender.hasPermission("darkperms.user.add")) {
                        Player player = Bukkit.getPlayer(args[2]);
                        if (!Main.getmain().getOnlinePermissions(player).contains(args[3])) {
                            if (player != null) {
                                ArrayList<String> perm = Main.getmain().getOnlinePermissions(player);
                                if (perm.contains("*") || player.isOp())
                                    sender.sendMessage(ChatColor.GOLD + "That user is opped or has all permissions!");
                                perm.add(args[3]);
                                Main.getmain().getConfig().set(player.getUniqueId().toString() + " / " + player.getDisplayName() + ".permissions", perm);
                                Main.getmain().saveConfig();
                                sender.sendMessage(player.getDisplayName() + " now has " + args[3] + " permission");
                            } else {
                                OfflinePlayer offlinePlayer = Main.getmain().getServer().getOfflinePlayer(args[2]);
                                ArrayList<String> perm = Main.getmain().getOfflinePermissions(offlinePlayer);
                                if (perm.contains("*") || offlinePlayer.isOp())
                                    sender.sendMessage(ChatColor.GOLD + "That user is opped or has all permissions!");
                                perm.add(args[3]);
                                Main.getmain().getConfig().set(offlinePlayer.getUniqueId().toString() + " / " + offlinePlayer.getName() + ".permissions", perm);
                                Main.getmain().saveConfig();
                                sender.sendMessage(offlinePlayer.getName() + " now has " + args[3] + " permission");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "Error: Player already has that permission!");
                        }
                    }else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }else if (args[1].equalsIgnoreCase("remove")){
                if (args.length < 3){
                    sender.sendMessage(ChatColor.BLUE + "/darkperms user <add|remove> <Player> <Perm>");
                }else if (args.length == 4) {
                    if (sender.hasPermission("darkperms.user.remove")) {
                        Player player = Bukkit.getPlayer(args[2]);
                        OfflinePlayer offlinePlayer = Main.getmain().getServer().getOfflinePlayer(args[2]);
                        if (player != null) {
                            if (Main.getmain().getOnlinePermissions(player).contains(args[3])) {
                                ArrayList<String> perm = Main.getmain().getOnlinePermissions(player);
                                perm.remove(args[3]);
                                Main.getmain().getConfig().set(player.getUniqueId().toString() + ".permissions", perm);
                                Main.getmain().saveConfig();
                                sender.sendMessage(player.getDisplayName() + " no longer has " + args[3] + " permission");
                            } else {
                                sender.sendMessage("That User doesn't have that permission!");
                            }
                        } else {
                            if (Main.getmain().getOfflinePermissions(player).contains(args[3])) {
                                ArrayList<String> perm = Main.getmain().getOfflinePermissions(offlinePlayer);
                                perm.remove(args[3]);
                                Main.getmain().getConfig().set(offlinePlayer.getUniqueId().toString() + ".permissions", perm);
                                Main.getmain().saveConfig();
                                sender.sendMessage(ChatColor.GOLD + offlinePlayer.getName() + " no longer has " + args[3] + " permission");
                            } else {
                                sender.sendMessage(ChatColor.GOLD + "That User doesn't have that permission!");
                            }
                        }
                    }else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }
        }
        return false;
    }
}

