package boardpackage;

import java.util.ArrayList;

/**
 * Class for the Mello BoardManager. Controls all board entities.
 * There can only be one shared instance of this entity.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class BoardManager{
    //only one instance of this can be made in boardManager
    private static BoardManager bm = null;

    private BoardWriter bw;
    private BoardReader br;

    private ArrayList<Board> boards;
    private transient Board currentBoard = null;
    private transient Column currentColumn = null;
    private transient Card currentCard = null;

    /**
     * Constructor for the board manager that loads
     *  an array list of all the boards
     */
    private BoardManager(){
        this.bw = new BoardWriter();
        this.br = new BoardReader();
        this.boards = new ArrayList<>();
        ArrayList<String> boardids = br.getAllBoardIds();
        for (String id : boardids){
            boards.add(br.getBoard(id));
        }
    }

    /**
     * Returns the only instance of the BoardManager
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
        this.currentBoard = board;
    }

    /**
     * Get the current board in focus
     * @return Board
     */
    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    /**
     * Get the current column in focus
     * @return Column
     */
    public Column getCurrentColumn() {
        return this.currentColumn;
    }

    /**
     * Focus on a given column
     * @param currentColumn
     */
    public void setCurrentColumn(Column currentColumn) {
        this.currentColumn = currentColumn;
    }

    /**
     * Get the current card in focus
     * @return Card
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * Focus on a given card
     * @param currentCard
     */
    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * Returns an ArrayList list of all the Boards held by the BoardManager
     * @return boards
     */
    public ArrayList<Board> getBoards(){
        return boards;
    }

    /**
     * Sets boards from the BoardManager into the JSON file.
     * @param boards array list of board objects
     */
    void setBoards(ArrayList<Board> boards){
        this.boards = boards;
    }

    /**
     * Do not test this class. It is for populating data.
     */
    public Board populate(){
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
	return b1;
    }

    /**
     * Gets all versions of a single board from the JSON file.
     * @return ArrayList of changes on board per version
     */
    public ArrayList<String[]> getAllBoardVersionsMeta(){
        return br.getAllVersionsMeta();
    }

    /**
     * Returns the version of the board.
     * @param version the board represented as a string
     * @return the version number of the board
     */
    public Board getBoardVersion(String version){
        return br.getBoardVersion(version);
    }

    /**
     * Gets the BoardWriter for the BoardManager instance.
     * @return the Board Writer
     */
    BoardWriter getBoardWriter() {
        return bw;
    }

    /**
     * Gets the BoardReader for the BoardManager instance.
     * @return the Board Reader
     */
    BoardReader getBoardReader() {
        return br;
    }

}
