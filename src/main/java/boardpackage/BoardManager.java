package boardpackage;

import java.util.ArrayList;

/**
 * A facade, singleton.
 * It represents an entity that controls
 * all Kanban board entities.
 * There can be only one shared instance of this entity
 * throughout this whole program.
 */
public class BoardManager{ 
    private static BoardManager bm = null;

    private ArrayList<Board> boards;
    private Board currentBoard = null;
    private Column currentColumn = null;
    private Card currentCard = null;

    /**
     * Constructor for the board manager
     * that loads the boards.
     */
    private BoardManager(){
        this.boards = new ArrayList<>();
	BoardManager.bm = this;
        // TODO: load boards with actual data from the json file
    }

    /**
     * Get the only instance of the BoardManager
     * @return BoardManager
     */
    static public BoardManager get(){
        if (bm == null){
            bm = new BoardManager();
        }
        return bm;
    }

    /**
     * Add a board to the board manager
     * @param board
     */
    void addBoard(Board board){
	if(board != null)
        	boards.add(board);
    }

    /**
     * Remove a board from the board manager
     * @param board
     */
    void removeBoard(Board board){
        boards.remove(board);
    }


    /**
     * Focus on a current board
     * @param board
     */
    public void setCurrentBoard(Board board) {
	if(board != null)
        	this.currentBoard = board;

    }

    /**
     * Get the current board in focus
     * @return Board
     */
    public Board getCurrentBoard() {
	if(currentBoard == null){
		if(boards.size() > 0){
			currentBoard = boards.get(0);
		}
		else{
			Board b = new Board("new board");
			addBoard(b);
			setCurrentBoard(b);
			
		}
		
	}
        return this.currentBoard;
    }

    /**
     * Get the current column in focus
     * @return Column
     */
    public Column getCurrentColumn() {
	if(currentColumn == null){
		Board b = getCurrentBoard();
		if(b.getColumns().size() > 0){
			currentColumn = b.getColumns().get(0);
		}
		else{
			Column c = new Column("new column");
			b.addColumn(c);
			setCurrentColumn(c);
			
		}
		
	}
        return this.currentColumn;
    }

    /**
     * Focus on a given column
     * @param currentColumn
     */
    public void setCurrentColumn(Column currentColumn) {
	if(currentColumn != null)
        	this.currentColumn = currentColumn;
    }

    /**
     * Get the current card in focus
     * @return Card
     */
    public Card getCurrentCard() {
	if(this.currentCard == null){
		Column col = getCurrentColumn();
		if(col.getCards().size()==0){
			col.addCard(new Card("new card"));
		}
		this.currentCard = col.getCards().get(0);		
		
		
	}
        return this.currentCard;
    }

    /**
     * Focus on a given card
     * @param currentCard
     */
    public void setCurrentCard(Card currentCard) {
	if(currentCard != null)
        	this.currentCard = currentCard;
    }

    /**
     * Get all boards held by the boardmanager
     * @return
     */
    public ArrayList<Board> getBoards(){
        return boards;
    }

    /**
     * Do not test this class.
     * It is merely for populating data.
     */
    public void populate(){
        Board b1 = new Board("Malachite");
        setCurrentBoard(b1);
        Column c1 = new Column("Backlog");
        setCurrentColumn(c1);
        new Card("Task01");
        new Card("Task02");
        new Card("Task03");
        new Card("SecretTask01");
        Column c2 = new Column("Developing");
        setCurrentColumn(c2);
        new Card("Task04");
        new Card("Task05");
        new Card("Task06");
        Column c3 = new Column("Finished");
        setCurrentColumn(c3);
        new Card("Task07");
        new Card("Task08");
        new Card("Task09");
    }

}
