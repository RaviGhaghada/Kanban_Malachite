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

    // Holders for statistics
    public HashMap<String, Double> velocityStatistics;
    public HashMap<String, Double> leadTimeStatistics;
    public HashMap<String, Double> wipStatistics;

    @FXML
    private Text messageBox;

    // Graph elements for statistics
    private CategoryAxis xAxis1 = new CategoryAxis();
    private NumberAxis yAxis1 = new NumberAxis();
    private CategoryAxis xAxis2 = new CategoryAxis();
    private NumberAxis yAxis2 = new NumberAxis();
    private CategoryAxis xAxis3 = new CategoryAxis();
    private NumberAxis yAxis3 = new NumberAxis();
    private LineChart<String,Number> velocityChart = new LineChart<String,Number>(xAxis1,yAxis1);
    private LineChart<String,Number> avgLeadTimeChart = new LineChart<String,Number>(xAxis2,yAxis2);
    private LineChart<String,Number> wipChart = new LineChart<String,Number>(xAxis3,yAxis3);

    // Statistic values
    private Double deliveryRate;
    private Double leadTime;

    // Each label to display description for each statistic
    public Label statLabel1, statLabel2, statLabel3;

    // Each label to display results for each statistic
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
        populateGraphs();
    }

    /**
     * Loads all calculated statistics into HashMaps.
     */
    protected void populateStatistics() {
        velocityStatistics = new HashMap<>(); // initialise HashMap.
        leadTimeStatistics = new HashMap<>(); // initialise HashMap.
        wipStatistics = new HashMap<>(); // initialise HashMap.
    }

    /**
     * Loads calculated statistics into labels to be displayed.
     */
    public void populateStatsFields() {
        statLabel1.setText("Overall Velocity: ");
        statResult1.setText(calculateVelocity()+"");

        statLabel2.setText("Average Lead Time: ");
        statResult2.setText(calculateAvgLeadTime()+"");

        statLabel3.setText("Work in Progress: ");
        statResult3.setText(calculateWIP()+"");
    }


    /**
     * Loads calculated statistics into graphs to be displayed.
     * Specifically, displays the overall velocity, average lead time, and
     * WIP over the time since the board was created.
     */
    public void populateGraphs() {
        velocityChart.setTitle("Overall Velocity This Week");
        XYChart.Series velocitySeries = new XYChart.Series();
        velocitySeries.setName("Overall Velocity");
        velocityStatistics.forEach((k,v) -> velocitySeries.getData().add(new XYChart.Data(k, v)));

        avgLeadTimeChart.setTitle("Average Lead Time This Week");
        XYChart.Series avgLeadTimeSeries = new XYChart.Series();
        avgLeadTimeSeries.setName("Average Lead Time");
        leadTimeStatistics.forEach((k,v) -> avgLeadTimeSeries.getData().add(new XYChart.Data(k, v)));

        wipChart.setTitle("Work in Progress This Week");
        XYChart.Series wipSeries = new XYChart.Series();
        wipSeries.setName("Work in Progress");
        wipStatistics.forEach((k,v) -> wipSeries.getData().add(new XYChart.Data(k, v)));
    }


    /**
     *
     * @return velocity expressed as story points per week
     */
    public double calculateVelocity() {
        double storyPoints = 0.0; // number of story points on board total
        double numWeeks = 0.0; // number of weeks since the board was created
        return storyPoints/numWeeks;
    }

    /**
     *
     * @return Double average lead time
     */
    public double calculateAvgLeadTime() {
        return 0.0;
    }

    /**
     *
     * @return WIP expressed in story points
     */
    public double calculateWIP() {
        deliveryRate = calculateDeliveryRate();
        leadTime = calculateLeadTime();
        return deliveryRate * leadTime;
    }

    /**
     *
     * @return delivery rate
     */
    public double calculateDeliveryRate() {
        return 0.0;
    }

    /**
     *
     * @return lead time
     */
    public double calculateLeadTime() {
        return 0.0;
    }


}
