package controllers;
import boardpackage.BoardManager;
import boardpackage.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a class fro creating the cards as we click on the small cards located in each column.
 * User can type the content of the card in it and save them in the cards.
 * Also user can delete the card from the columns using this class.
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

    /**
     * create the card and initialize  the title , the description and the story points of each card.
     */
    @FXML
    public void initialize(){
        card = BoardManager.get().getCurrentCard();
        cardTitle.setText(card.getTitle());
        cardDescription.setText(card.getText());
        storypoints.setText(String.valueOf(card.getStoryPoints()));
    }

    /**
     * this button will save all of the data of each card so that after closing and opening
     * the same card again , it gonna keep the data.
     * check if the user has inserted a title for the card , if not show a pop up error message .
     * @param event when the save button is clicked
     */
    @FXML
    public void saveAndCloseAction(ActionEvent event){
        String message = "";
        if (cardTitle.getText().length() <= 0){
            message = message + "Cannot give the board a blank title!";
        }
        if (!storypoints.getText().matches("\\d+")){
            message = message + "\nStory points must be a non-negative integer!";
        }
        if (message.equals("")) {
            card.setTitle(cardTitle.getText());
            card.setText(cardDescription.getText());
            card.setStoryPoints(Integer.parseInt(storypoints.getText()));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
        else{

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/errorpopup.fxml"));
                // Create a controller instance
                ErrorPopupController controller = new ErrorPopupController(message);
                // Set it in the FXMLLoader
                loader.setController(controller);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until dialog is closed
                Parent popup = (Parent) loader.load();
                stage.setScene(new Scene(popup));
                stage.showAndWait();

            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to load popup");
            }

        }
    }

    /**
     * delete the current opened card as the delete button is pressed .
     * @param event when the delete button is pressed .q
     */
    @FXML
    public void deleteButtonAction(ActionEvent event){
        // signal for deletion
        BoardManager.get().setCurrentCard(null);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
