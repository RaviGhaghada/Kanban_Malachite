

import java.util.*;

public class Board{
	private LinkedList<Column> columns;
	private String id;
	private String name;
	public Board(String id, String name){
		this.id = id;
		this.name = name;
		columns = new LinkedList<Column>();
	}
	/**
	 *moves card with from one column to another 
	 */
	public void moveCardBetweenColumns(Card card, Column columnFrom, Column columnTo){
		columnFrom.removeCard(card);
		columnTo.addCard(card);
	
	}
	public void addColumn(Column column){
		columns.add(column);
	}
	public void removeColumn(Column column){
		columns.remove(column);
	}
	public void moveColumn(Column column, int index){
		columns.remove(column);
		columns.add(index,column);
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
