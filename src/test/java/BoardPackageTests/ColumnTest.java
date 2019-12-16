package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class ColumnTest{
	private static String pathReader;
	private static String pathWriter;	 
	@Before
	public void setup(){
		pathReader = BoardManager.get().getBoardReader().getPath();
		pathWriter = BoardManager.get().getBoardWriter().getPath();
		BoardManager.get().getBoardReader().setPath("./src/test/resources/test.json");
		BoardManager.get().getBoardWriter().setPath("./src/test/resources/test.json");
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().getBoards().get(0).delete();
		}
		Board b = new Board("");
		BoardManager.get().setCurrentBoard(b);	
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
	public void testAddCardNull(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		c.addCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testAddCardNotNull(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		Card card = new Card("c1");
		assertEquals(1,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getTitle());
	}
	@Test
	public void testAddCardDuplciate(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		Card card = new Card("c1");
		c.addCard(card);
		assertEquals(2,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c1",c.getCards().get(1).getTitle());
	}
	@Test
	public void testRemoveCardNotIn(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		assertEquals(0,c.getCards().size());
		c.removeCard(new Card("1"));
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testRemoveCardNull(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		assertEquals(0,c.getCards().size());
		c.removeCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testMoveCardNull(){
		Column c = new Column("i1");
		assertEquals(0,c.getCards().size());
		BoardManager.get().setCurrentColumn(c);
		Card card = new Card("c1");
		assertEquals(1,c.getCards().size());
		card.move(null, 0);
		assertEquals(1,c.getCards().size());
	}
	@Test
	public void testMoveCardIn(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		card2.move(c,0);
		assertEquals("c2",c.getCards().get(0).getTitle());
		assertEquals("c1",c.getCards().get(1).getTitle());
	}
	@Test
	public void testMoveCardOutside(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		card1.move(c,10);
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c2",c.getCards().get(1).getTitle());
		card1.move(c,-1);
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c2",c.getCards().get(1).getTitle());
	}

	@Test
	public void testGetIDInit(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		assertNotNull(c.getId());

	}
	@Test
	public void testGetIDAfterSet(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		c.setId("abc");
		assertEquals("abc",c.getId());
		c.setId(null);
		assertEquals(null,c.getId());
		c.setId("");
		assertEquals("",c.getId());

	}
	@Test
	public void testGetTitleInit(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		assertEquals("i1",c.getTitle());

	}
	@Test
	public void testGetTitleAfterSet(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		c.setTitle("abc");
		assertEquals("abc",c.getTitle());
		c.setTitle(null);
		assertEquals("abc",c.getTitle());
		String tit = c.getTitle();
		c.setTitle("");
		assertEquals("",c.getTitle());

	}
	@Test
	public void testGetSetRole(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		c.setRole(Role.BACKLOG);
		assertEquals(Role.BACKLOG,c.getRole());
	}
	@Test
	public void testDelete(){
		Column c = new Column("i1");
		BoardManager.get().setCurrentColumn(c);	
		Board b = c.getParentBoard();
		assertEquals(true,b.getColumns().contains(c));
		c.delete();
		assertEquals(false,b.getColumns().contains(c));

	}

}

