package application.winstrategy;

import application.Player;
import gamelauncher.Game;

import java.io.Serializable;

public class DominantPlayerWin implements CheckWinStrategy, Serializable {
    private int threshold;
    public DominantPlayerWin(){
        threshold = Game.getInstance().getGameManager().getMatch().getMap().getRegionList().length/2 + 2;
    }
    public boolean checkWin(Player p){
        return (p.getRegions().size() >= threshold);
    }

    public String getMissionName(){
        return "Dominant Player";
    }

    public String getMissionDetails(){
        return "Be a dominant player and control " + threshold + " regions";
    }
}
