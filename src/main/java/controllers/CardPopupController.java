package controllers;
import boardpackage.BoardManager;
import boardpackage.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This is a class fro creating the cards as we click on the small cards located in each column .
 * User can type the content of the card in it and save them in the cards .
 * Also user can delete the card from the columns using this class .
 *
 */

public class CardPopupController {

    // title of a card
    @FXML
    private TextField cardTitle;

    // add a detailed description
    @FXML
    private TextArea cardDescription;

    // story points of a card
    @FXML
    private TextArea storypoints;

    Card card;

    @FXML
    public void initialize(){
        card = BoardManager.get().getCurrentCard();
        cardTitle.setText(card.getTitle());
        cardDescription.setText(card.getText());
        storypoints.setText(card.getStoryPoints());
    }

    @FXML
    public void saveAndCloseAction(ActionEvent event){
        if (cardTitle.getText().length() > 0) {
            card.setTitle(cardTitle.getText());
            card.setText(cardDescription.getText());
            card.setStoryPoints(storypoints.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
        else{
            // TODO: show error popup saying that it must have a title
        }
    }

    @FXML
    public void deleteButtonAction(ActionEvent event){
        // signal for deletion
        BoardManager.get().setCurrentCard(null);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
