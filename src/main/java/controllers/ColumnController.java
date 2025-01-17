package controllers;

import boardpackage.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;
import java.io.IOException;

/**
 * Controller for Mello Columns.
 * This class represents each column on the board.
 * Users can manage columns with actions such as creating, renaming, deleting,
 * assigning roles and adding cards.

 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */

public class ColumnController {

    //The VBox for each column with column functionality
    @FXML
    private ColumnWrapper columnVbox;
    //the textfield for the title of the column
    @FXML

    private TextField titleText; // Holds the title of the column
    
    //the VBox which we can add the small cards to it.
    @FXML
    private VBox cardContainer;
    //the scroll pane inside cardContainer to be able to scroll through cards.
    @FXML
    private ScrollPane scrollPane;
    //the choice box in the header of each column which is for choosing the role of each column.
    @FXML
    private ChoiceBox<String> colRole; // Dropdown for Column Roles



    /**
     * Initializes the current column and adds it to its Vbox.
     */
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
                setDragCardProperties(cardBox);  //add the drag and drop functionality to the existing card.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BoardManager.get().setCurrentCard(null);

        titleText.setText(column.getTitle());

        // Updates the column object to notify about
        titleText.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (!titleText.getText().equals("")){
                    columnVbox.getColumn().setTitle(titleText.getText());
                }
                else {
                    titleText.setText(columnVbox.getColumn().getTitle());
                }
            }
        });

        setDragCardOnColumnProperties();


        loadDataChoiceBox();
    }

    /**
     * Enables user to add a card to the column.
     */
    @FXML
    public void addCardAction() {
        BoardManager.get().setCurrentColumn(columnVbox.getColumn());

        BoardManager.get().setCurrentCard(null);
        //open a window and ask for the new card's title .
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
                setDragCardProperties(cardBox);
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

    /**
     * Enables user to delete the column from the board.
     */
    @FXML
    public void removeColumnAction(){
        columnVbox.getColumn().delete();
        ((HBox)columnVbox.getParent()).getChildren().remove(columnVbox);
    }

    /**
     * Resets the title to the original title stored for the column.
     */
    public void refresh(){
        titleText.setText(columnVbox.getColumn().getTitle());
    }

    /**
     * Loads all roles into the dropdown box.
     * Allows the user to choose a role for each column
     * and store it.

     */
    private void loadDataChoiceBox(){
        ObservableList<String> availableChoices = FXCollections.observableArrayList();
        availableChoices.removeAll(availableChoices);
        for (Role role : Role.values()){
            availableChoices.add(role.toString());
        }
        colRole.getItems().addAll(availableChoices);
        colRole.setValue(columnVbox.getColumn().getRole().toString());

        colRole.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            columnVbox.getColumn().setRole(Role.fromString(colRole.getItems().get((Integer)newValue)));
        });
    }

    /**
     * Enables the drag and drop function of the cards within the column and into other columns.
     * @param cardWrapper
     */
    public void setDragCardProperties(CardWrapper cardWrapper) {
        VBox mainVBox = (VBox) cardWrapper.getChildren().get(0);

        // the blue stripe
        VBox cardhead = (VBox) mainVBox.getChildren().get(0);

        // source(where we drag it)
        cardhead.setOnDragDetected(event -> {
            Dragboard db = cardWrapper.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(cardWrapper.toString());
            db.setContent(content);
            SnapshotParameters snapShot = new SnapshotParameters();
            snapShot.setTransform(Transform.scale(0.4,0.3));
            db.setDragView(cardWrapper.snapshot(snapShot, null));
            event.consume();
        });

        // target
        cardhead.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof CardWrapper && event.getGestureSource() != cardWrapper && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }

        });

        // target(where we wanna drop it)
        cardhead.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int indexForInsertion = cardContainer.getChildren().indexOf(cardWrapper);
                indexForInsertion = (indexForInsertion>=0)? indexForInsertion : 0;
                CardWrapper cardW = (CardWrapper) event.getGestureSource();
                // if it's the same column
                if (cardContainer.getChildren().contains(cardW)){
                    cardContainer.getChildren().remove(cardW);
                }
                cardContainer.getChildren().add(cardW);
                cardW.getCard().move(columnVbox.getColumn(), indexForInsertion);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void setDragCardOnColumnProperties() {
        // target
        cardContainer.setOnDragOver(event -> {
            System.out.println(columnVbox.getColumn().getTitle());
            if (event.getGestureSource() instanceof CardWrapper && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
                System.out.println("ACCEPTED!");
            }
            else {
                System.out.println("REJECTED :(");
            }
        });

        // target(where we wanna drop it)
        cardContainer.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {

                CardWrapper cardW = (CardWrapper) event.getGestureSource();
                // if it's the same column
                if (cardContainer.getChildren().contains(cardW)){
                    cardContainer.getChildren().remove(cardW);
                }
                int indexForInsertion = cardContainer.getChildren().size();
                indexForInsertion = (indexForInsertion>=0)? indexForInsertion : 0;
                cardContainer.getChildren().add(cardW);
                cardW.getCard().move(columnVbox.getColumn(), indexForInsertion);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}

