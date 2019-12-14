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
 * Displays the current state of the current Board.
 * Hosts columns, cards, and links to Statistics and Log pop up.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */


public class BoardController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private HBox columnContainer;

    @FXML
    private GridPane melloPane;
    @FXML
    private ScrollPane scrollPane;

    private Board board;

    @FXML
    public Button backButton;
    public Button quitButton;

    @FXML
    private Button activityLog;
    @FXML
    private Button stats ;
    @FXML
    private Label boardTitle;


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

    @FXML
    public void deleteBoardAction() {
        System.out.println("DELETE? " + BoardManager.get().getBoards().size());
        board.delete();
        System.out.println("DELETED: " + BoardManager.get().getBoards().size());
        this.board = null;
        backAction();
    }

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

        // target
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

    @FXML
    public void setBoardTitle(){
        String boardName = BoardManager.get().getCurrentBoard().getTitle();
        boardTitle.setText(boardName);


    }

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
