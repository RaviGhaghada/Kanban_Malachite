
package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ColumnTest{
	@Test
	public void testAddCardNull(){
		Column c = new Column("i1");
		c.addCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testAddCardNotNull(){
		Column c = new Column("i1");
		Card card = new Card("c1");
		c.addCard(card);
		assertEquals(1,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getTitle());
	}
	@Test
	public void testAddCardDuplciate(){
		Column c = new Column("i1");
		Card card = new Card("c1");
		c.addCard(card);
		c.addCard(card);
		assertEquals(2,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c1",c.getCards().get(1).getTitle());
	}
	@Test
	public void testRemoveCardNotIn(){
		Column c = new Column("i1");
		assertEquals(0,c.getCards().size());
		c.removeCard(new Card("1"));
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testRemoveCardNull(){
		Column c = new Column("i1");
		assertEquals(0,c.getCards().size());
		c.removeCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testMoveCardNull(){
		Column c = new Column("i1");
		c.moveCard(null,0);
		assertEquals(0,c.getCards().size());
		Card card1 = new Card("c1");
		c.addCard(card1);
		c.moveCard(null,0);
		assertEquals(1,c.getCards().size());
	}
	@Test
	public void testMoveCardIn(){
		Column c = new Column("i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.addCard(card2);
		c.moveCard(card1,1);
		assertEquals("c2",c.getCards().get(0).getTitle());
		assertEquals("c1",c.getCards().get(1).getTitle());
	}
	@Test
	public void testMoveCardOutside(){
		Column c = new Column("i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.addCard(card2);
		c.moveCard(card1,10);
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c2",c.getCards().get(1).getTitle());
		c.moveCard(card1,-1);
		assertEquals("c1",c.getCards().get(0).getTitle());
		assertEquals("c2",c.getCards().get(1).getTitle());
	}
	@Test
	public void testMoveCardNotIn(){
		Column c = new Column("i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.moveCard(card2,0);
		assertEquals(1,c.getCards().size());

	}
	@Test
	public void testGetIDInit(){
		Column c1 = new Column("i1");
		assertNotNull(c1.getId());

	}
	@Test
	public void testGetIDAfterSet(){
		Column c = new Column("i1");
		c.setId("abc");
		assertEquals("abc",c.getId());
		c.setId(null);
		assertEquals(null,c.getId());
		c.setId("");
		assertEquals("",c.getId());

	}
	@Test
	public void testGetTitleInit(){
		Column c1 = new Column("i1");
		assertEquals("i1",c1.getTitle());

	}
	@Test
	public void testGetTitleAfterSet(){
		Column c = new Column("i1");
		c.setTitle("abc");
		assertEquals("abc",c.getTitle());
		c.setTitle(null);
		assertEquals(null,c.getTitle());
		String tit = c.getTitle();
		c.setTitle("");
		assertEquals(tit,c.getTitle());

	}
	@Test
	public void testGetSetRole(){
		Column c = new Column("i1");
		assertNotNull(c.getRole());
		c.setRole("role");
		assertEquals("role",c.getRole());
	}
	@Test
	public void testDelete(){
		Column c = new Column("i1");
		Board b = c.getParentBoard();
		assertEquals(true,b.getColumns().contains(c));
		c.delete();
		assertEquals(false,b.getColumns().contains(c));

	}

}
