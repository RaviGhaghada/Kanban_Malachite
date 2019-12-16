package boardpackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Statistics {

    private ArrayList<String[]> versions;
    private static Statistics stats;

    private Statistics(){
        reload();
    }

    public void reload() {
         versions = BoardManager.get().getBoardReader().getAllVersionsMeta(); // all versions
    }

    /**
     * Gets the numbers of all versions of the board.
     * @return value of number of all versions of the board
     */
    public int getNumVersions(){
        return versions.size();
    }

    /**
     * Returns how many story points are completed per week.
     * @return velocity expressed as story points per week
     */
    public double calculateVelocity(){
        double storyPoints = 0.0; // number of story points completed in total
        double numWeeks = BoardManager.get().getCurrentBoard().getAge()/7.0;
        LinkedList<Card> allCompletedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : allCompletedCards) {
            storyPoints += card.getStoryPoints();
        }
        if (storyPoints == 0.0){
            return 0.0;
        }
        return Math.round(storyPoints/numWeeks * 100D) / 100D;
    }

    /**
     * Calculates the average lead time for cards to be completed
     * on the current board.
     * @return average lead time expressed in weeks
     */
    public double calculateAvgLeadTime() {
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += (int) DAYS.between(card.getCreationDate(versions), card.getCompletionDate(versions));
        }
        if (totalLeadTime == 0.0){
            return 0.0;
        }
        return Math.round(totalLeadTime/completedCards.size() * 100D) / 100D;
    }

    /**
     * Tracks how many story points are in progress per week.
     * @return WIP expressed in story points
     */
    public double calculateWIP() {
		double totalDeliveryRate = 0.0;
		double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getCurrentBoard().getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime +=  (int) DAYS.between(card.getCreationDate(versions), card.getCompletionDate(versions));
			totalDeliveryRate += card.getStoryPoints();	
        }
		totalDeliveryRate = totalDeliveryRate/ (BoardManager.get().getCurrentBoard().getAge()); // avrage of cards delivered per time unit
		totalLeadTime = totalLeadTime / completedCards.size(); // avarage time it takes to finish a card
        return Math.round(totalDeliveryRate * totalLeadTime * 100D) / 100D;
    }

    /**
     * Returns how many story points are completed per week per version.
     * @return velocity expressed as story points per week
     */
    public double getDailyVelocity(int version) {
        double storyPoints = 0.0; // number of story points on board total
        double numWeeks = (BoardManager.get().getCurrentBoard().getAge())/7.0;
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
    public double getDailyLeadTime(int version) {
        Double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getBoardVersion(version+"").getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += (int) DAYS.between(card.getCreationDate(versions), card.getCompletionDate(versions));
        }
        return totalLeadTime/completedCards.size();
    }

    /**
     * Tracks how many story points are in progress per week per version.
     * @return WIP expressed in story points
     */
    public double getDailyWIP(int version) {
        double totalDeliveryRate = BoardManager.get().getCurrentBoard().getDeliveryRate(versions);
        double totalLeadTime = 0.0;
        LinkedList<Card> completedCards = BoardManager.get().getBoardVersion(version+"").getCardsOf(Role.COMPLETED_WORK);
        for (Card card : completedCards) {
            totalLeadTime += (int) DAYS.between(card.getCreationDate(versions), card.getCompletionDate(versions));
        }
        return totalDeliveryRate * totalLeadTime;
    }

    public static Statistics get(){
        if (stats == null){
            stats = new Statistics();
        }
        return stats;
    }

}
