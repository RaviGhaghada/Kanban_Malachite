package controllers;//add package later

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ColumnPageController {

    @FXML
    private HBox columnHbox ;   //the hbox containing al of the columns.

    public ColumnPageController(){}

    //add functionality to the button "add column".
    @FXML
    public void addColumnAction(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("column.fxml"));

        try{
            VBox newCol = loader.load();
            columnHbox.getChildren().add(newCol);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }





}
