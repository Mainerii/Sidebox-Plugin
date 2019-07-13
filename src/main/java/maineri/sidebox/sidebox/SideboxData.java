package maineri.sidebox.sidebox;

import maineri.sidebox.configs.SideboxConfig;

public class SideboxData {

    private boolean closed = false;

    private String rankName = "";
    private String factionName = "";
    private String factionRole = "";
    private String moneyAmount = "";

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean setRankName(String rankName) {

        if(rankName == null || rankName.isEmpty()) {
            rankName = " ";
        }

        rankName = rankName.substring(0,1).toUpperCase() + rankName.substring(1).toLowerCase();

        boolean updated = ! this.rankName.equals(rankName);
        this.rankName = rankName;
        return updated;

    }

    public String getRankName() {
        return rankName;
    }

    public boolean setFactionName(String factionName) {

        if(factionName == null || factionName.isEmpty()) {
            factionName = SideboxConfig.sidebox_no_faction;
        } else {
            factionName = factionName.substring(0,1).toUpperCase() + factionName.substring(1).toLowerCase();
        }

        boolean updated = ! this.factionName.equals(factionName);
        this.factionName = factionName;
        return updated;

    }

    public String getFactionName() {
        return factionName;
    }

    public boolean setFactionRole(String factionRole) {

        if(factionRole == null || factionRole.isEmpty()) {
            factionRole = SideboxConfig.sidebox_no_faction_role;
        } else {
            factionRole = factionRole.substring(0,1).toUpperCase() + factionRole.substring(1).toLowerCase();
        }

        boolean updated = ! this.factionRole.equals(factionRole);
        this.factionRole = factionRole;
        return updated;

    }

    public String getFactionRole() {
        return factionRole;
    }

    public boolean setMoneyAmount(String moneyAmount) {

        boolean updated = ! this.moneyAmount.equals(moneyAmount);
        this.moneyAmount = moneyAmount;
        return updated;

    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

}
