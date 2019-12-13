
package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class CardTest{
	private static String pathReader;
	private static String pathWriter;	 
	@Before
	public void setUp(){
		pathReader = BoardManager.get().getBoardReader().getPath();
		pathWriter = BoardManager.get().getBoardWriter().getPath();
		BoardManager.get().getBoardReader().setPath("./src/test/resources/test.json");
		BoardManager.get().getBoardWriter().setPath("./src/test/resources/test.json");
		Board board = new Board("");
		BoardManager.get().setCurrentBoard(board);
		Column col = new Column("");
		BoardManager.get().setCurrentColumn(col);
	}
	@After
	public void tearDown(){
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
		}
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);
	}
	@Test
	public void testGetIDOnInit(){
		Card c = new Card("1");
		assertNotNull(c.getId());

	}
	@Test
	public void testGetTextOnInit(){
		Card c = new Card("1");
		assertEquals("",c.getText());
	}
	@Test
	public void testGetTitleOnInit(){
		Card c = new Card("1");
		assertEquals("1",c.getTitle());
	}
	@Test
	public void testGetSetText(){
		Card c = new Card("1");
		assertEquals("",c.getText());
		c.setText("test");
		assertEquals("test",c.getText());
		c.setText(null);
		assertNull(c.getText());
		c.setText("");
		assertEquals("",c.getText());
	}
	@Test
	public void testGetSetID(){
		Card c = new Card("1");
		assertNotNull(c.getId());
		c.setId(null);
		assertNull(c.getId());
		c.setId("-102");
		assertEquals("-102",c.getId());
		c.setId("");
		assertEquals("",c.getId());
		c.setId("10a2c98a");
		assertEquals("10a2c98a",c.getId());
	}
	@Test
	public void testSetGetParentColumn(){
		Card c = new Card("1");
		assertNotNull(c.getParentColumn());
		c.setParentColumn(null);
		c.setParentColumn(new Column("c"));
		assertEquals("c",c.getParentColumn().getTitle());
	}
	@Test
	public void testSetGetStoryPoint(){
		Card c = new Card("1");
		assertNotNull(c.getStoryPoints());
		c.setStoryPoints(1);
		assertEquals(1,c.getStoryPoints());
		c.setStoryPoints(-10);
		assertEquals(-10,c.getStoryPoints());
		c.setStoryPoints(2147483647);
		assertEquals(2147483647,c.getStoryPoints());
		c.setStoryPoints(-2147483648);
		assertEquals(-2147483648,c.getStoryPoints());
		c.setStoryPoints(0);
		assertEquals(0,c.getStoryPoints());
	}
	@Test
	public void testDelete(){
		Card c = new Card("1");
		Column col = c.getParentColumn();
		assertEquals(true,col.getCards().contains(c));
		c.delete();
		assertEquals(false,col.getCards().contains(c));


	}






}
