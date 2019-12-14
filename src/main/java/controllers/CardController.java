package controllers;
import boardpackage.BoardManager;
import boardpackage.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;

import java.io.IOException;

/**
 * Controller for cards.
 * Displays the current state of the current Board.
 * Hosts columns, cards, and links to Statistics and Log pop up.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class CardController {

    @FXML
    private CardWrapper smallCardHbox;

    @FXML
    private TextArea cardDisplayText;

    private Stage stage;

    @FXML
    public void initialize(){
        Card card = BoardManager.get().getCurrentCard();
        smallCardHbox.setCard(card);
        refresh();
    }

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

    public void removeCardBtnAction(){
        smallCardHbox.getCard().delete();

        ((Pane)(smallCardHbox.getParent())).getChildren().remove(smallCardHbox);
    }

    public void refresh(){
        cardDisplayText.setText(smallCardHbox.getCard().getTitle());
    }

}



