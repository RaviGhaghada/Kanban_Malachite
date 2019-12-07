package controllers;
import boardpackage.Board;
import boardpackage.BoardManager;
import boardpackage.Column;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import wrappers.CardWrapper;
import wrappers.ColumnWrapper;

import java.io.IOException;
import java.util.ArrayList;


public class BoardController {

    @FXML
    private HBox columnContainer;

    private Board board;

    @FXML
    public void initialize(){
        this.board = BoardManager.get().getCurrentBoard();

        for (Column c : this.board.getColumns()){
            BoardManager.get().setCurrentColumn(c);
            ColumnWrapper colBox;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/column.fxml"));
                colBox = loader.load();
                columnContainer.getChildren().add(colBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BoardManager.get().setCurrentColumn(null);
    }


    @FXML
    public void addColumnAction(){

        BoardManager.get().setCurrentColumn(null);


        // TODO: Use Mariam's popup to ask for a name
        // code ... code ... code .. code
        // BUT ONLY FOR TESTING PURPOSES, for now:
        BoardManager.get().setCurrentColumn(new Column("ColumnTitle"));
        // The program will pause until the popup is closed.

        // Now if we are at this line, then that means
        // that the popup has closed

        if (BoardManager.get().getCurrentColumn() != null){
            // that means that a column successfully created in the popup
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/column.fxml"));
                ColumnWrapper colBox = loader.load();
                columnContainer.getChildren().add(colBox);

                BoardManager.get().setCurrentColumn(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dragCol(VBox column) {
        VBox head = (VBox) column.getChildren().get(0);
        head.setOnDragDetected(event -> {
            Dragboard db = column.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(column.toString());
            db.setContent(content);
            event.consume();
        });

        head.setOnDragOver(event -> {
            Parent newParent = (Parent) event.getGestureSource();
            if (newParent != column && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });

        head.setOnDragDropped(event -> {
            boolean finish = false;
            if (event.getDragboard().hasString()) {
                Parent start = (Parent) event.getGestureSource();
                int startingPoint = columnContainer.getChildren().indexOf(start);
                int endingPoint = columnContainer.getChildren().indexOf(column);
                if (startingPoint >= 0 && endingPoint >= 0) {
                    ObservableList<Node> allColumns = columnContainer.getChildren();
                    ArrayList<Node> allSwappedCol = new ArrayList<>(allColumns);
                    Node swapped = allSwappedCol.get(startingPoint);
                    allSwappedCol.set(startingPoint, allSwappedCol.get(endingPoint));
                    allSwappedCol.set(endingPoint, swapped);
                    columnContainer.getChildren().clear();
                    for (Node node : allSwappedCol) {
                        columnContainer.getChildren().add(node);
                    }
                }
                finish = true;
            }
            event.setDropCompleted(finish);
            event.consume();
        });


    }

}
