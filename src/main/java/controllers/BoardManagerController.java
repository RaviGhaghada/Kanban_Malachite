package controllers;

import boardpackage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the Mello Board Manager.
 * Displays all of the existing boards. Allows user to add and select boards.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */

public class BoardManagerController {

    @FXML
    public ListView<Board> boardListView;

    @FXML
    public Button backButton;

    /**
     * Constructor for BoardManagerController.
     */
    public BoardManagerController() {

    }

    /**
     * Initializes the window and adds all existing boards to the ListView.
     */
    @FXML
    public void initialize() {

        BoardManager.get().setCurrentBoard(null);
        // Board list styling
        boardListView.setStyle("-fx-font-family: 'monospaced';");

        boardListView.getItems().addAll(BoardManager.get().getBoards());
        boardListView.setCellFactory(lv -> new ListCell<Board>() {
            @Override
            public void updateItem(Board item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getTitle();
                    setText(text);
                }
            }
        });
    }

    /**
     * Allows user to add Board. Opens "New Board" popup.
     */
    // TODO: Functionality of creating new board and adding to boards
    @FXML
    public void addAction (ActionEvent actionEvent) {
        BoardManager.get().setCurrentBoard(null);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newtitle.fxml"));

        try {
            Parent popup = loader.load();
            ((NewTitleController)loader.getController()).setaClass(Board.class);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(popup));
            stage.setResizable(false);
            stage.showAndWait();

            if (BoardManager.get().getCurrentBoard() != null){
                boardListView.getItems().add(BoardManager.get().getCurrentBoard());
                BoardManager.get().setCurrentBoard(null);
            }

        }
        catch (Exception e){
            System.err.println("failed to launch new title popup");
            e.printStackTrace();
        }
    }

    /**
     * Opens the window with selected board.
     */
    public void openBoard (Board board){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/board.fxml"));
        BoardManager.get().setCurrentBoard(board);

        try {
            Parent popup = (Parent) loader.load();
            backButton.getScene().setRoot(popup);
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

        Board selectedBoard = boardListView.getSelectionModel().getSelectedItem();

        if (selectedBoard == null) return; // nothing to click on

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                BoardManager.get().setCurrentBoard(selectedBoard);
                System.out.println("Double clicked");
                openBoard(selectedBoard);
            }
        }

    }


    /**
     * Enables the backButton to open the Welcome page when clicked.
     */
    public void backClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
        System.out.println("I was clicked!");
        Parent root = (Parent) loader.load();
        Scene s = backButton.getScene();
        s.setRoot(root);

    }

}
