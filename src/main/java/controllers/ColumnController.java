package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
import boardpackage.Card;
import boardpackage.Column;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


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
    private ChoiceBox<String> cardRoll;

    ObservableList<String> availableChoices = FXCollections.observableArrayList("To do", "Doing","Done");;

    @FXML
    public void initialize(){
        loadDataChoiceBox();
        Column column = BoardManager.get().getCurrentColumn();
        columnVbox.setColumn(column);
        cardRoll = new ChoiceBox<>();   //create a choice box for each added column
        for (Card card : column.getCards()){
            BoardManager.get().setCurrentCard(card);
            CardWrapper cardBox;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/card.fxml"));
                cardBox = loader.load();
                cardBox.setOnDragDetected(event -> {
                    BoardManager.get().setCurrentCard(cardBox.getCard());
                    System.out.println("picked card " + cardBox.getCard().getTitle());
                });
                cardBox.setOnDragDropped(event -> {
                    cardContainer.getChildren().remove(cardBox);
                });
                cardContainer.getChildren().add(cardBox);
                setDragCardProperties(cardBox);
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
                //dragCard(cardBox);
                BoardManager.get().setCurrentCard(null);

                Platform.runLater(() -> {
                    scrollPane.setVvalue(1.0);
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

    private void loadDataChoiceBox(){
        availableChoices.removeAll(availableChoices);
        String ToDo = "To Do";
        String doing = "Doing";
        String done = "done";
        availableChoices.addAll(ToDo , doing , done);
        cardRoll.getItems().addAll(availableChoices);
        cardRoll.setValue("To Do");
    }

    public void setDragCardProperties(CardWrapper cardWrapper) {
            VBox mainVBox = (VBox) cardWrapper.getChildren().get(0);

            // the blue stripe
            VBox cardhead = (VBox) mainVBox.getChildren().get(0);

            /*// source
            cardWrapper.setOnDragDone(event -> {
                Dragboard db = event.getDragboard();
                db.setContent(new ClipboardContent());
                event.consume();
            });

            // target
            cardWrapper.setOnDragExited(event -> {
                if (event.getTransferMode() == TransferMode.MOVE){}
                    event.consume();

            });*/

            // source
            cardhead.setOnDragDetected(event -> {
                System.out.println("DRAG DETECTED FOR " + event.getSource().getClass().getSimpleName());
                Dragboard db = cardWrapper.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(cardWrapper.toString());
                db.setContent(content);
                event.consume();
            });

            // target
            cardhead.setOnDragOver(event -> {
                if (event.getGestureTarget() != cardWrapper && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            // target
            cardhead.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    success = true;
                    int indexForInsertion = cardContainer.getChildren().indexOf(cardWrapper);
                    CardWrapper cardW = (CardWrapper) event.getGestureSource();
                    // if it's the same column
                    if (cardContainer.getChildren().contains(cardW)){
                        cardContainer.getChildren().remove(cardW);
                    }
                    cardContainer.getChildren().add(indexForInsertion, cardW);
                }
                event.setDropCompleted(success);
                event.consume();
            });
/*
            cardWrapper.setOnDragDropped(event -> {
                System.out.println("DRAG DROPPED");
/*                boolean finish = false;
                if (event.getDragboard().hasString()) {
                    Parent start = (Parent) event.getGestureSource();
                    Parent end = (Parent) event.getGestureTarget();
                    int startingPoint = cardContainer.getChildren().indexOf(start);
                    int endingPoint = cardContainer.getChildren().indexOf(card);
                    if (startingPoint >= 0 && endingPoint >= 0) {
                        ObservableList<Node> allCards = cardContainer.getChildren();
                        ArrayList<Node> allSwappedCards = new ArrayList<>(allCards);
                        Node swapped = allSwappedCards.get(startingPoint);
                        allSwappedCards.set(startingPoint, allSwappedCards.get(endingPoint));
                        allSwappedCards.set(endingPoint, swapped);
                        cardContainer.getChildren().clear();
                        for (Node node : allSwappedCards) {
                            cardContainer.getChildren().add(node);
                        }
                    }/*
                        else {
                             //else its not in our VBOX
                             LinkedList<Column> listOfColumns = BoardManager.get().getCurrentBoard().getColumns();
                             for(Column col : listOfColumns){
                                if (col.getContainsTaskRoot(end) != null){
                                    //do the swap
                                    ObservableList<Node> listOfColsToBeSwapped = boxOfTask.getChildren();
                                    ArrayList<Node> newListOfCols = new ArrayList<>(listOfColsToBeSwapped);
                                    newListOfCols.remove(end);
                                    newListOfCols.add(start);

                                    boxOfTask.getChildren().clear();
                                    ArrayList<Node> cleard = new ArrayList<>();
                                    for( Node n : newListOfCols){
                                        if(!(cleard.contains(n))){
                                            cleard.add(n);
                                        }
                                    }
                                    newListOfCols = cleard;

                                    for (Node node : newListOfCols ){
                                        boxOfTask.getChildren().add(node);
                                    }

                                    //to do : get source's boxOfTask and add Target into it.

                                    ObservableList<Node> listOfColsTarget = col.getTaskBox().getChildren();
                                    ArrayList<Node> targetListOfCols = new ArrayList<>(listOfColsTarget);
                                    targetListOfCols.add(end);

                                    (col.getTaskBox()).getChildren().clear();
                                    ArrayList<Node> clearTargetd = new ArrayList<>();
                                    for( Node n : newListOfCols){
                                        if(!(cleard.contains(n))){
                                            cleard.add(n);
                                        }
                                    }
                                    newListOfCols = clearTargetd;

                                    for (Node node : targetListOfCols ){
                                        col.getTaskBox().getChildren().add(node);
                                    }

                                    break;
                                }
                            }

                        }
                        finish = true;

                    event.setDropCompleted(finish);
                    event.consume();
                }
            });*/

    }
}

