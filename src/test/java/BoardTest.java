package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class BoardTest{
	@Test
	public void testMoveCardBetweenColumns(){
		Board board = new Board("b1","b");
		Column col1 = new Column("","c1");
		Column col2 = new Column("","c2");
		Card card = new Card("c1");
		board.moveCardBetweenColumns(null,null,null);
		col1.addCard(card);
		board.moveCardBetweenColumns(card,col1,col2);
		assertEquals(0,col1.getCards().size());
		assertEquals(1,col2.getCards().size());
		assertEquals(col2.getCards().get(0).getID(),card.getID());

	}
	@Test
	public void testAddColumn(){
		Board board = new Board("b1","b");
		Column col1 = new Column("","c1");
		assertEquals(0,board.getColumns().size());
		board.addColumn(col1);
		assertEquals(1,board.getColumns().size());
		assertEquals("c1",board.getColumns().get(0).getID());
	}
	@Test
	public void testRemoveColumn(){
		Board board = new Board("b1","b");
		Column col1 = new Column("","c1");
		board.addColumn(col1);
		assertEquals(1,board.getColumns().size());
		assertEquals("c1",board.getColumns().get(0).getID());
		board.removeColumn(col1);
		assertEquals(0,board.getColumns().size());
	}
	@Test
	public void testMoveColumn(){
		Board board = new Board("b1","b");
		Column col1 = new Column("","c1");
		Column col2 = new Column("","c2");
		Column col3 = new Column("","c3");
		board.moveColumn(null,0);
		board.moveColumn(col1,0);
		assertEquals(0,board.getColumns().size());
		board.addColumn(col1);
		board.addColumn(col2);
		board.moveColumn(col3,1);
		assertEquals(2,board.getColumns().size());
		board.addColumn(col3);
		board.moveColumn(col3,1);
		assertEquals(col3.getID(),board.getColumns().get(1).getID());
	}
	@Test
	public void testGetId(){
		Board board = new Board("b1","b");
		assertEquals("b1",board.getId());

	}
	@Test
	public void testSet_GetName(){
		Board board = new Board("b1","b");
		assertEquals("b",board.getName());
		board.setName("xd");
		assertEquals("xd",board.getName());
		board.setName("");
		assertEquals("",board.getName());
		board.setName(null);
		assertNull(board.getName());
	}






}
