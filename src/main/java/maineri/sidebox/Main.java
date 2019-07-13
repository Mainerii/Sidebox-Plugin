package maineri.sidebox;

import maineri.sidebox.commands.SideboxManager;
import maineri.sidebox.configs.PlayerConfig;
import maineri.sidebox.configs.SideboxConfig;
import maineri.sidebox.sidebox.Sidebox;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Sidebox sidebox;

    @Override
    public void onEnable() {

        SideboxConfig.loadConfig(this);
        PlayerConfig.load(this);

        if(!SideboxAPI.setup(this)) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getServer().getPluginManager().registerEvents(sidebox = new Sidebox(this), this);
        this.getCommand("sidebox").setExecutor(new SideboxManager(sidebox));

    }

    @Override
    public void onDisable() {

        if(sidebox != null) sidebox.unsetAll();

        for(Player player : this.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.getServer().getScoreboardManager().getMainScoreboard());
        }

        PlayerConfig.save(this);

    }

}
