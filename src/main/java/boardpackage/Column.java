//Manvi Jain
//12-11-2019

import java.util.*;

public class Column {

    private String ID;
    private String heading;
   // private ArrayList<Card> cards;
    private LinkedList<Card> cards;

    public Column(String headingIn, String IDIn){
        heading = headingIn;
        ID = IDIn;
        cards = new LinkedList<>();
    }

    //To add cards to the column
    public void addCard(Card card){
        cards.add(card);
    }

    //To remove cards from a column
    public void removeCard(Card card){
        cards.remove(card);
    }

    //To move cards within a column from one position to another
    public void moveCard(Card card, int finalIndex){
        cards.remove(card);
        //add the element to that index of hte linkedlist and moving the remaining elements up by 1
        cards.add(finalIndex,card);
    }

    //gets the ID of a column
    public String getID(){
        return ID;
    }

    //changes the ID of a column
    public void setID(String IDIn){
        ID=IDIn;
    }

    //gets the heading of the column
    public String getHeading(){
        return heading;
    }

    //changes the heading of our column
    public void setHeading(String headingIn){
        heading = headingIn;
    }


}
