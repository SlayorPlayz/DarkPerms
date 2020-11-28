package darkdevs.xyz.Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
    private static SettingsManager instance = new SettingsManager();

    private Plugin p;

    private FileConfiguration config;

    private File cfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    private HashMap<String, PermissionAttachment> attachments = new HashMap<>();

    private ArrayList<GroupManager> groups = new ArrayList<>();

    public void setup(Plugin p) {
        this.p = p;
        if (!p.getDataFolder().exists())
            p.getDataFolder().mkdir();
        this.cfile = new File(p.getDataFolder(), "perms.yml");
        if (!this.cfile.exists())
            try {
                this.cfile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.cfile);
        if (!this.config.contains("groups"))
            this.config.createSection("groups");
        setupGroups();
    }

    public void addPerm(String player, String perm) {
        player = player.toLowerCase();
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null) {
            injectPlayer(new Player[] { p });
            ((PermissionAttachment)this.attachments.get(p.getName())).setPermission(perm, true);
        }
        List<String> perms = getPerms(player);
        perms.add(perm);
        this.config.set("user." + player + ".perms", perm);
        save();
    }

    public void removePerm(String player, String perm) {
        player = player.toLowerCase();
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null) {
            injectPlayer(new Player[] { p });
            ((PermissionAttachment)this.attachments.get(p.getName())).setPermission(perm, false);
        }
        List<String> perms = getPerms(player);
        perms.remove(perm);
        this.config.set("user." + player + ".perms", perm);
        save();
    }

    public List<String> getPerms(String player) {
        player = player.toLowerCase();
        if (!this.config.contains("user." + player + ".perms"))
            this.config.set("user." + player + ".perms", new ArrayList());
        return this.config.getStringList("user." + player + ".perms");
    }

    public void addGroup(String player, String g) {
        player = player.toLowerCase();
        List<String> groups = getGroups(player);
        groups.add(g);
        this.config.set("user." + player + ".groups", groups);
        save();
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null)
            injectPlayer(new Player[] { p });
    }

    public void removeGroup(String player, String g) {
        player = player.toLowerCase();
        List<String> groups = getGroups(player);
        groups.remove(g);
        this.config.set("user." + player + ".groups", groups);
        save();
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null)
            injectPlayer(new Player[] { p });
    }

    public List<String> getGroups(String player) {
        player = player.toLowerCase();
        if (!this.config.contains("user." + player + ".groups"))
            this.config.set("user." + player + ".groups", new ArrayList());
        return this.config.getStringList("user." + player + ".groups");
    }

    public void createGroup(String group) {
        this.config.getConfigurationSection("groups").set(String.valueOf(group) + ".perms", new ArrayList());
        save();
        setupGroups();
    }

    private void setupGroups() {
        this.groups.clear();
        for (String groupName : this.config.getConfigurationSection("groups").getKeys(false))
            this.groups.add(new GroupManager(groupName));
    }

    public void injectPlayer(Player... pl) {
        byte b;
        int i;
        Player[] arrayOfPlayer;
        for (i = (arrayOfPlayer = pl).length, b = 0; b < i; ) {
            Player p = arrayOfPlayer[b];
            if (this.attachments.get(p.getName()) == null)
                this.attachments.put(p.getName(), p.addAttachment(getPlugin()));
            for (Map.Entry<String, Boolean> perm : (Iterable<Map.Entry<String, Boolean>>)((PermissionAttachment)this.attachments.get(p.getName())).getPermissions().entrySet()) {
                if (getPerms(p.getName()).contains(perm.getKey())) {
                    ((PermissionAttachment)this.attachments.get(p.getName())).setPermission(perm.getKey(), true);
                    continue;
                }
                ((PermissionAttachment)this.attachments.get(p.getName())).setPermission(perm.getKey(), false);
            }
            if (!this.config.contains("user." + p.getName().toLowerCase() + ".groups"))
                this.config.set("user." + p.getName().toLowerCase() + ".groups", new ArrayList());
            for (String gName : this.config.getStringList("user." + p.getName().toLowerCase() + ".groups")) {
                for (GroupManager g : this.groups) {
                    if (g.getName().equals(gName))
                        for (String perm : g.getPerms()) {
                            System.out.println("Injecting " + perm + " from " + gName);
                            ((PermissionAttachment)this.attachments.get(p.getName())).setPermission(perm, true);
                        }
                }
            }
            b++;
        }
    }

    public void uninjectPlayer(Player pl) {
        if (this.attachments.get(pl.getName()) == null)
            return;
        pl.removeAttachment(this.attachments.get(pl.getName()));
        this.attachments.remove(pl.getName());
    }

    public GroupManager getGroup(String name) {
        for (GroupManager g : this.groups) {
            if (g.getName().equals(name))
                return g;
        }
        return null;
    }

    public ConfigurationSection getGroupSection(String name) {
        return this.config.getConfigurationSection("groups." + name);
    }

    public void save() {
        try {
            this.config.save(this.cfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Plugin getPlugin() {
        return this.p;
    }
}

