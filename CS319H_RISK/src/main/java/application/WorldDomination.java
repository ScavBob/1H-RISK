package application;

import game.Game;

import java.io.Serializable;

public class WorldDomination implements CheckWinStrategy, Serializable {

    WorldDomination(){

    }

    public boolean checkWin(Player p){
        Region[] list = Game.getInstance().getGameManager().getMatch().getMap().getRegionList();

        for(Region r: list){
            if(r.getOwner() != p)
                return false;
        }

        return true;
    }

    public String getMissionName(){
        return "World Domination";
    }

    public String getMissionDetails(){
        return "Control all the regions!";
    }
}
