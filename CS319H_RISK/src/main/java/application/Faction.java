package application;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Faction implements Serializable {
    public static Faction Ottoman = new Faction("Ottoman", 0.30, 0, 0, 0, 0, 0); // +%30 starting bonus, %-0 continental bonus, %0, %0
    public static Faction Germany = new Faction("Germany", 0, 0, 0, 0, 0, 0); // -5 staring bonus +3 continental bonus
    public static Faction France = new Faction("France", 0, 0, 0, 0, 0, 0); // +5 starting bonus -1 continental bonus
    public static Faction Britain = new Faction("Britain", 0, 0, 0, 0, 0, 0); // -7 staring bonus +4 continental bonus
    public static Faction America = new Faction("America", 0, 0, 0, 0, 0, 0); //
    public static Faction Italy = new Faction("Italy", 0, 0, 0, 0, 0, 0); //
    public static Faction Russia = new Faction("Russia", 0, 0, 0, 0, 0, 0); //

    private int factionID;
    private String factionName;
    private double continentalBonus;
    private double startingBonus;
    private double turnBonus;
    private double tradeBonus; //TODO
    private int attackDiceBonus;
    private int defenceDiceBonus;

    public String getFactionName() {
        return factionName;
    }

    public int getAttackDiceBonus() {
        return attackDiceBonus;
    }

    public int getDefenceDiceBonus() {
        return defenceDiceBonus;
    }

    public Faction(String factionName, double continentalBonus, double startingBonus, double turnBonus, double tradeBonus, int attackDiceBonus, int defenceDiceBonus) {
        this.factionName = factionName;
        this.continentalBonus = continentalBonus;
        this.startingBonus = startingBonus;
        this.turnBonus = turnBonus;
        this.tradeBonus = tradeBonus;
        this.attackDiceBonus = attackDiceBonus;
        this.defenceDiceBonus = defenceDiceBonus;
    }

    public double getContinentalBonus(){
        return continentalBonus;
    }

    public double getStartingBonus(){
        return startingBonus;
    }

    public double getTurnBonus(){
        return turnBonus;
    }

    public double getTradeBonus(){
        return tradeBonus;
    }



    public Color getFactionColor(){

        return factionColor;

    }
    // Faction name is the symbl of the function, getSymbol gives the name of the function. 
    public String getFactionSymbol(){
        return factionName;
    }

    // Could not decide the return type so choosed void as default 
     public void getFactionBonus(){}

}
