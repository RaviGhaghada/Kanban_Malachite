package controllers;

import boardpackage.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class BoardManagerController implements Initializable {
    private ArrayList<Board> allBoards;
    public ListView<Board> boardListView;


    public BoardManagerController(ArrayList<Board> boardList) {
        allBoards =  boardList;
    }

    /**
     * Initializes the window.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // property list styling
        boardListView.setStyle("-fx-font-family: 'monospaced';");

        // adds all properties in borough to list of properties to be displayed
        for(Board board : allBoards){
            boardListView.getItems().add(board);
        }
    }

    /**
     * Allows user to add Board.
     * Opens "New Board" popup.
     */
    public void addAction (ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/newboard.fxml"));

        // Create a controller instance
        NewBoardController controller = new NewBoardController();
        // Set it in the FXMLLoader
        loader.setController(controller);

        try {
            Parent popup = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(popup));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            System.out.println("failed to launch popup");
            e.printStackTrace();
        }
    }

    public void removeAction (ActionEvent actionEvent) {
\
    }

    /**
     * Checks mouseClick event for double click.
     * Opens board popup if there is a list item selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    public void selectAction (MouseEvent mouseEvent){

        Board selectedBoard = boardListView.getSelectionModel().getSelectedItem();
        if(selectedBoard == null) return; // nothing to click on

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                System.out.println("Double clicked");
                openBoard(selectedBoard);
            }
        }

    }

    public void openBoard (Board board){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/board.fxml"));

        // Create a controller instance
        BoardController controller = new BoardController(board);
        // Set it in the FXMLLoader
        loader.setController(controller);

        try {
            Parent popup = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(popup));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            System.out.println("failed to launch popup");
            e.printStackTrace();
        }
    }
}
