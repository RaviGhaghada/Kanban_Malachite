package controllers;

import boardpackage.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public Button forwardButton;

    // Stores all boards in the application
    ArrayList<Board> allBoards;


    /**
     * On initialisation, load all existing boards.
     */
    @FXML
    protected void initialize() {
        //allBoards = dataLoader.load();
    }

    /**
     * Enables the forwardButton to open the Board Manager when clicked.
     */
    // TODO: set the board manager to current board manager
    public void forwardClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardmanager.fxml"));
        System.out.println("I was clicked!");
        Parent root = (Parent) loader.load();
        forwardButton.getScene().setRoot(root);

    }

}
