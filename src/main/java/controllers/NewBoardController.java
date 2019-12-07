package controllers;

import boardpackage.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.event.ActionEvent;

public class NewBoardController {

    public TextField boardTitle = new TextField();

    @FXML
    public Button cancelButton = new Button(); // button labeled as "Cancel" in popup
    public Button saveButton = new Button(); // button labeled as "Save" in popup

    public Stage stage;

    /**
     * Initializes the NewBoard popup window.
     */
    @FXML
    protected void initialize() {

    }

    /**
     * Creates an error message popup when an error occurs.
     * Has a catch statement in case the popup fails to open.
     * @param errorMessage
     */
    public void newBoardErrorPopup(String errorMessage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/errorpopup.fxml"));
            // Create a controller instance
            ErrorPopupController controller = new ErrorPopupController(errorMessage);
            // Set it in the FXMLLoader
            loader.setController(controller);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until dialog is closed
            Parent popup = (Parent) loader.load();
            stage.setScene(new Scene(popup));
            stage.showAndWait();
            System.out.println("FUCK THIS");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to launch popup.");
        }
    }

    /**
     * Adds new board to the existing boards.
     * Checks for empty name or duplicate name.
     * Closes NewBoard popup.
     */
    @FXML
    // TODO: create new board
    // TODO: error for duplicate names
    public void saveAndCloseAction(){
        if (boardTitle.getText().length() == 0){
            newBoardErrorPopup("Please give your board a name");
        } else {
            Board b = new Board(boardTitle.getText());
            BoardManager.get().setCurrentBoard(b);
            stage.close();
        }

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Closes NewBoard popup when cancelButton is selected.
     */
    @FXML
    public void cancelAction(ActionEvent event){
        stage.close();
    }
}
