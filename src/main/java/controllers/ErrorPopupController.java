package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the Popup windows. Popups occur when there
 * is a user error. There is a specified message created to
 * inform the user of the specific error.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class ErrorPopupController {
    private Button okButton; // button labeled as "OK" in popup

    @FXML
    private Label errorLabel; // the label which holds the error message

    @FXML
    private String errorMessageText; // the error message specified for the type of error

    /**
     * Sets the error message within the label on popup window.
     */
    @FXML
    protected void initialize() {
        errorLabel.setText(errorMessageText);
    }

    /**
     * Generates the error message in the popup
     */
    public ErrorPopupController(String errorMessage){
        errorMessageText = errorMessage;
    }

    /**
     * When the ok button is clicked, the popup is closed.
     */
    public void okButtonClicked(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close(); // closes the popup window
    }

}
