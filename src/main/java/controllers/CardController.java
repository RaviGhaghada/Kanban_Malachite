package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class CardController {
    @FXML
    private Button delete;
    @FXML
    private AnchorPane cardPane ;
    @FXML
    private TextArea cardText;
    @FXML
    private TextField cardTitle;

    private smallCardController parent ;

    private TextArea smallcard;


    public void setFollow(TextArea smallCard){
        String title = cardTitle.getText();
        this.smallcard = smallCard;
        this.cardText.setText(this.smallcard.getText());
    }

    @FXML
    public void saveAndCloseAction(){
        this.smallcard.setText(cardText.getText());
    }

    @FXML
    public void deleteButtonAction(){
        cardText.setText("");
        smallcard.setText("");
        parent.removeCardBtnAction();
    }

    public void setParent(smallCardController currentParent){
        this.parent = currentParent ;
    }


}
