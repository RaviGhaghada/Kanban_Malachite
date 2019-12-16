package controllers;

import boardpackage.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;

import java.util.HashMap;

/**
 * Controller for the Mello Statistics Popup.
 * Allows users to view all stored Kanban statistics associated with the current board.
 * The statistics are: Overall Velocity, Average Lead Time and Work In Progress.
 * Corresponding time graphs are also generated.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class StatisticsController {

    // Holders for statistics
    public HashMap<Integer, Double> velocityStatistics;
    public HashMap<Integer, Double> leadTimeStatistics;
    public HashMap<Integer, Double> wipStatistics;

    @FXML
    private Text messageBox;

    // Graph elements for statistics
    private NumberAxis xAxis1 = new NumberAxis();
    private NumberAxis yAxis1 = new NumberAxis();
    private NumberAxis xAxis2 = new NumberAxis();
    private NumberAxis yAxis2 = new NumberAxis();
    private NumberAxis xAxis3 = new NumberAxis();
    private NumberAxis yAxis3 = new NumberAxis();
    private LineChart<Number,Number> velocityChart = new LineChart<Number,Number>(xAxis1,yAxis1);
    private LineChart<Number,Number> avgLeadTimeChart = new LineChart<Number,Number>(xAxis2,yAxis2);
    private LineChart<Number,Number> wipChart = new LineChart<Number,Number>(xAxis3,yAxis3);

    // Each label to display description for each statistic
    public Label statLabel1, statLabel2, statLabel3;

    // Each label to display results for each statistic
    public Label statResult1, statResult2, statResult3;

    public int days = BoardManager.get().getCurrentBoard().getAge(); // days since the board was created

    /**
     * Initialises the statistics pane,
     * Loads all external data for generating statistics,
     * populates list of labels for use in displaying values,
     */
    @FXML
    public void initialize() {
        //messageBox.setText("Statistics for Your Board");
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
        for (int i =0; i <= days; i++) {
            velocityStatistics.put(i, Statistics.getDailyVelocity(i));
            leadTimeStatistics.put(i, Statistics.getDailyLeadTime(i));
            wipStatistics.put(i, Statistics.getDailyWIP(i));
        }
    }

    /**
     * Loads calculated statistics into labels to be displayed.
     */
    public void populateStatsFields() {
        statLabel1.setText("Overall Velocity: ");
        statResult1.setText(Statistics.calculateVelocity()+"");

        statLabel2.setText("Average Lead Time: ");
        statResult2.setText(Statistics.calculateAvgLeadTime()+"");

        statLabel3.setText("Work in Progress: ");
        statResult3.setText(Statistics.calculateWIP()+"");
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

}
