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

public class NewBoardController{

    public TextField boardTitle = new TextField();
    // TODO: remove the boardDescription TextField
    public TextField boardDescription = new TextField();

    @FXML
    public Button cancelButton; // button labeled as "Cancel" in popup

    @FXML
    public void saveAndCloseAction(){
//        if (boardTitle.getText() == null){
//            // Error pop up
//        } else {
        Board b = new Board(boardTitle.getText());
        BoardManager.get().setCurrentBoard(b);
//        }

    }

    @FXML
    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
