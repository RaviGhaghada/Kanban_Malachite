package controllers;
import boardpackage.BoardManager;
import boardpackage.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import wrappers.CardWrapper;

import java.io.IOException;

public class smallCardController {

    @FXML
    private CardWrapper smallCardHbox;

    @FXML
    private TextArea cardDisplayText;

    private Stage stage;

    @FXML
    public void initialize(){
        Card card = BoardManager.get().getCurrentCard();
        smallCardHbox.setCard(card);
        refresh();
    }


    @FXML
    public void openEditPopupAction(){
        BoardManager.get().setCurrentCard(smallCardHbox.getCard());
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cardpopup.fxml"));
            root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            stage = new Stage();
            stage.setTitle("Card Editor");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.showAndWait();

            if (BoardManager.get().getCurrentColumn() == null){
                removeCardBtnAction();
            }
            else{
                refresh();
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            BoardManager.get().setCurrentCard(null);
        }
    }

    public void removeCardBtnAction(){
        smallCardHbox.getCard().delete();
        smallCardHbox.getParent().getChildrenUnmodifiable().remove(smallCardHbox);
    }

    public HBox getHbox(){
        return smallCardHbox;
    }



    public void refresh(){
        cardDisplayText.setText(smallCardHbox.getCard().getTitle());
    }
}

