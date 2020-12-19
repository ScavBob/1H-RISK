package application;

import game.Game;

import java.io.Serializable;

public class DominantPlayerWin implements CheckWinStrategy, Serializable {
    private int threshold;
    DominantPlayerWin(){
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
