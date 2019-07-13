package maineri.sidebox;

import maineri.sidebox.sidebox.SideboxData;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class SideboxAPI {

    private static Economy economy;
    private static Permission permissions;

    public static boolean setup(Main plugin) {

        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Vault plugin not found! Disabling AdmiralMC-Sidebox.");
            return false;
        }

        if(setupEconomy(plugin) && setupPermissions(plugin)) {
            return true;
        }

        return false;

    }

    private static boolean setupEconomy(Main plugin) {

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            plugin.getLogger().severe("No economy plugin found! Disabling AdmiralMC-Sidebox.");
            return false;
        }

        economy = rsp.getProvider();

        return true;

    }

    private static boolean setupPermissions(Main plugin) {

        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);

        if (rsp == null) {
            plugin.getLogger().severe("No permission plugin found! Disabling AdmiralMC-Sidebox.");
            return false;
        }

        permissions = rsp.getProvider();

        return true;

    }

    public static boolean update(Player player, SideboxData data) {

        boolean rankUpdated = data.setRankName(permissions.getPrimaryGroup(player));
        boolean balanceUpdated = data.setMoneyAmount("" + (Math.floor(economy.getBalance(player) * 100)) / 100);

        FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);
        boolean factionUpdated, roleUpdated;
        if(factionPlayer.hasFaction()) {
            factionUpdated = data.setFactionName(factionPlayer.getFaction().getTag());
            roleUpdated = data.setFactionRole(factionPlayer.getRole().name());
        } else {
            factionUpdated = data.setFactionName(null);
            roleUpdated = data.setFactionRole(null);
        }

        return rankUpdated || balanceUpdated || factionUpdated || roleUpdated;

    }

}
