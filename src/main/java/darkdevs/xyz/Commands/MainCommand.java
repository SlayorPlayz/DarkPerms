package darkdevs.xyz.Commands;

import darkdevs.xyz.Main;
import org.bukkit.command.CommandSender;

public abstract class MainCommand {

    Main plugin;

    public MainCommand(Main plugin) {
        this.plugin = plugin;
    }

    private String name;

    private String args;

    public MainCommand(String name, String args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return this.name;
    }

    public String getArgs() {
        return this.args;
    }

    public abstract void run(CommandSender paramCommandSender, String[] paramArrayOfString);
}

