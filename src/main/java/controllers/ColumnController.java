package controllers;

import app.Main;
import boardpackage.BoardManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class ColumnController {

    @FXML
    private VBox columnVbox;

    @FXML
    private VBox cardContainer;       //each column

    private ArrayList<smallCardController> childrenList ;

    public ColumnController() {
        childrenList = new ArrayList<>();

    }

    @FXML
    public void addCardAction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("cardHbox.fxml"));
        try {
            HBox cardInColumn = loader.load();
            smallCardController smallCard = loader.getController();
            smallCard.setParent(this);
            childrenList.add(smallCard);
            cardContainer.getChildren().add(cardInColumn);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void removeColumnAction(){
        ((HBox)columnVbox.getParent()).getChildren().remove(columnVbox);
    }

    public void removeSmallCard(smallCardController currentChild){
        smallCardController deleteThis = null;
        for(smallCardController smallCard : childrenList){
            if(currentChild==smallCard){
                deleteThis = currentChild;

            }
        }
        cardContainer.getChildren().remove(deleteThis.getHbox());
        childrenList.remove(currentChild);
    }
}

