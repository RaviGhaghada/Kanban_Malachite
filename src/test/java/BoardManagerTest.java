package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
public class BoardManagerTest{
	@Test
	public void testAddRemoveBoard(){
		Board b = new Board("");
		BoardManager.get().addBoard(b);
		assertEquals(true,BoardManager.get().getBoards().contains(b));
		BoardManager.get().removeBoard(b);
		assertEquals(false,BoardManager.get().getBoards().contains(b));
		int size = BoardManager.get().getBoards().size();
		BoardManager.get().addBoard(null);
		assertEquals(size,BoardManager.get().getBoards().size());
		BoardManager.get().removeBoard(b);
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
		assertNotNull(BoardManager.get().getCurrentCard());
		BoardManager.get().setCurrentCard(null);
		assertNotNull(BoardManager.get().getCurrentCard());
		BoardManager.get().setCurrentCard(new Card("1"));
		assertEquals("1",BoardManager.get().getCurrentCard().getTitle());
		BoardManager.get().setCurrentCard(null);
		assertEquals("1",BoardManager.get().getCurrentCard().getTitle());

	}
	@Test
	public void testSetGetCurrentColumn(){
		assertNotNull(BoardManager.get().getCurrentColumn());
		BoardManager.get().setCurrentColumn(null);
		assertNotNull(BoardManager.get().getCurrentColumn());
		BoardManager.get().setCurrentColumn(new Column("1"));
		assertEquals("1",BoardManager.get().getCurrentColumn().getTitle());
		BoardManager.get().setCurrentColumn(null);
		assertEquals("1",BoardManager.get().getCurrentColumn().getTitle());
	}
	@Test
	public void testSetGetCurrentBoard(){
		assertNotNull(BoardManager.get().getCurrentBoard());
		BoardManager.get().setCurrentBoard(null);
		assertNotNull(BoardManager.get().getCurrentBoard());
		BoardManager.get().setCurrentBoard(new Board("1"));
		assertEquals("1",BoardManager.get().getCurrentBoard().getTitle());
		BoardManager.get().setCurrentBoard(null);
		assertEquals("1",BoardManager.get().getCurrentBoard().getTitle());
	}




}

