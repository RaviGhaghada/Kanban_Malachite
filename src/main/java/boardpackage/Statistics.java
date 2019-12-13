package boardpackage;

public class Statistics {

    // Statistic values
    private Double deliveryRate;
    private Double leadTime;

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
