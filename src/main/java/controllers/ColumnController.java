package controllers;

import boardpackage.BoardManager;
import boardpackage.Card;
import boardpackage.Column;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;

import java.io.IOException;


public class ColumnController {

    @FXML
    private ColumnWrapper columnVbox;

    @FXML
    private TextField titleText;
    @FXML
    private VBox cardContainer;

    @FXML
    public void initialize(){
        Column column = BoardManager.get().getCurrentColumn();
        columnVbox.setColumn(column);

        for (Card card : column.getCards()){
            BoardManager.get().setCurrentCard(card);
            CardWrapper cardBox;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/card.fxml"));
                cardBox = loader.load();
                cardContainer.getChildren().add(cardBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BoardManager.get().setCurrentCard(null);

        // Updates the column object to notify about
        titleText.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
                columnVbox.getColumn().setTitle(titleText.getText());
        });

        refresh();
    }

    @FXML
    public void addCardAction() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/cardpopup.fxml"));
            BoardManager.get().setCurrentCard(null);
            loader.load();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void removeColumnAction(){
        columnVbox.getColumn().delete();
        ((HBox)columnVbox.getParent()).getChildren().remove(columnVbox);
    }

    public void refresh(){
        titleText.setText(columnVbox.getColumn().getTitle());
    }

/*
    public void addSmallCard(String s) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/card.fxml"));

        try {
            HBox cardInColumn = loader.load();
            CardController smallCard = loader.getController();

            //smallCard.setText(s);
            cardContainer.getChildren().add(cardInColumn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

