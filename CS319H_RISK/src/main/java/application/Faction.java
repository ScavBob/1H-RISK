package application;

import java.awt.*;
import java.util.*;

public class Faction{
    private int factionID;
    private String factionName;
    private Color factionColor;

    // Player could not be seen in the project.

    private ArrayList<Player> players;
    Faction (){
        players  = new ArrayList<Player>();
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
