package app;

import java.util.*;

public class Board{
    private LinkedList<Column> columns;
    private String id;
    private String name;

    public Board(String id, String name){
        this.id = id;
        this.name = name;
        this.columns = new LinkedList<Column>();
    }

    /**
     *moves card with from one column to another
     */
    public void moveCardBetweenColumns(Card card, Column columnFrom, Column columnTo){
	if(columnTo != null && columnFrom != null && card != null){
        	columnFrom.removeCard(card);
        	columnTo.addCard(card);
	}

    }

    public void addColumn(Column column){
        columns.add(column);
    }
    public void removeColumn(Column column){
        columns.remove(column);
    }
    public void moveColumn(Column column, int index){
	if(index < columns.size() && index >= 0&&column != null){
		if(columns.remove(column))
			columns.add(index,column);
	}
    }

    //getters
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public LinkedList<Column> getColumns(){
        return columns;
    }
    //setter
    public void setName(String name){
        this.name = name;
    }
}
