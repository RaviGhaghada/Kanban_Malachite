package boardpackage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Class for the Mello Board.
 * A board is capable of holding multiple columns and
 * each column can hold a multiple cards.
 * Each board has an ID, title and a linked list of columns
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class Board {

    private String id; // identifying string for board
    private String title; // title assigned by user to board
    private LinkedList<Column> columns; // all of the columns on the board


    /**
     * Constructor for a board used only for loading json files.
     * @param id ID of the board
     * @param title title of the board
     * @param columns all columns on the board
     */
    Board(String id, String title, LinkedList<Column> columns){
        this.id = id;
        this.title = title;
        this.columns = columns;
    }

    /**
     * Constructor for the board class
     * @param title title of the board
     */
    public Board(String title){
        this.title = title;
        this.columns = new LinkedList<>();

        BoardManager.get().addBoard(this);
        this.id = BoardManager.get().getBoardReader().getNewBoardId();
        BoardManager.get().getBoardWriter().createBoard(this, "Created new board");
    }

    /**
     * Moves card with from one column to another
     * @param card to be moved
     * @param columnTo destination column
     */
    public void moveCardTos(Card card, Column columnTo){
	if(card != null && columnTo != null ){
        	String info = "Moved card %s (%s) from column %s (%s) to column %s (%s).";
        	info = String.format(info, card.getTitle(), card.getId(),
                card.getParentColumn().getTitle(), card.getParentColumn().getId(),
                columnTo.getTitle(), columnTo.getId());
        	card.getParentColumn().removeCard(card);
        	columnTo.addCard(card);
        	card.setParentColumn(columnTo);
        	BoardManager.get().getBoardWriter().append(info);
    	}
	}

    /**
     * Add a new column to the board
     * @param column new column to be added
     */
    void addColumn(Column column){
        columns.add(column);
    }

    /**
     * Remove a column from the board
     * @param column
     */
    void removeColumn(Column column){
        columns.remove(column);
    }

    /**
     * Moves column within the board from one position to another
     * @param column Column to be moved
     * @param index New index of the board
     */
    public void moveColumn(Column column, int index){
        if(index < columns.size() && index >= 0 &&column != null){
            String info = "Moved column %s (%s) to index %s";
            info = String.format(info, column.getTitle(), column.getId(), String.valueOf(index));
            if(columns.remove(column))
                columns.add(index,column);
            BoardManager.get().getBoardWriter().append(info);
        }
    }

    /**
     * Get the id of the board
     * @return id
     */
    public String getId(){
        return id;
    }

    /**
     * Set the id of the board
     * @param id id of the board
     */
    void setId(String id){
        this.id = id;
    }

    /**
     * Get the title of the board   
     * @return title of the board
     */
    public String getTitle(){
        return title;
    }
	/**
	* sets board title
	* @args title, new title
	*/
	public void setTitle(String title){
		this.title = title;	
	}

    /**
     * Get all the columns of the board
     * @return columns
     */
    public LinkedList<Column> getColumns(){
        return columns;
    }

    /**
     * Deletes board (including all of its elements) from BoardManager
     */
    public void delete(){
        BoardManager.get().removeBoard(this);
        if (BoardManager.get().getCurrentBoard() == this){
            BoardManager.get().setCurrentBoard(null);
            BoardManager.get().setCurrentColumn(null);
            BoardManager.get().setCurrentCard(null);
        }
        BoardManager.get().getBoardWriter().removeBoard(this);
    }

    /**
     * Gets all cards of a specified role.
     * @param role the role assigned to the returned cards
     * @return all cards of the given role
     */
    public LinkedList<Card> getCardsOf(Role role) {
        LinkedList <Card> cards = new LinkedList<>();
        for (Column col : columns){
            if (col.getRole().equals(role)){
                cards.addAll(col.getCards());
            }
        }
        return cards;
    }

    public LinkedList<Card> getAllCards(){
        LinkedList <Card> cards = new LinkedList<>();
        for (Column col : columns){
                cards.addAll(col.getCards());
        }
        return cards;
    }

    /**
     * Calculates the number of cards completed each day.
     * @return the delivery rate for the board
     */
     double getDeliveryRate(ArrayList<String[]> allVersionsMeta){

        TreeMap<String, LinkedList<Card>> map = new TreeMap<>();
        for (String[] versionmeta: allVersionsMeta){
            Board board = BoardManager.get().getBoardVersion(versionmeta[0]);
            LocalDate date = LocalDate.from(LocalDateTime.parse(versionmeta[1]));
            LinkedList<Card> cards = board.getCardsOf(Role.COMPLETED_WORK);
            map.put(date.toString(), cards);
        }

        HashSet<Card> allCards = new HashSet<>();
        for (String key : map.keySet()){
            LinkedList<Card> cards = map.get(key);
            map.put(key, cards.stream().filter(card -> allCards.add(card)).collect(Collectors.toCollection(LinkedList::new)));
        }

        return map.values().stream().mapToInt(list -> list.stream().mapToInt(c -> c.getStoryPoints()).sum()).average().orElse(0);
    }

    /**
     * Gets the age of the board (time between creation and current date)
     * @return Age of the board in days (rounded)
     */
    public int getAge(){
        ArrayList<String[]> versions = BoardManager.get().getAllBoardVersionsMeta();
        LocalDateTime creation = LocalDateTime.parse(versions.stream().min(Comparator.comparingInt(e -> Integer.parseInt(e[0]))).get()[1]);
        int x = (int) DAYS.between(creation, LocalDateTime.now());
        return (x>0)? x : 1;
    }
}
