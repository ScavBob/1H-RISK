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
import java.util.ArrayList;

public class Card implements Serializable {

    private int type;
    private String typeName;

    // Card combination(Grid layout must be in the Player class) Since each card has only type from 0 to 3.


    Card(int type){
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
    public static int tradeCards(ArrayList<Card> list,int numberOfTrades){
        if(list.size() < 3)
            return 0;
        Card c1 = null;
        Card c2 = null;
        Card c3 = null;
        boolean has = false;

        if(list.size() <=4){
            for(Card c: list){
                if(c1 == null)
                    c1 = c;
                else if(c2 == null)
                    c2 = c;
                else if (c3 == null)
                    c3 = c;
                if (c.type == 3){
                    c1 = list.get(list.indexOf(c));
                    list.remove(c1);
                    c2 = list.get(0);
                    list.remove(c2);
                    c3 = list.get(0);
                    list.remove(c3);
                    has = true;
                    break;
                }
                if(c1 == null || c2 == null || c3 == null)
                    continue;

                if(checkCombination(c1,c2,c3)){
                    has = true;
                    break;
                }

            }
        }
        else if(list.size() >= 5){
            int loc1;
            int loc2;
            int loc3;
            do{
                loc1 = (int)(Math.random() * list.size());
                do{
                    loc2 = (int)(Math.random() * list.size());
                    do{
                        loc3 = (int)(Math.random() * list.size());
                    }while(loc3 == loc2);
                }while(loc2 == loc1);
                c1 = list.get(loc1);
                c2 = list.get(loc2);
                c3 = list.get(loc3);
            }while(!checkCombination(c1,c2,c3));

            list.remove(c1);
            list.remove(c2);
            list.remove(c3);
            has = true;
        }


        if(has){
            return tradeCards(c1,c2,c3,numberOfTrades);
        }

        return 0;
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