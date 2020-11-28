package darkdevs.xyz;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.ServerOperator;

public class CustomPermissionBase extends PermissibleBase {
    private Player player;

    public CustomPermissionBase(Player player) {
        super((ServerOperator)player);
        this.player = player;
    }

    public boolean hasPermission(String inName) {
        ArrayList<String> permissions = Main.getmain().getOnlinePermissions(this.player);
        if (Arrays.<String>asList(new String[] { "bukkit.broadcast.user", "bukkit.broadcast", "bukkit.command.version", "" }).contains(inName))
            return true;
        if (permissions.contains("-" + inName))
            return false;
        if (permissions.contains("*") || this.player.isOp())
            return true;
        return permissions.contains(inName);
    }
}

