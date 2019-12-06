package controllers;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class BoardController {

    @FXML
    private HBox columnHbox ;   //the hbox containing al of the columns.

    public BoardController(){}

    //add functionality to the button "add column".
    @FXML
    public void addColumnAction(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/column.fxml"));

        try{
            VBox newCol = loader.load();
            columnHbox.getChildren().add(newCol);
            dragCol(newCol);
        }
        catch (Exception e){
            e.printStackTrace();
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
                int startingPoint = columnHbox.getChildren().indexOf(start);
                int endingPoint = columnHbox.getChildren().indexOf(column);
                if (startingPoint >= 0 && endingPoint >= 0) {
                    ObservableList<Node> allColumns = columnHbox.getChildren();
                    ArrayList<Node> allSwappedCol = new ArrayList<>(allColumns);
                    Node swapped = allSwappedCol.get(startingPoint);
                    allSwappedCol.set(startingPoint, allSwappedCol.get(endingPoint));
                    allSwappedCol.set(endingPoint, swapped);
                    columnHbox.getChildren().clear();
                    for (Node node : allSwappedCol) {
                        columnHbox.getChildren().add(node);
                    }
                }
                finish = true;
            }
            event.setDropCompleted(finish);
            event.consume();
        });


    }

}
