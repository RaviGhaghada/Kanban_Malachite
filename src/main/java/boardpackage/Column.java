package boardpackage;
import java.util.*;

/**
 * Represent a column of a Kanban board
 */
public class Column {


    private Board parentBoard;
    private String id;
    private String title;
    private String role;
    private LinkedList<Card> cards;

    /**
     * Special constructor for a column
     * that is package private
     * It must only be used to create a column from data loaded
     * from a json file
     */
    Column (Board parent, String id, String title, String role){
        this.parentBoard = parent;
        parentBoard.addColumn(this);

        this.id = id;
        this.title = title;
        this.role = role;

        cards = new LinkedList<>();
    }

    /**
     * Constructor for a column
     * @param title heading of the column
     */
    public Column(String title){
        this.title = title;
        cards = new LinkedList<>();

        parentBoard = BoardManager.get().getCurrentBoard();
        parentBoard.addColumn(this);

        // TODO: request the logger to give us a new unique id for this column
    }

    /**
     * Change the position of a card within the column
     * @param card
     * @param finalIndex
     */
    public void moveCard(Card card, int finalIndex){
        if (cards.contains(card) && cards.size() > finalIndex && finalIndex >= 0) {
            cards.remove(card);
            cards.add(finalIndex, card);
        }
        // TODO: low priority notification for the logger
    }


    /**
     * Get the ID of a column
     * @return unique id of the column
     */
    public String getId(){
        return id;
    }

    /**
     * Get the list of cards
     * @return A list of cards
     */
    public LinkedList<Card> getCards(){
        return cards;
    }

    /**
     * Add a card to a column
     * Set to package private.
     * @param card card to be added
     */
    public void addCard(Card card){
	if(card != null)
        // TODO: notify logger
        	cards.add(card);

    }
	/**
	* return parent board
	*@return parentBoard
	*/
	public Board getParentBoard(){
		return parentBoard;
	}
    /**
     * Remove a card from a column
     * Set to package-private
     * @param card
     */
    void removeCard(Card card){
        cards.remove(card);
    }


    /**
     * Get the title of a column
     * @return title
     */
    public String getTitle(){
        return title;

    }


    /**
     * Set the title of a column
     * @param title
     */
    public void setTitle(String title){
        if (this.title != null && !this.title.equals(title)) { //to not log twice same thing
            this.title = title;
            // TODO: notify the logger
        }
    }

    /**
     * Set the id of the column
     * Ideally should only be done while reading a column from a .json file
     * This method is package-private.
     * @param s
     */
    void setId(String s){
        this.id = s;
    }

    /**
     * Get the role of the card
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the role of a card
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Set the id of the column
     * Ideally should only be done while reading a column from a .json file
     * This method is package-private.
     * @param board parent
     */
    void setParentBoard(Board board){
	if(board != null)
        	this.parentBoard = board;
    }


    public void delete(){
        this.parentBoard.removeColumn(this);
        this.parentBoard = null;
        if (this.equals(BoardManager.get().getCurrentColumn())){
            BoardManager.get().setCurrentColumn(null);
            BoardManager.get().setCurrentCard(null);
        }
        // TODO: notify the logger about deletion of this column
    }

}
