package darkdevs.xyz;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Main extends JavaPlugin {
    private static Main main;

    public void onEnable() {
        main = this;
        saveDefaultConfig();
        saveConfig();
        Bukkit.getPluginManager().registerEvents((Listener)new ConnectEvents(), (Plugin)this);
        getCommand("darkperms").setExecutor((CommandExecutor)new PermCommand());
        System.out.println(ChatColor.GREEN + "Darkperms v0.1.1 has loaded succesfully");
    }

    public void onDisable() {

    }

    public static Main getmain() {
        return main;
    }

    public ArrayList<String> getOnlinePermissions(Player player) {
        ArrayList<String> permissions = new ArrayList<>();
        if (getConfig().contains(player.getUniqueId().toString() + ".permissions"))
            permissions = (ArrayList<String>)getConfig().getStringList(player.getUniqueId().toString() + ".permissions");
        return permissions;
    }

    public ArrayList<String> getOfflinePermissions(OfflinePlayer offlinePlayer){
        ArrayList<String> permissions = new ArrayList<>();
        if (getConfig().contains(offlinePlayer.getUniqueId().toString() + ".permissions"))
            permissions = (ArrayList<String>)getConfig().getStringList(offlinePlayer.getName().toString() + ".permissions");
        return permissions;
    }
}
