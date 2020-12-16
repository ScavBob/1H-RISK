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
    
    Card(Region region,int type){
        this.region = region;
        this.type = type;
    }
    public int getType(){
        return type;
    }




}