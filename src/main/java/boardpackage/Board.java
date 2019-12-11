package boardpackage;

import java.util.*;


/**
 * A class to represent a Kanban board.
 * board is capable of holding multiple columns
 * each of which holds multiple cards.
 */
public class Board{

    private String id;
    private String title;
    private LinkedList<Column> columns;


    /**
     * Special constructor for a board
     * that is package private.
     * It must only be used to create a board being loaded
     * from a json file
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

    }

    /**
     * Moves card with from one column to another
     * @param card to be moved
     * @param columnTo destination column
     */
    public void moveCardTos(Card card, Column columnTo){

        card.getParentColumn().removeCard(card);
        columnTo.addCard(card);
        card.setParentColumn(columnTo);

    }

    /**
     * Add a new column
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
     * Move column within the board internally
     * @param column Column to be moved
     * @param index New index of the board
     */
    public void moveColumn(Column column, int index){
        if(index < columns.size() && index >= 0 &&column != null){
            if(columns.remove(column))
                columns.add(index,column);
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
     * Get all the columns of the board
     * @return columns
     */
    public LinkedList<Column> getColumns(){
        return columns;
    }

    public void delete(){
        BoardManager.get().removeBoard(this);
        // TODO: notify the logger
    }

}
