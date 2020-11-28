package darkdevs.xyz.Commands;

import java.util.Iterator;

import darkdevs.xyz.Managers.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GroupCmd extends MainCommand {
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You didn't enter a group.");
            return;
        }
        String g = args[0];
        if (args.length < 2) {
            if (SettingsManager.getInstance().getGroup(g) != null) {
                if (SettingsManager.getInstance().getGroup(g).getPerms().size() == 0) {
                    sender.sendMessage(ChatColor.AQUA + "No permissions for " + g);
                    return;
                }
                Iterator<String> iterator = SettingsManager.getInstance().getGroup(g).getPerms().iterator();
                if (iterator.hasNext()) {
                    String perm = iterator.next();
                    sender.sendMessage(ChatColor.AQUA + perm);
                    return;
                }
            } else {
                SettingsManager.getInstance().createGroup(g);
                sender.sendMessage(ChatColor.GREEN + "Created group!");
                return;
            }
        } else {
            if (args[1].equalsIgnoreCase("adduser")) {
                if (sender.hasPermission("darkperms.group.adduser")) {
                    SettingsManager.getInstance().addGroup(args[2], g);
                    sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("removeuser")) {
                if (sender.hasPermission("darkperms.group.removeuser")) {
                    SettingsManager.getInstance().removeGroup(args[2], g);
                    sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("addperm")) {
                if (sender.hasPermission("darkperms.group.addperm")) {
                    SettingsManager.getInstance().getGroup(g).addPerm(args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("removeperm")) {
                if (sender.hasPermission("darkperms.group.removeperm")) {
                    SettingsManager.getInstance().getGroup(g).removePerm(args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
        }
    }

    public GroupCmd() {
        super("group", "<name> [<add | remove | addperm | removeperm> <user | perm>]");
    }
}

