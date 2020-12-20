package application.winstrategy;

import application.Player;

public interface CheckWinStrategy {

    public boolean checkWin(Player p);
    public String getMissionName();
    public String getMissionDetails();
}
