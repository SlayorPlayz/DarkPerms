package darkdevs.xyz.Managers;

import java.util.ArrayList;

import darkdevs.xyz.Managers.SettingsManager;
import org.bukkit.configuration.ConfigurationSection;

public class GroupManager {
    private String name;

    private ArrayList<String> perms;

    public GroupManager(String name) {
        this.name = name;
        ConfigurationSection s = SettingsManager.getInstance().getGroupSection(name);
        if (!s.contains("perms"))
            s.set("perms", new ArrayList());
        this.perms = new ArrayList<>(s.getStringList("perms"));
    }

    public String getName() {
        return this.name;
    }

    public boolean hasPerm(String perm) {
        return this.perms.contains(perm);
    }

    public void addPerm(String paramString) {
        throw new Error("Unresolved compilation problem: \n\tThe method injectPlayer(Player...) in the type SettingsManager is not applicable for the arguments (Collection<capture#1-of ? extends Player>)\n");
    }

    public void removePerm(String paramString) {
        throw new Error("Unresolved compilation problem: \n\tThe method injectPlayer(Player...) in the type SettingsManager is not applicable for the arguments (Collection<capture#3-of ? extends Player>)\n");
    }

    public ArrayList<String> getPerms() {
        return this.perms;
    }
}

