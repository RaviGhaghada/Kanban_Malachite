package boardpackage;

import java.util.LinkedList;
import java.util.List;

public class Statistics {

    /**
     *
     * @return velocity expressed as story points per week
     */
    public double calculateVelocity(){
        double storyPoints = 0.0; // number of story points on board total
        double numWeeks = 0.0; // number of weeks since the board was created
        LinkedList<Card> allCards = BoardManager.get().getCurrentBoard().getAllCards();
        for (Card card : allCards) {
            storyPoints += card.getStoryPoints();
        }
        return storyPoints/numWeeks;
    }

    /**
     * Calculates the average lead time for cards to be completed
     * on the current board.
     * @return Double average lead time
     */
    public double calculateAvgLeadTime() {
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCompletedCards();
        for (Card card : completedCards) {
            totalLeadTime += card.getLeadTime();
        }
        return totalLeadTime/completedCards.size();
    }

    /**
     * Tracks how many story points are in progress per week.
     * @return WIP expressed in story points
     */
    public double calculateWIP() {
        int totalDeliveryRate = 0;
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCompletedCards();
        for (Card card : completedCards) {
            totalLeadTime += card.getLeadTime();
        }
        totalDeliveryRate = BoardManager.get().getCurrentBoard().getDeliveryRate();
        return totalDeliveryRate * totalLeadTime;
    }

    public static double getDailyVelocity(int day) {
        return 0.0;
    }

    public static double getDailyLeadTime(int day) {
        return 0.0;
    }

    public static double getDailyWIP(int day) {
        return 0.0;
    }

}
