package boardpackage;
//Manvi Jain
//12-11-2019

import java.util.*;

public class Column {

    private Board parentBoard;
    private String id;
    private String heading;
    private String role;
    private LinkedList<Card> cards;

    public Column(String headingIn){
        heading = headingIn;
        cards = new LinkedList<>();

        parentBoard = BoardManager.get().getCurrentBoard();
        parentBoard.addColumn(this);
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
        return id;
    }
	//gets List of cards
	public LinkedList<Card> getCards(){
		return cards;
	}
    //changes the ID of a column
    public void setId(String IDIn){
        id=IDIn;
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
