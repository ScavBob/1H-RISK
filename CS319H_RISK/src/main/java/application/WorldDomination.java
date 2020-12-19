package application;

import game.Game;

public class WorldDomination implements CheckWinStrategy{

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
