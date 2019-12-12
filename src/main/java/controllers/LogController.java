package controllers;

import boardpackage.Board;
import boardpackage.BoardManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;


import java.util.ArrayList;
import java.util.Comparator;

public class LogController {

    @FXML
    private TableColumn versionCol;

    @FXML
    private TableColumn timeCol;

    @FXML
    private TableColumn changesCol;

    @FXML
    private Text messageBox;

    @FXML
    private Button backButton;



    @FXML
    private TableView<String[]> versionTableView;

    /**
     * Initialises the Log pane.
     * Loads all external data for generating the board's log,
     * populates list of labels for use in displaying values,
     */
    @FXML
    public void initialize() {
        ArrayList<String[]> versions = BoardManager.get().getAllBoardVersionsMeta();
        versions.sort(Comparator.comparingInt(o -> Integer.parseInt(o[0])));

        versionCol.setCellValueFactory((Callback<CellDataFeatures<String[], String>, ObservableValue<String>>) p -> new SimpleStringProperty((p.getValue()[0])));
        timeCol.setCellValueFactory((Callback<CellDataFeatures<String[], String>, ObservableValue<String>>) p -> new SimpleStringProperty((p.getValue()[1])));
        changesCol.setCellValueFactory((Callback<CellDataFeatures<String[], String>, ObservableValue<String>>) p -> new SimpleStringProperty((p.getValue()[2])));

        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(versions);
        versionTableView.setItems(data);
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

        Board selectedBoard = BoardManager.get().getCurrentBoard();// = versionTableView.getSelectionModel().getSelectedItem();

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
