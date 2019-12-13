package wrappers;

import boardpackage.BoardManager;
import boardpackage.Column;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Represent a column of a Kanban board
 */
public class ColumnWrapper extends VBox {

    private Column column = null;

    public ColumnWrapper(){
        super();
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }


}
