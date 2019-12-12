package controllers;
import boardpackage.Board;
import boardpackage.BoardManager;
import boardpackage.Card;
import boardpackage.Column;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;


public class BoardController {

    @FXML
    private HBox columnContainer;

    @FXML
    private ScrollPane scrollPane;

    private Board board;

    @FXML
    public Button backButton;
    public Button quitButton;

    @FXML
    public void initialize() {
        this.board = BoardManager.get().getCurrentBoard();

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

    @FXML
    // TODO: call BoardManager.save() when Manvi pushes her code
    public void quitAction() {
        Platform.exit();
    }


    public void setDragColumnProperties(ColumnWrapper columnWrapper) {
        VBox mainVBox = (VBox) columnWrapper.getChildren().get(0);
        //the very first component in the card , which we can drag the card with
        HBox cardhead = (HBox) mainVBox.getChildren().get(0);


        // source
        cardhead.setOnDragDetected(event -> {
            System.out.println("DRAG DETECTED FOR " + event.getSource().getClass().getSimpleName());
            Dragboard db = columnWrapper.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(columnWrapper.toString());
            db.setContent(content);
            event.consume();
        });

        // target
        cardhead.setOnDragOver(event -> {
            //if (event.;)
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
                System.out.println(colW.getColumn().getTitle());
                // if it's the same column
                if (columnContainer.getChildren().contains(colW)) {
                    columnContainer.getChildren().remove(colW);
                }
                columnContainer.getChildren().add(indexForInsertion, colW);
                success = true;
            } else {
                System.out.println("FALSE DROP!");
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }


}
