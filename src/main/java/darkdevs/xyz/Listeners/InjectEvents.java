package darkdevs.xyz.Listeners;

import darkdevs.xyz.Managers.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InjectEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SettingsManager.getInstance().injectPlayer(new Player[] { e.getPlayer() });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        SettingsManager.getInstance().uninjectPlayer(e.getPlayer());
    }
}

