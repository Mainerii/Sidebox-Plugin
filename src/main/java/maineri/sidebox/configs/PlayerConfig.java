package maineri.sidebox.configs;

import maineri.sidebox.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerConfig {

    private static YamlConfiguration configuration = new YamlConfiguration();

    public static void setPlayerConfig(Player player, boolean isClosed) {

        configuration.set("player." + player.getName() + ".closed", isClosed);

    }

    public static boolean getPlayerConfig(Player player) {

        return configuration.getBoolean("player." + player.getName() + ".closed", true);

    }

    public static void load(Main plugin) {

        File configFile = new File(plugin.getDataFolder(), "players.yml");

        if(!configFile.exists()) {

            plugin.getLogger().info("No players.yml found, creating one...");

        } else {

            try {

                configuration.load(configFile);

            } catch (InvalidConfigurationException | IOException e) {

                plugin.getLogger().severe("Config file players.yml couldn't be read from!");
                e.printStackTrace();

            }

        }

    }

    public static void save(Main plugin) {

        try {

            configuration.save(new File(plugin.getDataFolder(), "players.yml"));

        } catch (IOException e) {

            plugin.getLogger().severe("Config file players.yml couldn't be saved in to!");
            e.printStackTrace();

        }

    }

}
