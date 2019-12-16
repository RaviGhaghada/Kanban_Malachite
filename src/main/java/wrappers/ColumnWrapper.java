package wrappers;

import boardpackage.Column;
import javafx.scene.layout.VBox;

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
