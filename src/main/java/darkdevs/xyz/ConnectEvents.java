package darkdevs.xyz;

import java.lang.reflect.Field;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ConnectEvents implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        try {
            Field f = HumanEntity.class.getDeclaredField("perm");
            f.setAccessible(true);
            f.set(e.getPlayer(), new CustomPermissionBase(e.getPlayer()));
            f.setAccessible(false);
        } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }


}

