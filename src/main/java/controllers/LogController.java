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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;


import java.io.IOException;
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
     * Opens the window with a non-editable board.
     */
    private void openBoard(String versionNo){
        try {
            Board original = BoardManager.get().getCurrentBoard();
            Board temp = BoardManager.get().getBoardVersion(versionNo);

            BoardManager.get().setCurrentBoard(temp);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/board.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until dialog is closed
            Parent popup = loader.load();
            ((BoardController)loader.getController()).setReadOnly();
            stage.setScene(new Scene(popup));
            stage.showAndWait();

            BoardManager.get().setCurrentBoard(original);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks mouseClick event for double click.
     * Opens board version if there is a listed board version selected & available.
     * @param mouseEvent the details on the mouseClick event
     */
    public void selectAction (MouseEvent mouseEvent) {

        String versionNo = versionTableView.getSelectionModel().getSelectedItem()[0];
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                openBoard(versionNo);
            }
        }
    }
}
