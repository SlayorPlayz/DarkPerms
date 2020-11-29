package darkdevs.xyz.Commands;

import darkdevs.xyz.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd extends MainCommand{


    public void run(CommandSender sender, String[] args) {
        if (sender.hasPermission("darkperms.test")) {
            if (args.length == 1){
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reloaded Darkperms v0.1.3");
            }
        }else {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
        }
    }

    public ReloadCmd() {
        super("test", "<player> <perm>");
    }


}
