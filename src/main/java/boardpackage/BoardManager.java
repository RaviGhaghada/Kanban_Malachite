package boardpackage;

import java.util.ArrayList;


public class BoardManager{ 
    private static BoardManager bm = null;

    private ArrayList<Board> boards;
    private Board currentBoard = null;
    private Column currentColumn = null;
    private Card currentCard = null;

    private BoardManager(){
        this.boards = new ArrayList<>();
        this.currentBoard= null;

    }

    static public BoardManager get(){
        if (bm == null){
            bm = new BoardManager();
        }
        return bm;
    }

//    public void addBoard(Board board) throws DuplicateNameException{
//        for (int i=0; i<this.boards.size(); i++)
//            if (this.boards.get(i).getName().equals(board.getName()))
//                throw new DuplicateNameException("Board of same name already exists.");
//        boards.add(board);
//    }

    public void addBoard(Board board){
        boards.add(board);
    }

    public void removeBoard(){
        if (this.currentBoard!= null)
            this.boards.remove(this.currentBoard);
        this.currentBoard= null;
    }

//    public void setCurrentBoard(Board board) throws UnknownBoardException{
//        if (!boards.contains(board)) {
//            throw new UnknownBoardException("Attempt to feed alien board to BoardManager");
//        }
//        this.currentBoard = board;
//    }

    public void setCurrentBoard(Board board){
        this.currentBoard = board;
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public Column getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(Column currentColumn) {
        this.currentColumn = currentColumn;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public ArrayList<Board> getBoards(){
        return boards;
    }

}
