package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
import boardpackage.Card;
import boardpackage.Column;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private ScrollPane scrollPane;

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
        BoardManager.get().setCurrentColumn(columnVbox.getColumn());

        BoardManager.get().setCurrentCard(null);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newtitle.fxml"));

        try {
            Parent popup = loader.load();
            ((NewTitleController) loader.getController()).setaClass(Card.class);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(popup));
            stage.setResizable(false);
            stage.showAndWait();

            if (BoardManager.get().getCurrentCard()!= null){
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/card.fxml"));
                CardWrapper cardBox = loader.load();
                cardContainer.getChildren().add(cardBox);
                BoardManager.get().setCurrentCard(null);

                Platform.runLater(() -> {
                    scrollPane.setHvalue(1.0);
                });
            }
        } catch (IOException e) {
            System.err.println("Failed to load fxml file.");
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

}

