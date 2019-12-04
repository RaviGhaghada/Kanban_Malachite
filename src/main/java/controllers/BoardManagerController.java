package controllers;

import boardpackage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public ListView<Board> boardListView;

    @FXML
    public Button backButton;

    /**
     * Constructor for BoardManagerController.
     */
    public BoardManagerController() {
 
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
        boardListView = new ListView<Board>();

        ArrayList<Board> boards = BoardManager.get().getBoards();

        for(Board board : boards){
            boardListView.getItems().add(board);
        }
    }

    /**
     * Allows user to add Board. Opens "New Board" popup.
     */
    public void addAction (ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newboard.fxml"));
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
     * Opens the window with selected board.
     */
    public void openBoard (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/board.fxml"));

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

    /**
     * Checks mouseClick event for double click.
     * Opens board popup if there is a listed board selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    public void selectAction (MouseEvent mouseEvent) {

//        try {
            Board selectedBoard = boardListView.getSelectionModel().getSelectedItem();
            if (selectedBoard == null) return; // nothing to click on
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    BoardManager.get().setCurrentBoard(selectedBoard);
                    System.out.println("Double clicked");
                    openBoard();
                }
            }
//        } catch (UnknownBoardException e) {
//            e.printStackTrace();
//        }

    }


    /**
     * Enables the forwardButton to open the Board Manager when clicked.
     */
    public void backClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
        System.out.println("I was clicked!");
        Parent root = (Parent) loader.load();
        Scene s = backButton.getScene();
        s.setRoot(root);

    }

    /**
     * Checks mouseClick event for double click.
     * Opens board if there is a board selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    public void listViewClicked (MouseEvent mouseEvent) {

        Board selectedItem = boardListView.getSelectionModel().getSelectedItem();

        if(selectedItem == null) return; // nothing to click on

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                System.out.println("Double clicked");
                openBoard();
            }
        }

    }
}
