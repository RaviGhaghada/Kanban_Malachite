package boardpackage;

import java.util.ArrayList;


public class BoardManager{
    private ArrayList<Board> boards;
    private Board current;


    public BoardManager(){
        this.boards = new ArrayList<>();
        this.current = null;

    }

    public void addBoard(String id, String name) throws DuplicateNameException{
        Board board = new Board(id,name);
        for (int i=0; i<this.boards.size(); i++)
            if (this.boards.get(i).getName().equals(name))
                throw new DuplicateNameException("Board of same name already exists.");
        boards.add(board);
    }

    public void removeBoard(){
        if (current != null)
            this.boards.remove(current);
        current = null;
    }

    public void setCurrentBoard(String boardid) throws UnknownBoardException{
        for (int i=0; i<this.boards.size(); i++)
            if (this.boards.get(i).getId().equals(boardid)){
                this.current = this.boards.get(i);
                break;
            }
        throw new UnknownBoardException("Attempt to set current to inexistent board."); //<-<-
    }

    public Board getCurrentBoard() {
        return this.current;
    }
}
