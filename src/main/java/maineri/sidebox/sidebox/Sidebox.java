package maineri.sidebox.sidebox;

import maineri.sidebox.SideboxAPI;
import maineri.sidebox.Main;
import maineri.sidebox.configs.PlayerConfig;
import maineri.sidebox.configs.SideboxConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class Sidebox implements Listener {

    private Main plugin;
    private ScoreboardManager scoreboardManager;

    public HashMap<Player, SideboxData> data;
    public HashMap<Player, Scoreboard> scoreboards;

    public Sidebox(Main plugin) {

        this.plugin = plugin;

        data = new HashMap<>();
        scoreboards = new HashMap<>();
        scoreboardManager = plugin.getServer().getScoreboardManager();

        initAll();

        updateAll();

    }

    public void initAll() {

        plugin.getServer().getOnlinePlayers().forEach(player -> initPlayer(player));

    }

    public void unsetAll() {

        plugin.getServer().getOnlinePlayers().forEach(player -> unsetPlayer(player));
        PlayerConfig.save(plugin);

    }

    private void initPlayer(Player player) {

        boolean isClosed;

        SideboxData sideboxData = new SideboxData();
        sideboxData.setClosed(isClosed = PlayerConfig.getPlayerConfig(player));

        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        scoreboard.registerNewObjective("stats", "", "");

        scoreboards.put(player, scoreboard);
        data.put(player, sideboxData);

        if(isClosed) {
            hide(player);
        } else {
            show(player);
        }

    }

    private void unsetPlayer(Player player) {

        if(data.containsKey(player) && scoreboards.containsKey(player)) {

            PlayerConfig.setPlayerConfig(player, data.get(player).isClosed());

            data.remove(player);
            scoreboards.remove(player);

        }

    }

    public void show(Player player) {

        updatePlayer(player);

        data.get(player).setClosed(false);
        player.setScoreboard(scoreboards.get(player));

    }

    public void hide(Player player) {

        data.get(player).setClosed(true);
        player.setScoreboard(scoreboardManager.getMainScoreboard());

    }

    public void render(Player player) {

        Scoreboard scoreboard = scoreboards.get(player);
        SideboxData playerData = data.get(player);

        scoreboard.getObjective("stats").unregister();

        Objective stats = scoreboard.registerNewObjective("stats", "", "");
        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
        stats.setDisplayName(SideboxConfig.sidebox_title);

        Score[] lines = buildGUI(stats, playerData);

        int emptyRows = 0;
        for(int i = 0; i < lines.length; i++) {
            Score line = lines[i];
            if(line == null) {
                line = stats.getScore(repeat(" ", ++emptyRows));
            }
            line.setScore(lines.length - i);
        }

    }

    public Score[] buildGUI(Objective stats, SideboxData playerData) {

        int padding = SideboxConfig.sidebox_min_width;

        if(padding <= 2) {     // 2 = amount of empty lines (amount of nulls in returns array)
            padding = 3;       // +1
        }

        Score paddingLineScore = stats.getScore(repeat(" ", padding));
        Score rankScore = stats.getScore(SideboxConfig.sidebox_rank + " " + SideboxConfig.sidebox_rank_prefix + playerData.getRankName());
        Score factionScore = stats.getScore(SideboxConfig.sidebox_faction + " " + SideboxConfig.sidebox_faction_prefix + playerData.getFactionName());
        Score roleScore = stats.getScore(SideboxConfig.sidebox_faction_role + " " + SideboxConfig.sidebox_faction_role_prefix + playerData.getFactionRole());
        Score moneyScore = stats.getScore(SideboxConfig.sidebox_money + " " + SideboxConfig.sidebox_money_prefix + playerData.getMoneyAmount());
        Score bottomScore = stats.getScore(SideboxConfig.sidebox_bottom_link);

        System.out.println(playerData.getRankName() + " " + playerData.getMoneyAmount());

        return new Score[] {
                paddingLineScore,
                rankScore,
                null,
                factionScore,
                roleScore,
                null,
                moneyScore,
                null,
                bottomScore
        };

    }

    public void updatePlayer(Player player) {

        if(data.containsKey(player) && scoreboards.containsKey(player)) {

            if(SideboxAPI.update(player, data.get(player))) render(player);

        }

    }

    public void updateAll() {

        int refreshRate = SideboxConfig.sidebox_refresh_rate;

        if(refreshRate < 1) {
            plugin.getLogger().warning("Refresh rate less than 1 given, defaulting to 20.");
            refreshRate = 20;
        }

        // Update every 20 ticks (1 second)
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                plugin.getServer().getOnlinePlayers().forEach(player -> updatePlayer(player));

            }
        }, 0L, refreshRate);

    }

    private String repeat(String string, int times) {

        String result = "";
        for (int i = 0; i < times; i++) {
            result += string;
        }
        return result;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        initPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        unsetPlayer(event.getPlayer());
    }

}
