package application;

import game.Game;

import java.util.ArrayList;

public class TwoContinentWin implements CheckWinStrategy{
    private int continent1;
    private int continent2;
    private String continent1Name;
    private String continent2Name;

    TwoContinentWin(){

        String[] names = Game.getInstance().getGameManager().getMatch().getMap().getContinentNames();
        continent1 = (int)(Math.random() * names.length);
        do{
            continent2 = (int)(Math.random()*names.length);
        }while(continent1 != continent2);
        continent1Name = names[continent1];
        continent2Name = names[continent2];
    }
    public boolean checkWin(Player p){
        boolean [] list = p.hasContinents();
        if(list[continent1] && list[continent2])
            return true;
        return false;
    }
    public String getMissionName(){
        return "Control two continents";
    }

    public String getMissionDetails(){
        return  "Control all of " + continent1Name + " and " + continent2Name;
    }
}
