package boardpackage;
//Manvi Jain
//12-11-2019


public class Card {

    private String ID;
    private String Text = "";

   public Card(String IDIn){
        ID=IDIn;
    }

    //Changes the text of a card
    //initially the text is ""
    public void editText(String TextIn){
       Text = TextIn;
    }

    //gets the text of a card
    public String getText(){
       return Text;
    }

    //gets the ID of a card
    public String getID(){
        return ID;
    }

    //changes the ID of the card
    public void setID(String IDIn){
       ID=IDIn;
    }


}
