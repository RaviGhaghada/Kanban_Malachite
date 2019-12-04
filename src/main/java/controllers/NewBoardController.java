package controllers;

import boardpackage.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewBoardController {

    public TextField boardTitle = new TextField();

    public void NewBoardController(){
        boardTitle.setPromptText("Board Title...");
    }

    @FXML
    public void saveAndCloseAction(){
        if (boardTitle.getText() == null){
            // Error pop up
        } else {
            Board board = new Board(boardTitle.getText());
        }
    }

    @FXML
    public void deleteAction(){


    }
}
