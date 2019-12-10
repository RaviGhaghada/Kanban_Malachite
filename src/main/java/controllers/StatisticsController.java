package controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;

import java.util.HashMap;

public class StatisticsController {

    // Buttons for each stat-label to traverse through stats list.
    public HashMap<String, Double> statistics;

    @FXML
    private Text messageBox;
    private CategoryAxis xAxis1 = new CategoryAxis();
    private NumberAxis yAxis1 = new NumberAxis();
    private CategoryAxis xAxis2 = new CategoryAxis();
    private NumberAxis yAxis2 = new NumberAxis();
    private CategoryAxis xAxis3 = new CategoryAxis();
    private NumberAxis yAxis3 = new NumberAxis();
    private LineChart<String,Number> velocityChart = new LineChart<String,Number>(xAxis1,yAxis1);
    private LineChart<String,Number> avgLeadTimeChart = new LineChart<String,Number>(xAxis2,yAxis2);
    private LineChart<String,Number> wipChart = new LineChart<String,Number>(xAxis3,yAxis3);

    private Double deliveryRate;

    private Double leadTime;

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
    }


    public void populateGraphs() {
        velocityChart.setTitle("Overall Velocity This Week");

        XYChart.Series velocitySeries = new XYChart.Series();
        velocitySeries.setName("Overall Velocity");
        velocitySeries.getData().add(new XYChart.Data("Jan", 23));

        avgLeadTimeChart.setTitle("Average Lead Time This Week");

        XYChart.Series avgLeadTimeSeries = new XYChart.Series();
        avgLeadTimeSeries.setName("Average Lead Time");
        avgLeadTimeSeries.getData().add(new XYChart.Data("Jan", 23));

        wipChart.setTitle("Average Lead Time This Week");

        XYChart.Series wipSeries = new XYChart.Series();
        wipSeries.setName("Average Lead Time");
        wipSeries.getData().add(new XYChart.Data("Jan", 23));
    }


    /**
     *
     * @return velocity expressed as story points per week
     */
    public double calculateVelocity() {

        return 0.0;
    }

    /**
     *
     * @return lead time expressed in weeks
     */
    public double calculateAvgLeadTime() {

        return 0.0;
    }

    /**
     *
     * @return WIP expressed in story points
     */
    public double calculateWIP() {
        // delivery rate x lead time
        return 0.0;
    }


}
