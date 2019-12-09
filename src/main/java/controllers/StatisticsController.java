package controllers;

import javafx.fxml.FXML;
import java.util.HashMap;

public class StatisticsController {

    // Buttons for each stat-label to traverse through stats list.
    public HashMap<String,Integer> statistics;

    /**
     * Initialises the statistics pane,
     * Loads all external data for generating statistics,
     * populates list of labels for use in displaying values,
     */
    @FXML
    public void initialize() {
        populateStatistics();
    }


    protected void populateStatistics() {
        statistics = new HashMap<>(); // initialise HashMap.
        statistics.put("overall velocity ", calculateVelocity());
        statistics.put("average lead time ", calculateAvgLeadTime());
        statistics.put("average work in progress ", calculateWIP());
        System.out.println("Generating stats in series...\n Done!");
    }

    public int calculateVelocity() {
        return 0;
    }

    public int calculateAvgLeadTime() {
        return 0;
    }

    public int calculateWIP() {
        return 0;
    }


}
