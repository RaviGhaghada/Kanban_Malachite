package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class LogController {

    @FXML
    private Text messageBox;

    @FXML
    public Button backButton;

    @FXML
    public TableView<Board> boardTableView;

    /**
     * Initialises the Log pane.
     * Loads all external data for generating the board's log,
     * populates list of labels for use in displaying values,
     */
    @FXML
    public void initialize() {
        messageBox.setText("Log for Your Board");
        boardTableView.getItems().addAll(Logger.get().getVersions());
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
     * Opens board version if there is a listed board version selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    public void selectAction (MouseEvent mouseEvent) {

        Board selectedBoard = boardTableView.getSelectionModel().getSelectedItem();

        if (selectedBoard == null) return; // nothing to click on

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                BoardManager.get().setCurrentBoard(selectedBoard);
                System.out.println("Double clicked");
                openBoard(selectedBoard);
            }
        }

    }
}
