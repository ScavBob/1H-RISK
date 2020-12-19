package application;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Faction implements Serializable {
    public static Faction Ottoman = new Faction(0.30,0,0,0); // +%30 starting bonus, %-0 continental bonus, %0, %0
    public static Faction Germany = new Faction(0,0,0,0); // -5 staring bonus +3 continental bonus
    public static Faction France = new Faction(0,0,0,0); // +5 starting bonus -1 continental bonus
    public static Faction Britain = new Faction(0,0,0,0); // -7 staring bonus +4 continental bonus
    public static Faction America = new Faction(0,0,0,0); //
    public static Faction Italy = new Faction(0,0,0,0); //
    public static Faction Russia = new Faction(0,0,0,0); //

    private int factionID;
    private String factionName;
    private Color factionColor;
    private double continentalBonus;
    private double startingBonus;
    private double turnBonus;
    private double tradeBonus; //TODO


    // Player could not be seen in the project.7

    Faction(double startingBonus, double continentalBonus , double turnBonus, double tradeBonus ){
        this.continentalBonus = continentalBonus;
        this.startingBonus = startingBonus;
        this.turnBonus = turnBonus;
        this.tradeBonus = tradeBonus;
    }

    Faction(){

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
