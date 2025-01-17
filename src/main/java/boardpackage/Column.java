package boardpackage;
import java.util.*;

/**
 * Class for the Mello Column.
 * Each Column has a parent board, id, title, role and a linked list of cards
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class Column {

    private transient Board parentBoard;
    private String id;
    private String title;
    private Role role;
    private LinkedList<Card> cards;

    /**
     * Constructor for a column
     * Only to be used to create a column from data loaded
     * from a JSON file
     */
    Column (Board parent, String id, String title, Role role){
        this.parentBoard = parent;
        parent.addColumn(this);

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
        this.role = Role.BACKLOG;

        parentBoard = BoardManager.get().getCurrentBoard();
        parentBoard.addColumn(this);

        this.id = BoardManager.get().getBoardReader().getNewColId();
        String info = String.format("Added new column %s (%s)", this.title, this.id);
        BoardManager.get().getBoardWriter().append(info);
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
    void addCard(Card card){
        if(card != null) {
            cards.add(card);
        }
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
        if (title != null && !this.title.equals(title)) {
            String info = String.format("Renamed column %s (%s) 's title to %s", this.title, this.id, title);
            this.title = title;
            BoardManager.get().getBoardWriter().append(info);
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
    public Role getRole() {
        return role;
    }

    /**
     * Set the role of a card
     * @param role
     */
    public void setRole(Role role) {
        if (!role.equals(this.role)) {
            String info = String.format("Changed column %s (%s) 's role to %s", this.role, this.id, role);
            this.role = role;
            BoardManager.get().getBoardWriter().append(info);
        }
    }

    /**
     * Set the Board object of the column
     * Method to be used only inside the package
     * @param board parent
     */
    void setParentBoard(Board board){
        this.parentBoard = board;
    }


    /**
     * Get the Board object of the column
     * Method to be used only inside the package
     * @return board that hosts column
     */
    public Board getParentBoard(){
        return this.parentBoard;
    }

    /**
     * Delete the column from the parent board
     * Automatically removes cards as well
     */
    public void delete(){
        this.parentBoard.removeColumn(this);
        this.parentBoard = null;
        if (this.equals(BoardManager.get().getCurrentColumn())){
            BoardManager.get().setCurrentColumn(null);
            BoardManager.get().setCurrentCard(null);
        }
        String info = String.format("Removed column %s (%s)", this.title, this.id, title);
        BoardManager.get().getBoardWriter().append(info);
    }

}
