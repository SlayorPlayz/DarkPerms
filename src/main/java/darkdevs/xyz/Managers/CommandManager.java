package darkdevs.xyz.Managers;

import java.util.ArrayList;
import java.util.Arrays;

import darkdevs.xyz.Commands.*;
import darkdevs.xyz.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
    private ArrayList<MainCommand> cmds = new ArrayList<>();

    Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    public CommandManager() {
        this.cmds.add(new GroupCmd());
        this.cmds.add(new UserCmd());
        this.cmds.add(new TestCmd());
        this.cmds.add(new ReloadCmd());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("darkperms")) {
            if (args.length == 0) {
                for (MainCommand c : this.cmds) {
                    sender.sendMessage(ChatColor.BLUE + "Running DarkPerms v0.1.3");
                    sender.sendMessage(ChatColor.BLUE + "/darkperms " + c.getName() + " " + c.getArgs());
                }

                return true;
            }
            ArrayList<String> a = new ArrayList<>(Arrays.asList(args));
            a.remove(0);
            for (MainCommand c : this.cmds) {
                if (c.getName().equalsIgnoreCase(args[0])) {
                    try {
                        c.run(sender, a.<String>toArray(new String[a.size()]));
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "An error has occurred.");
                        e.printStackTrace();
                    }
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Invalid command!");
        }
        return true;
    }
}

