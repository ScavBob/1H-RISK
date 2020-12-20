package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Faction implements Serializable {
    public static Faction Ottoman = new Faction("Ottoman", 1,0, 0.40, 0, 0.10, 1, -1);      // +%30 starting bonus, %-0 continental bonus, %0, %0
    public static Faction Germany = new Faction("German", 2,0.20, 0, 0.10, 0, 0, 0);        // -5 staring bonus +3 continental bonus
    public static Faction France = new Faction("French", 3, 0, 0, 0.30, 0.05, 0, 0);        // +5 starting bonus -1 continental bonus
    public static Faction Britain = new Faction("British", 4, 0.20, -0.10, 0.10, 0, 1, -1); // -7 staring bonus +4 continental bonus
    public static Faction America = new Faction("American", 5, 0, 0.30, -0.10, 0.10, 0, 0); //
    public static Faction Italy = new Faction("Italian", 6,0.10, 0, 0, 0.40, 0, 0);         //
    public static Faction Russia = new Faction("Russian", 7, 0.40, 0, 0.10, 0, -2, 2);      //

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

    public Faction(String factionName, int factionID, double continentalBonus, double startingBonus, double turnBonus, double tradeBonus, int attackDiceBonus, int defenceDiceBonus) {
        this.factionName = factionName;
        this.factionID = factionID;
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

    public int getFactionID(){return factionID;}

    public Media getFactionBattleMusic()
    {
        String musicPath = getClass().getResource("/musics/battle/factionMusic" + factionID + ".mp3").toExternalForm();
        Media music = new javafx.scene.media.Media(musicPath);
        return music;
    }

}
