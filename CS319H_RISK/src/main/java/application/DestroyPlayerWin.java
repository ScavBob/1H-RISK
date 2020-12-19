package application;

import game.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class DestroyPlayerWin implements CheckWinStrategy, Serializable {
    private Player target;
    static private ArrayList<Player> used = new ArrayList<Player>();

    DestroyPlayerWin(Player p){

        ArrayList<Player> list = Game.getInstance().getGameManager().getMatch().getPlayers();
        do {
            int number = (int) (Math.random() * list.size());
            target = list.get(number);
        }while(target != p && !used.contains(target));

        used.add(target);
    }

    public boolean checkWin(Player p){

        return target.getRegions().size() == 0;
    }

    public String getMissionName(){
        return "Destroy Player";
    }

    public String getMissionDetails(){
        return "Destroy the player with the " + target.getColor() + " color.";
    }
}
