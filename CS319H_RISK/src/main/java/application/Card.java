package application;
/*
    * Type of the cards:
     0 = Infantry
     1 = Cavalry
     2 = Artillery
     3 = Wild       (Wild cards depict infantry, cavalry and artillery pieces. 
                    Because these cards have all three symbols, they can match with any two other cards to form a set.) 
    */

import java.io.Serializable;

public class Card implements Serializable {
    private Region region;
    private int type;
    private String typeName;

    // Card combination(Grid layout must be in the Player class) Since each card has only type from 0 to 3.

    Card(Region region,int type){
        this.region = region;
        this.type = type;
        setTypeName(this.type);
    }
    public int getType(){
        return type;
    }
    public String getTypeName(){
        return typeName;
    }
    /**
     * Sets the typename of each card by using the int coded type.
     *
     * @param  type  int random varaible of the card type
     */
    
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

    public static int tradeCards(Card card1, Card card2, Card card3, int numberOfTrades){
        if(!checkCombination(card1,card2,card3))
            return -1;
        int extra = 0;
        if(numberOfTrades < 5)
            extra = 4 + (2*numberOfTrades);
        if(numberOfTrades == 5)
            extra = 15;
        if(numberOfTrades > 5)
            extra = 15 + (5*numberOfTrades);
        return extra;
    }

    private static boolean checkCombination(Card card1, Card card2, Card card3){
        if(card1.type == 3 || card2.type==3 || card3.type ==3)
            return true;
        if(card1.type == card2.type && card2.type == card3.type)
            return true;
        if(card1.type != card2.type && (card3.type != card1.type && card3.type != card2.type))
            return true;
        return false;
    }


}