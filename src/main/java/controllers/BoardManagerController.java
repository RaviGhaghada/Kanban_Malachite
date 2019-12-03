package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
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

/**
 * Controller for the Mello Board Manager.
 * Displays all of the existing boards. Allows user to add, remove, and view boards.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */

public class BoardManagerController implements Initializable {
    private ArrayList<Board> allBoards;
    public ListView<Board> boardListView;

    /**
     * Constructor for BoardManagerController.
     * @param boardList is a list of all boards
     */
    public BoardManagerController(ArrayList<Board> boardList) {
        allBoards = boardList;
    }

    /**
     * Initializes the window and adds all boards to the list.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Board list styling
        boardListView.setStyle("-fx-font-family: 'monospaced';");

        // adds all boards available to list of boards to be displayed
        for(Board board : allBoards){
            boardListView.getItems().add(board);
        }
    }

    /**
     * Allows user to add Board. Opens "New Board" popup.
     */
    public void addAction (ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/newboard.fxml"));
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
    }

    /**
     * Checks mouseClick event for double click.
     * Opens board popup if there is a listed board selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    // TODO: set current board to selected board
    public void selectAction (MouseEvent mouseEvent) {

        Board selectedBoard = boardListView.getSelectionModel().getSelectedItem();
        if (selectedBoard == null) return; // nothing to click on
        BoardManager.get().setCurrentBoard(selectedBoard);
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                BoardManager.get().setCurrentBoard(selectedBoard);
                System.out.println("Double clicked");
                openBoard(selectedBoard);
            }
        }

    }

    /**
     * Opens the window with selected board.
     * @param board The board selected by the user
     */
    public void openBoard (Board board){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/board.fxml"));

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