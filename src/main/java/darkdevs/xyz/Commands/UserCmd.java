package darkdevs.xyz.Commands;

import java.util.Iterator;
import darkdevs.xyz.Managers.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserCmd extends MainCommand {
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You didn't enter a user.");
            return;
        }
        String p = args[0];
        if (args.length < 2) {
            if (SettingsManager.getInstance().getPerms(p).size() == 0)
                sender.sendMessage(ChatColor.AQUA + "No permissions for " + p);
            Iterator<String> iterator = SettingsManager.getInstance().getPerms(p).iterator();
            if (iterator.hasNext()) {
                String perm = iterator.next();
                sender.sendMessage(ChatColor.AQUA + perm);
                return;
            }
        } else {
            if (args[1].equalsIgnoreCase("addperm")) {
                if (sender.hasPermission("darkperms.user.addperm")) {
                    SettingsManager.getInstance().addPerm(p, args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to " + p);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("removeperm")) {
                if (sender.hasPermission("darkperms.user.removeperm")) {
                    SettingsManager.getInstance().removePerm(p, args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from " + p);
                }else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
                return;
            }
        }
    }

    public UserCmd() {
        super("user", "<name> [<addperm | removeperm> <perm>]");
    }
}

