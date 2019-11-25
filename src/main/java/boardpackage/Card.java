//Manvi Jain
//25-11-2019
import java.util.*;
public class Card {

    private String ID;
    private String Text = "";
    ArrayList<String> members;

   public Card(String IDIn){
        ID=IDIn;
       members = new ArrayList<>();
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

    //to assign a card to certain members
    public void addMember(String name){
       members.add(name);
    }

    //returns the arraylist of the members assigned to card
    public ArrayList<String> getMembers(){
       return members;
    }

}
