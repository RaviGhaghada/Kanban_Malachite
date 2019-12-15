package boardpackage;

import java.util.LinkedList;
import java.util.List;

public class Statistics {

    /**
     * Returns how many story points are completed per week.
     * @return velocity expressed as story points per week
     */
    public static double calculateVelocity(){
        double storyPoints = 0.0; // number of story points completed in total
        double numWeeks = BoardManager.get().getCurrentBoard().getAge()/7;
        LinkedList<Card> allCompletedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : allCompletedCards) {
            storyPoints += card.getStoryPoints();
        }
        if (storyPoints == 0.0){
            return 0.0;
        }
        return storyPoints/numWeeks;
    }

    /**
     * Calculates the average lead time for cards to be completed
     * on the current board.
     * @return average lead time expressed in weeks
     */
    public static double calculateAvgLeadTime() {
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += card.getAge();
        }
        if (totalLeadTime == 0.0){
            return 0.0;
        }
        return totalLeadTime/completedCards.size();
    }

    /**
     * Tracks how many story points are in progress per week.
     * @return WIP expressed in story points
     */
    public static double calculateWIP() {
        double totalDeliveryRate = BoardManager.get().getCurrentBoard().getDeliveryRate();
        double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += card.getAge();
        }
        return totalDeliveryRate * totalLeadTime;
    }

    public static double getDailyVelocity(int day) {
        double storyPoints = 0.0; // number of story points on board total
        double numWeeks = (BoardManager.get().getCurrentBoard().getAge() - (BoardManager.get().getCurrentBoard().getAge() - day))/7;
        LinkedList<Card> allCards = BoardManager.get().getBoardVersion("1").getAllCards();
        for (Card card : allCards) {
            storyPoints += card.getStoryPoints();
        }
        return storyPoints/numWeeks;
    }

    public static double getDailyLeadTime(int day) {
        return 0.0;
    }

    public static double getDailyWIP(int day) {
        return 0.0;
    }

}
