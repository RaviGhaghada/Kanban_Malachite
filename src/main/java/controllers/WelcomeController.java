package controllers;

import boardpackage.BoardManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.ArrayList;

/**
 * Controller for the Mello Welcome Page.
 * Displays information about the application.
 * Has a button that allows user to enter the application.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */

public class WelcomeController {

    // Buttons on Welcome Page
    @FXML
    public Button forwardButton;

    /**
     * On initialisation, load all existing boards.
     */
    @FXML
    protected void initialize() {
        BoardManager.get(); // to construct single instance enclosed within
        BoardManager.get().populate(); // create one sample board

    }

    /**
     * Enables the forwardButton to open the Board Manager when clicked.
     */
    public void forwardClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardmanager.fxml"));
        System.out.println("I was clicked!");
        Parent root = (Parent) loader.load();
        Scene s = forwardButton.getScene();
        s.setRoot(root);

    }

}
