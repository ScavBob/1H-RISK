package application;

import java.awt.*;

public class Faction{
    private int factionID;
    private String factionName;
    private Color factionColor;
    private Arraylist <Player> players;
    Faction (){
        players  = new ArrayList <Player> ();
    }
    public Color getFactionColor(){
        
        return factionColor;

    }
    // Faction name is the symbl of the function, getSymbol gives the name of the function. 
    public String getFactionSymbol(){
        return factionName;
    }
    

    public Color getFactionColor(){
        return null;
    }

    // Could not decide the return type so choosed void as default 
     public void getFactionBonus(){}

}
