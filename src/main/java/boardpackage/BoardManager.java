package boardpackage;

import java.util.ArrayList;

public class BoardManager{
    private ArrayList<Board> boards;
    private Board current;

    public BoardManager(){
        this.boards = new ArrayList<>();
        this.current = null;
    }

    public void addBoard(String[] boarddetails) throws DuplicateNameException{
        Board board = new BoardDetails(boarddetails);
        
        for (int i=0; i<this.boards.size(); i++)
            if (this.boards[i].getName().equals(board.getName()))
                throw new DuplicateNameException();
    }

    public void removeBoard(){
        if (current != null)
            this.boards.remove(current)
        current = null;
    }

    public void setCurrentBoard(String id){
        for (int i=0; i<this.boards.size(); i++)
            if (boards[i].getId().equals(id)){
                this.current = boards[i];
                break;
            }
    }
}