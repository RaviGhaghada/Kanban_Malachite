package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewBoardController {
    public Board board;

    public TextField boardTitle = new TextField();
    public TextField boardDescription = new TextField();

    public void NewBoardController(){
        boardTitle.setPromptText("Board Title...");
        boardDescription.setPromptText("Board Description...");
    }

    @FXML
    // TODO: add board function
    public void saveAndCloseAction(){
        if (boardTitle.getText() == null){
            // Error pop up
        } else {
            this.board.setName(boardTitle.getText());
        }
        //BoardManager.get().addBoard("1", "New Board", "The new board");
    }

    @FXML
    public void deleteAction(){


    }
}
