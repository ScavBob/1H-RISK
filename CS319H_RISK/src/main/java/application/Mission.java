package application;

import java.io.Serializable;
import java.lang.String;

public class Mission implements Serializable {
    private int missionID;

    private CheckWinStrategy strategy;

    public boolean checkWin(Player p){
        return strategy.checkWin(p);
    }

    public String getMissionName(){
        return strategy.getMissionName();
    }

    public String getMissionDetails(){
        return strategy.getMissionDetails();
    }
}