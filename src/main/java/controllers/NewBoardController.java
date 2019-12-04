package controllers;

import boardpackage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewBoardController implements Initializable {
    public Board board = new Board("1", "");

    public TextField boardTitle = new TextField();
    public TextField boardDescription = new TextField();

    @FXML
    public Button cancelButton; // button labeled as "Cancel" in popup

    public void NewBoardController(){
        boardTitle.setPromptText("Board Title...");
        boardDescription.setPromptText("Board Description...");
    }

    /**
     * Initializes the window and adds all boards to the list.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardTitle.setPromptText("Board Title...");
        boardDescription.setPromptText("Board Description...");
    }

    @FXML
    public void saveAndCloseAction(ActionEvent actionEvent){
//        if (boardTitle.getText() == null){
//            // Error pop up
//        } else {
            this.board.setName(boardTitle.getText());
//        }
//        try {
            BoardManager.get().addBoard(board);
            BoardManager.get().setCurrentBoard(board);
//        } catch (DuplicateNameException e){
//            // error pop up! kanban board of the same name already exists!
//        }
    }

    @FXML
    public void cancelAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
