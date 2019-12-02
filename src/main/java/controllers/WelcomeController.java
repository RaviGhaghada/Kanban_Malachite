package controllers;

import boardpackage.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class WelcomeController {
    // buttons on welcome fxml
    public Button forwardButton;
    ArrayList<Board> allBoards;


    /**
     * On initialisaton, load all existing boards.
     *
     */
    @FXML
    protected void initialize() {
        forwardButton.setOpacity(0.3);
        allBoards = dataLoader.load();
    }


    public void forwardClick(ActionEvent actionEvent) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardmanager.fxml"));
        System.out.println("I was clicked!");
        BoardManagerController controller = new BoardManagerController(allBoards);
        loader.setController(controller);
        Parent root = (Parent) loader.load();
        forwardButton.getScene().setRoot(root);

    }

}
