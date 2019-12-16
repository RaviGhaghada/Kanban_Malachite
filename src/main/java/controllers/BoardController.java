package controllers;
import boardpackage.Board;
import boardpackage.BoardManager;
import boardpackage.Card;
import boardpackage.Column;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Controller for the Mello Board.
 * This class is the main board which contains all of the columns and their cards
 * it allows the user to insert the columns to a board and remove them.
 * The user can also view the statistics and the log of the board.
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */


public class BoardController {

    //the root of the board.
    @FXML
    private AnchorPane rootPane;
    // the main HBox which contains all of the columns as they are added .
    @FXML
    private HBox columnContainer;
    //a pane at the top of the board which contains the logo and few buttons.
    @FXML
    private GridPane melloPane;
    //the horizontal scroll pane which allows the user to scroll through columns.
    @FXML
    private ScrollPane scrollPane;
    //current board.
    private Board board;
    //a button for taking us back to the welcome page.
    @FXML
    public Button backButton;
    //the button for quitting the app .
    public Button quitButton;
    //the button to see the activity log .
    @FXML
    private Button activityLog;
    //the column to see che statistics of the current board .
    @FXML
    private Button stats ;
    //the title for the current board on the top left .
    @FXML
    private Label boardTitle;

    /**
     * initialize the current board with a column and card in it . If it doesnt open throw an error.
     */
    @FXML
    public void initialize() {
        this.board = BoardManager.get().getCurrentBoard();
        setBoardTitle();

        for (Column c : this.board.getColumns()) {
            BoardManager.get().setCurrentColumn(c);
            ColumnWrapper colBox;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/column.fxml"));
                colBox = loader.load();
                columnContainer.getChildren().add(colBox);
                setDragColumnProperties(colBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BoardManager.get().setCurrentColumn(null);
    }

    /**
     * a button for adding column to the current board .
     * Opens a new window for inserting the title of the new column. if couldnt load the column
     * throw an exception error
     */
    @FXML
    public void addColumnAction() {

        BoardManager.get().setCurrentColumn(null);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newtitle.fxml"));

        try {
            Parent popup = loader.load();
            ((NewTitleController) loader.getController()).setaClass(Column.class);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(popup));
            stage.setResizable(false);
            stage.showAndWait();

            if (BoardManager.get().getCurrentBoard() != null) {
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/column.fxml"));
                ColumnWrapper colBox = loader.load();
                colBox.setColumn(BoardManager.get().getCurrentColumn());
                columnContainer.getChildren().add(colBox);
                setDragColumnProperties(colBox);

                BoardManager.get().setCurrentColumn(null);

                Platform.runLater(() -> {
                    scrollPane.setHvalue(1.0);
                });
            }

        } catch (Exception e) {
            System.err.println("failed to launch load fxml file");
            e.printStackTrace();
        }
    }

    /**
     * delete the current board .
     */
    @FXML
    public void deleteBoardAction() {
        System.out.println("DELETE? " + BoardManager.get().getBoards().size());
        board.delete();
        System.out.println("DELETED: " + BoardManager.get().getBoards().size());
        this.board = null;
        backAction();
    }

    /**
     * a button for taking the user back to the welcome page where
     * there's a list of all of the boards.
     */
    @FXML
    public void backAction() {
        try {
            BoardManager.get().setCurrentBoard(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardmanager.fxml"));
            System.out.println("I was clicked!");
            Parent root = (Parent) loader.load();
            Scene s = backButton.getScene();
            s.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enables user to exit the application.
     */
    @FXML
    public void quitAction() {
        Platform.exit();
    }

    /**
     * allows the user to drag a column and drop it on another column .
     * (replacing the dragged column with the column we drop on it and shift all other columns
     * one to the left or right .
     * @param columnWrapper the VBox of the current column .
     */
    public void setDragColumnProperties(ColumnWrapper columnWrapper) {
        VBox mainVBox = (VBox) columnWrapper.getChildren().get(0);
        //the very first component in the card , which we can drag the card with
        HBox cardhead = (HBox) mainVBox.getChildren().get(0);


        // source
        cardhead.setOnDragDetected(event -> {
            Dragboard db = columnWrapper.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(columnWrapper.toString());
            db.setContent(content);
            SnapshotParameters snapShot = new SnapshotParameters();
            snapShot.setTransform(Transform.scale(0.2,0.2));
            db.setDragView(columnWrapper.snapshot(snapShot, null));
            event.consume();
        });

        // target
        cardhead.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof ColumnWrapper && event.getGestureSource() != columnWrapper && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }

        });

        // target(where we want to drop the column)
        cardhead.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int indexForInsertion = columnContainer.getChildren().indexOf(columnWrapper);
                indexForInsertion = (indexForInsertion >= 0) ? indexForInsertion : 0;
                ColumnWrapper colW = (ColumnWrapper) event.getGestureSource();
                // if it's the same column
                if (columnContainer.getChildren().contains(colW)) {
                    columnContainer.getChildren().remove(colW);
                }
                columnContainer.getChildren().add(indexForInsertion, colW);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    /**
     * a pop up message which will appear after holding the
     * mouse on the stats button (for user's information)
     */
    @FXML
    public void showStatsMessage(){

        stats.setText("statistics");
        Tooltip tt = new Tooltip();
        tt.setText("Board Statistics");
        tt.setStyle("-fx-font-size: 12;"
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: #87CEFA;");
        stats.setTooltip(tt);
    }

    /**
     * a pop up message which will appear after holding the
     * mouse on the log button .(for giving user information about the
     * button)
     */
    @FXML
    public void showLogMessage(){

        activityLog.setText("activity");
        Tooltip tt = new Tooltip();
        tt.setText("Activity Log");
        tt.setStyle("-fx-font-size: 12;"
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: #87CEFA;");
        activityLog.setTooltip(tt);
    }

    /**
     * set the title of the current board on the label on the top left of the board .
     */
    @FXML
    public void setBoardTitle(){
        String boardName = BoardManager.get().getCurrentBoard().getTitle();
        boardTitle.setText(boardName);


    }

    /**
     * open the log activity for the user after pressing the button .
     */
    @FXML
    private void openLogAction(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until dialog is closed
            Parent popup = loader.load();
            stage.setScene(new Scene(popup));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open the statistics page after pressing it's button .
     */
    @FXML
    private void openStatisticsAction(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/statistics.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until dialog is closed
            Parent popup = loader.load();
            stage.setScene(new Scene(popup));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * disables all events except the one for scrolling
     * this function will be used when we want to have
     * a read only version of the app(from activity log)
     */
    public void setReadOnly(){
        rootPane.getChildren().remove(melloPane);
        rootPane.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getTarget().getClass().toString().contains("Scroll")){
                event.consume();
            }
            // else let any scrolling events slide by
        });
    }

}
