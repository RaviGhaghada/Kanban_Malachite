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
        Double totalLeadTime = 0.0;
        for (card : allCompletedCards) {
            totalLeadTime += calculateLeadTime(card);
        }
        return totalLeadTime/numberCardsCompleted;
    }

    /**
     * Tracks how many story points are in progress per week.
     * @return WIP expressed in story points
     */
    public double calculateWIP() {
        deliveryRate = calculateDeliveryRate();
        leadTime = calculateLeadTime();
        return deliveryRate * leadTime;
    }

    /**
     * Tracks
     * @return delivery rate
     */
    public double calculateDeliveryRate() {
        return 0.0;
    }

    /**
     * Tracks how long it takes for a card to move through the
     * stream.
     * @return lead time, expressed in weeks
     */
    public double calculateLeadTime(Card card) {
        return 0.0;
    }

}
