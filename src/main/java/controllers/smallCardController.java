package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class smallCardController {

    @FXML
    private TextArea cardDisplayText;
    private Stage stage;
    @FXML
    private HBox smallCardHbox;
    private ColumnController parent;

    @FXML
    public void smallCardBtnAction(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cardFXML.fxml"));
            root = loader.load();
            CardController cardC = loader.getController();
            cardC.setFollow(cardDisplayText);
            cardC.setParent(this);
            Scene scene = new Scene(root, 600, 400);
            stage = new Stage();
            stage.setTitle("Card");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void setParent(ColumnController columnCntrler){
        this.parent = columnCntrler ;
    }

    public void removeCardBtnAction(){
        stage.close();
        parent.removeSmallCard(this);

    }

    public HBox getHbox(){
        return smallCardHbox;
    }



}

