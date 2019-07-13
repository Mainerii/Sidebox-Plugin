package maineri.sidebox.commands;

import maineri.sidebox.configs.SideboxConfig;
import maineri.sidebox.sidebox.Sidebox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SideboxManager implements CommandExecutor {

    private Sidebox sidebox;

    public SideboxManager(Sidebox sidebox) {

        this.sidebox = sidebox;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length != 1) return false;

        if(!(sender instanceof Player)) {
            sender.sendMessage(SideboxConfig.players_only);
            return true;
        }

        if(args[0].equals("show")) {
            sidebox.show((Player) sender);
            return true;
        } else if(args[0].equals("hide")) {
            sidebox.hide((Player) sender);
            return true;
        }

        return false;

    }

}
