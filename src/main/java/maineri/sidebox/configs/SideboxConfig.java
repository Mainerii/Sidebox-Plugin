package maineri.sidebox.configs;

import maineri.sidebox.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SideboxConfig {

    public static String players_only = "Only players can run this command!";
    public static String sidebox_title = "§3§l->§b §lServerName §3§l<-";
    public static String sidebox_faction = "§aFaction:";
    public static String sidebox_faction_prefix = "§7";
    public static String sidebox_no_faction = "-";
    public static String sidebox_faction_role = "§aRole:";
    public static String sidebox_faction_role_prefix = "§7";
    public static String sidebox_no_faction_role = "-";
    public static String sidebox_rank = "§aRank:";
    public static String sidebox_rank_prefix = "§7";
    public static String sidebox_money = "§aBalance:";
    public static String sidebox_money_prefix = "§7$";
    public static String sidebox_bottom_link = "§6§lexample.com";

    public static int sidebox_refresh_rate = 20;
    public static int sidebox_min_width = 20;

    private static YamlConfiguration configuration = new YamlConfiguration();

    private static void saveConfig(Main plugin, File configFile) {

        try {

            configuration.set("messages.players-only", players_only);
            configuration.set("sidebox.refresh-rate", sidebox_refresh_rate);
            configuration.set("sidebox.min-width", sidebox_min_width);
            configuration.set("sidebox.top-title", sidebox_title);
            configuration.set("sidebox.faction-title", sidebox_faction);
            configuration.set("sidebox.faction-prefix", sidebox_faction_prefix);
            configuration.set("sidebox.no-faction", sidebox_no_faction);
            configuration.set("sidebox.faction-role_title", sidebox_faction_role);
            configuration.set("sidebox.faction-role-prefix", sidebox_faction_role_prefix);
            configuration.set("sidebox.no-faction-role", sidebox_no_faction_role);
            configuration.set("sidebox.rank-title", sidebox_rank);
            configuration.set("sidebox.rank-prefix", sidebox_rank_prefix);
            configuration.set("sidebox.balance-title", sidebox_money);
            configuration.set("sidebox.balance-prefix", sidebox_money_prefix);
            configuration.set("sidebox.bottom-title", sidebox_bottom_link);

            configuration.save(configFile);

        } catch (IOException e) {

            plugin.getLogger().severe("Config file sidebox.yml couldn't be saved in to!");
            e.printStackTrace();

        }

    }

    public static void loadConfig(Main plugin) {

        File configFile = new File(plugin.getDataFolder(), "sidebox.yml");

        if(!configFile.exists()) {

            plugin.getLogger().info("No messages.yml found, creating one...");

        } else {

            try {

                configuration.load(configFile);

                players_only = configuration.getString("messages.players-only", players_only);
                sidebox_refresh_rate = configuration.getInt("sidebox.refresh-rate", sidebox_refresh_rate);
                sidebox_min_width = configuration.getInt("sidebox.min-width", sidebox_min_width);
                sidebox_title = configuration.getString("sidebox.top-title", sidebox_title);
                sidebox_faction = configuration.getString("sidebox.faction-title", sidebox_faction);
                sidebox_faction_prefix = configuration.getString("sidebox.faction-prefix", sidebox_faction_prefix);
                sidebox_no_faction = configuration.getString("sidebox.no-faction", sidebox_no_faction);
                sidebox_faction_role = configuration.getString("sidebox.faction-role-title", sidebox_faction_role);
                sidebox_faction_role_prefix = configuration.getString("sidebox.faction-role-prefix", sidebox_faction_role_prefix);
                sidebox_no_faction_role = configuration.getString("sidebox.no-faction-role", sidebox_no_faction_role);
                sidebox_rank = configuration.getString("sidebox.rank-title", sidebox_rank);
                sidebox_rank_prefix = configuration.getString("sidebox.rank-prefix", sidebox_rank_prefix);
                sidebox_money = configuration.getString("sidebox.balance-title", sidebox_money);
                sidebox_money_prefix = configuration.getString("sidebox.balance-prefix", sidebox_money_prefix);
                sidebox_bottom_link = configuration.getString("sidebox.bottom-title", sidebox_bottom_link);

            } catch (InvalidConfigurationException | IOException e) {

                plugin.getLogger().severe("Config file sidebox.yml couldn't be read from!");
                e.printStackTrace();

            }

        }

        saveConfig(plugin, configFile);

    }

}
