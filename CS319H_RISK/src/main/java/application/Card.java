package application;
/*
    * Type of the cards:
     0 = Infantry
     1 = Cavalry
     2 = Artillery
     3 = Wild       (Wild cards depict infantry, cavalry and artillery pieces. 
                    Because these cards have all three symbols, they can match with any two other cards to form a set.) 
    */
    
public class Card {
    private Region region;
    private int type;
    private String typeName;
    // Card combination(Grid layout must be in the Player class) Since each card has only type from 0 to 3.

    Card(Region region,int type){
        this.region = region;
        this.type = type;
    }
    public int getType(){
        return type;
    }
    public String getTypeName(){
        return typeName;
    }
    private void setTypeName(int type){
        switch(type){
            case 0:
                typeName = "Infantry";
                break;
            case 1:
                typeName = "Cavalry";
                break;
            case 2:
                typeName = "Artillery";
                break;
            case 3:
                typeName = "Wild";
                break;
        }
    }


}