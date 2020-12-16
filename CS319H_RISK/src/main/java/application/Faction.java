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
    
<<<<<<< HEAD:CS319H_RISK/src/main/java/Application/Faction.java
=======
    public Color getFactionColor(){return null;}
>>>>>>> 4fa0fe0596bd4f8d6b2d973543af61461f03c3a4:CS319H_RISK/src/main/java/application/Faction.java
    // Could not decide the return type so choosed void as default 
     public void getFactionBonus(){}

}
