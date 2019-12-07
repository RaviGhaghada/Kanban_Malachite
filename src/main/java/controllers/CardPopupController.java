package controllers;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is a class fro creating the cards as we click on the small cards located in each column .
 * User can type the content of the card in it and save them in the cards .
 * Also user can delete the card from the columns using this class .
 *
 */

public class CardPopupController {

    //the text area for writing the content of each card in it
    @FXML
    private TextArea cardText;
    //the title for each card .
    @FXML
    private TextField cardTitle;
    //the parent of each card which is a small card.
    private smallCardController parent ;
    //the content of each card will be shown on the text area of the small cards in columns .
    private TextArea smallcard;
    private ColumnController columncontroller;

    @FXML
    public void saveAndCloseAction(){
            if (this.columncontroller == null){
                this.smallcard.setText(cardText.getText());
            }
            else
                this.columncontroller.addSmallCard("hola");

    }

    @FXML
    public void deleteButtonAction(){
        parent.removeCardBtnAction();
    }

    public void setParent(smallCardController currentParent){
        this.parent = currentParent ;
    }

    public void setColumnController(ColumnController columnController) {
        this.columncontroller = columnController;
    }
}
