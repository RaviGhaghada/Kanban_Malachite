package boardpackage;

public class Card {


    private Column parentColumn = null;
    private String id;
    private String title = "";
    private String text = "";
    private String storypoints = "";

    public Card(){
        parentColumn = BoardManager.get().getCurrentColumn();
        parentColumn.addCard(this);
    }

    //Changes the text of a card
    //initially the text is ""
    public void setText(String textIn){
        text = textIn;
    }

    //gets the text of a card
    public String getText(){
        return text;
    }

    //gets the ID of a card
    public String getId(){
        return id;
    }

    //changes the ID of the card
    public void setID(String IDIn){
        id=IDIn;
    }


}