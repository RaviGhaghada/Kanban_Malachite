package boardpackage;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Statistics {

    public static int versions = BoardManager.get().getBoardReader().getAllVersionsMeta().size(); // number of all versions

    /**
     * Gets the numbers of all versions of the board.
     * @return value of number of all versions of the board
     */
    public static int getNumVersions(){
        return versions;
    }

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
            totalLeadTime += (int) DAYS.between(card.getCreationDate(), card.getCompletionDate());
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
            totalLeadTime +=  (int) DAYS.between(card.getCreationDate(), card.getCompletionDate());
        }
        return totalDeliveryRate * totalLeadTime;
    }

    /**
     * Returns how many story points are completed per week per version.
     * @return velocity expressed as story points per week
     */
    public static double getDailyVelocity(int version) {
        double storyPoints = 0.0; // number of story points on board total
        double numWeeks = (BoardManager.get().getBoardVersion(version+"").getAge())/7;
        LinkedList<Card> allCards = BoardManager.get().getBoardVersion(version+"").getAllCards();
        for (Card card : allCards) {
            storyPoints += card.getStoryPoints();
        }
        return storyPoints/numWeeks;
    }

    /**
     * Calculates the average lead time for cards to be completed
     * on the current board per version of board.
     * @return Double average lead time
     */
    public static double getDailyLeadTime(int version) {
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getBoardVersion(version+"").getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += (int) DAYS.between(card.getCreationDate(), card.getCompletionDate());
        }
        return totalLeadTime/completedCards.size();
    }

    /**
     * Tracks how many story points are in progress per week per version.
     * @return WIP expressed in story points
     */
    public static double getDailyWIP(int version) {
        double totalDeliveryRate = BoardManager.get().getCurrentBoard().getDeliveryRate();
        double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getBoardVersion(version+"").getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += (int) DAYS.between(card.getCreationDate(), card.getCompletionDate());
        }
        return totalDeliveryRate * totalLeadTime;
    }

}
