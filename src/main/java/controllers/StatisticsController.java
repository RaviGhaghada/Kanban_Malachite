package controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.util.HashMap;

public class StatisticsController {

    // Buttons for each stat-label to traverse through stats list.
    public HashMap<String, Double> statistics;

    @FXML
    private Text messageBox;

    // Each label to display description for each statistic.
    public Label statLabel1,statLabel2,statLabel3;

    // Each label to display results for each statistic.
    public Label statResult1, statResult2, statResult3;

    /**
     * Initialises the statistics pane,
     * Loads all external data for generating statistics,
     * populates list of labels for use in displaying values,
     */
    @FXML
    public void initialize() {
        messageBox.setText("Statistics for Your Board");
        populateStatistics();
        populateStatsFields();
    }

    protected void populateStatistics() {
        statistics = new HashMap<>(); // initialise HashMap.
        statistics.put("overall velocity ", calculateVelocity());
        statistics.put("average lead time ", calculateAvgLeadTime());
        statistics.put("average work in progress ", calculateWIP());
        System.out.println("Generating stats in series...\n Done!");
    }

    public void populateStatsFields() {
        statistics = new HashMap<>(); // initialise HashMap.
        statistics.put("overall velocity ", calculateVelocity());
        statistics.put("average lead time ", calculateAvgLeadTime());
        statistics.put("average work in progress ", calculateWIP());
        System.out.println("Generating stats in series...\n Done!");
    }


    public double calculateVelocity() {
        return 0.0;
    }

    public double calculateAvgLeadTime() {
        return 0.0;
    }

    public double calculateWIP() {
        return 0.0;
    }


}
