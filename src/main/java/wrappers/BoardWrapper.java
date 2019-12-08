package wrappers;

import boardpackage.Board;
import javafx.scene.layout.HBox;

/**
 * A class to represent a Kanban board.
 * Ideally a board should be capable of holding multiple columns
 * each of which holds multiple cards.
 */
public class BoardWrapper extends HBox {
    private Board board = null;

    public BoardWrapper(){
        super();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
