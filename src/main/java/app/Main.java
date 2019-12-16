package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Mello GUI. Extends the Application class
 * and launches the GUI when run.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class Main extends Application {

    /**
     * Sets the first pane of the GUI, the Welcome Pane.
     * Sets the window size to be unable to be resized.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
        primaryStage.setTitle("Mello");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    /**
     * Launches the GUI.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
