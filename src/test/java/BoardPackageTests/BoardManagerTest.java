package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
public class BoardManagerTest{

	private static String pathReader;
	private static String pathWriter;	 
	@Before
	public void setUp(){
		pathReader = BoardManager.get().getBoardReader().getPath();
		pathWriter = BoardManager.get().getBoardWriter().getPath();
		BoardManager.get().getBoardReader().setPath("./src/test/resources/test.json");
		BoardManager.get().getBoardWriter().setPath("./src/test/resources/test.json");
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().getBoards().get(0).delete();
		}
	}
	@After
	public void tearDown(){
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().getBoards().get(0).delete();
		}
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);
	}
	@Test
	public void testAddRemoveBoard(){
		Board b = new Board("");
		assertEquals(true,BoardManager.get().getBoards().contains(b));
		b.delete();
		assertEquals(false,BoardManager.get().getBoards().contains(b));
		int size = BoardManager.get().getBoards().size();
		assertEquals(size,BoardManager.get().getBoards().size());
		b.delete();
		assertEquals(false,BoardManager.get().getBoards().contains(b));
		assertEquals(size,BoardManager.get().getBoards().size());
	}
	@Test
	public void testNotNullGet(){
		assertNotNull(BoardManager.get());
		assertNotNull(BoardManager.get().getBoards());
	}
	@Test
	public void testSameGet(){
		Board b = new Board("");
		BoardManager.get().addBoard(b);
		assertEquals(true,BoardManager.get().getBoards().contains(b));
		assertEquals(BoardManager.get(),BoardManager.get()); // to check if get returns same board
	}
	@Test
	public void testSetGetCurrentCard(){
		BoardManager.get().setCurrentCard(null);
		assertNull(BoardManager.get().getCurrentCard());
		BoardManager.get().setCurrentBoard(new Board(""));
		BoardManager.get().setCurrentColumn(new Column(""));
		BoardManager.get().setCurrentCard(new Card("1"));
		assertEquals("1",BoardManager.get().getCurrentCard().getTitle());


	}
	@Test
	public void testSetGetCurrentColumn(){
		BoardManager.get().setCurrentColumn(null);
		assertNull(BoardManager.get().getCurrentColumn());
		BoardManager.get().setCurrentBoard(new Board(""));
		BoardManager.get().setCurrentColumn(new Column("1"));
		assertEquals("1",BoardManager.get().getCurrentColumn().getTitle());

	}
	@Test
	public void testSetGetCurrentBoard(){
		BoardManager.get().setCurrentBoard(null);
		assertNull(BoardManager.get().getCurrentBoard());
		BoardManager.get().setCurrentBoard(new Board("1"));
		assertEquals("1",BoardManager.get().getCurrentBoard().getTitle());

	}




}

