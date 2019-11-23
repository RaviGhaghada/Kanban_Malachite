package app;

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
    public Button createButton;
    public Button resetButton;

    // user's selected budget

    // board data


    public void forwardClick(ActionEvent actionEvent) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/boardmanager.fxml"));
        // Create a controller instance
        BoardManagerController controller = new BoardManagerController();
        // Set it in the FXMLLoader
        loader.setController(controller);
        Parent root = (Parent) loader.load();
        forwardButton.getScene().setRoot(root);

    }

}
