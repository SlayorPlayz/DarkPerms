package darkdevs.xyz;

import darkdevs.xyz.Listeners.InjectEvents;
import darkdevs.xyz.Managers.CommandManager;
import darkdevs.xyz.Managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        Bukkit.getServer().getLogger().info("DarkPerms Enabled.");
        SettingsManager.getInstance().setup((Plugin)this);
        getCommand("darkperms").setExecutor(new CommandManager());
        Bukkit.getServer().getPluginManager().registerEvents(new InjectEvents(), (Plugin)this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    public void onDisable() {
        Bukkit.getServer().getLogger().info("DarkPerms Disabled");
    }
}

