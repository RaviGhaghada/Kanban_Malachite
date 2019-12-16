package controllers;
import boardpackage.BoardManager;
import boardpackage.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;

import java.io.IOException;

/**
 * Controller for Mello Cards.
 * This class represents the small cards in each column.
 * By pressing on the a new pop up window will open which
 * is the card itself with all of it's elements.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class CardController {

    //the card's HBox with all of the small card functionality
    @FXML
    private CardWrapper smallCardHbox;
    //display the title of the card as it's text
    @FXML
    private TextArea cardDisplayText;
    //the current stage for opening the pop up card.
    private Stage stage;

    /**
     * initialize the current card and add it to its HBox.
     */
    @FXML
    public void initialize(){
        Card card = BoardManager.get().getCurrentCard();
        smallCardHbox.setCard(card);
        refresh();
    }

    /**
     * open a pop up window which displays the same card but with all of it's
     *  information . store the inserted data for each card specifically .
     */
    @FXML
    public void openEditPopupAction(){
        BoardManager.get().setCurrentCard(smallCardHbox.getCard());
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cardpopup.fxml"));
            root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Card Editor");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();

            if (BoardManager.get().getCurrentCard() == null){
                removeCardBtnAction();
            }
            else{
                refresh();
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            BoardManager.get().setCurrentCard(null);
        }
    }

    /**
     * as the delete button of the pop up card is pressed
     * the card in the column will be deleted with all of it's
     * functionality .
     */
    public void removeCardBtnAction(){
        smallCardHbox.getCard().delete();

        ((Pane)(smallCardHbox.getParent())).getChildren().remove(smallCardHbox);
    }

    /**
     * Display the title of the card as the text of the small card
     * in the column in each card.
     */
    public void refresh(){
        cardDisplayText.setText(smallCardHbox.getCard().getTitle());
    }

}



