package controllers;

import boardpackage.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.event.ActionEvent;

public class NewTitleController {

    private Class aClass;

    @FXML
    private Text messageBox;

    @FXML
    private TextArea textInput;

    @FXML
    public Button cancelButton; // button labeled as "Cancel" in popup

    @FXML
    public Button saveButton; // button labeled as "Save" in popup

    public void setaClass(Class aClass){
        this.aClass = aClass;
        if (aClass.equals(Board.class)){
            messageBox.setText("Enter a title for your new board:");
            textInput.setPromptText("Name of board");
        } else if (aClass.equals(Column.class)){
            messageBox.setText("Enter a title for your new column:");
            textInput.setPromptText("Name of column");
        } else if (aClass.equals(Card.class)){
            messageBox.setText("Enter a title for your new card:");
            textInput.setText("Name of card");
        }
        else {
            ((Stage)(saveButton.getScene().getWindow())).close();
        }
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
    public void saveAndCloseAction(){
        if (textInput.getText().length() == 0){
            String classname = aClass.getSimpleName().toLowerCase();
            newBoardErrorPopup("Please give your " + classname + " a name!");
        } else {

            if (aClass.equals(Board.class)){
                Board board = new Board(textInput.getText());
                BoardManager.get().setCurrentBoard(board);
            } else if (aClass.equals(Column.class)){
                Column column = new Column(textInput.getText());
                BoardManager.get().setCurrentColumn(column);
            } else if (aClass.equals(Card.class)){
                Card card = new Card(textInput.getText());
                BoardManager.get().setCurrentCard(card);
            }

            ((Stage)(saveButton.getScene().getWindow())).close();


        }

    }

    /**
     * Closes NewBoard popup when cancelButton is selected.
     */
    @FXML
    public void cancelAction(ActionEvent event){
        ((Stage)(cancelButton.getScene().getWindow())).close();
    }
}
