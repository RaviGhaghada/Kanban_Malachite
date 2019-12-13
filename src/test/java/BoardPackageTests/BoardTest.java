package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class BoardTest{
	private static String pathReader;
	private static String pathWriter;	 
	@Before
	public void setUp(){
		pathReader = BoardManager.get().getBoardReader().getPath();
		pathWriter = BoardManager.get().getBoardWriter().getPath();
		BoardManager.get().getBoardReader().setPath("./src/test/resources/test.json");
		BoardManager.get().getBoardWriter().setPath("./src/test/resources/test.json");
	}
	@After
	public void tearDown(){
		BoardManager.get().setCurrentBoard(null);
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
		}
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);
	}
	
	@Test
	public void testMoveCardBetweenColumns(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		Column col1 = new Column("c1");
		BoardManager.get().setCurrentColumn(col1);
		Column col2 = new Column("c2");
		Card card = new Card("c1");
		board.moveCardTos(null,null);
		card.setParentColumn(col1);
		board.moveCardTos(card,col2);
		assertEquals(0,col1.getCards().size());
		assertEquals(1,col2.getCards().size());
		assertEquals(col2.getCards().get(0).getTitle(),card.getTitle());


	}
	@Test
	public void testAddColumn(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		Column col1 = new Column("c1");
		assertEquals(1,board.getColumns().size());
		assertEquals("c1",board.getColumns().get(0).getTitle());
	}
	@Test
	public void testRemoveColumn(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		Column col1 = new Column("c1");
		assertEquals(1,board.getColumns().size());
		assertEquals("c1",board.getColumns().get(0).getTitle());
		board.removeColumn(col1);
		assertEquals(0,board.getColumns().size());
	}
	@Test
	public void testMoveColumn(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		Column col1 = new Column("c1");
		Column col2 = new Column("c2");
		Column col3 = new Column("c3");
		board.moveColumn(null,0);
		board.moveColumn(col1,0);
		assertEquals(3,board.getColumns().size());
		board.removeColumn(col3);
		board.moveColumn(col3,1);
		assertEquals(2,board.getColumns().size());
		board.addColumn(col3);
		board.moveColumn(col3,1);
		assertEquals(col3.getTitle(),board.getColumns().get(1).getTitle());
	}
	@Test
	public void testgetTitle(){
		Board board = new Board("b1");
		BoardManager.get().setCurrentBoard(board);
		assertEquals("b1",board.getTitle());

	}
	@Test
	public void testSet_getTitle(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		assertEquals("b",board.getTitle());
		board.setTitle("xd");
		assertEquals("xd",board.getTitle());
		board.setTitle("");
		assertEquals("",board.getTitle());
		board.setTitle(null);
		assertNull(board.getTitle());
	}
	@Test
	public void testIdIni(){
		Board board = new Board("b");
		BoardManager.get().setCurrentBoard(board);
		assertNotNull(board.getId());
	}
	@Test
	public void testDelete(){
		Board b = new Board("");
		BoardManager.get().setCurrentBoard(b);
		assertEquals(true,BoardManager.get().getBoards().contains(b));
		b.delete();
		assertEquals(false,BoardManager.get().getBoards().contains(b));

	}





}
