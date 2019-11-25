package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ColumnTest{
	@Test
	public void testAddCardNull(){
		Column c = new Column("h1","i1");
		c.addCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testAddCardNotNull(){
		Column c = new Column("h1","i1");
		Card card = new Card("c1");
		c.addCard(card);
		assertEquals(1,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getID());
	}
	@Test
	public void testAddCardDuplciate(){
		Column c = new Column("h1","i1");
		Card card = new Card("c1");
		c.addCard(card);
		c.addCard(card);
		assertEquals(2,c.getCards().size());
		assertEquals("c1",c.getCards().get(0).getID());
		assertEquals("c1",c.getCards().get(1).getID());
	}
	@Test
	public void testRemoveCardNotIn(){
		Column c = new Column("h1","i1");
		assertEquals(0,c.getCards().size());
		c.removeCard(new Card("1"));
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testRemoveCardNull(){
		Column c = new Column("h1","i1");
		assertEquals(0,c.getCards().size());
		c.removeCard(null);
		assertEquals(0,c.getCards().size());
	}
	@Test
	public void testMoveCardNull(){
		Column c = new Column("h1","i1");
		c.moveCard(null,0);
		assertEquals(0,c.getCards().size());
		Card card1 = new Card("c1");
		c.addCard(card1);
		c.moveCard(null,0);
		assertEquals(1,c.getCards().size());
	}
	@Test
	public void testMoveCardIn(){
		Column c = new Column("h1","i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.addCard(card2);
		c.moveCard(card1,1);
		assertEquals("c2",c.getCards().get(0).getID());
		assertEquals("c1",c.getCards().get(1).getID());
	}
	@Test
	public void testMoveCardOutside(){
		Column c = new Column("h1","i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.addCard(card2);
		c.moveCard(card1,10);
		assertEquals("c1",c.getCards().get(0).getID());
		assertEquals("c2",c.getCards().get(1).getID());
		c.moveCard(card1,-1);
		assertEquals("c1",c.getCards().get(0).getID());
		assertEquals("c2",c.getCards().get(1).getID());
	}
	@Test
	public void testMoveCardNotIn(){
		Column c = new Column("h1","i1");
		Card card1 = new Card("c1");
		Card card2 = new Card("c2");
		c.addCard(card1);
		c.moveCard(card2,0);
		assertEquals(1,c.getCards().size());

	}
	@Test
	public void testGetIDInit(){
		Column c1 = new Column("h1","i1");
		assertEquals("i1",c1.getID());
		Column c2 = new Column("h1",null);
		assertNull(c2.getID());
		Column c3 = new Column("h1","");
		assertEquals("",c3.getID());
	}
	@Test
	public void testGetIDAfterSet(){
		Column c = new Column("h1","i1");
		c.setID("abc");
		assertEquals("abc",c.getID());
		c.setID(null);
		assertEquals(null,c.getID());
		c.setID("");
		assertEquals("",c.getID());

	}
	@Test
	public void testGetHeadingInit(){
		Column c1 = new Column("h1","i1");
		assertEquals("h1",c1.getHeading());
		Column c2 = new Column(null,"i1");
		assertNull(c2.getHeading());
		Column c3 = new Column("","i1");
		assertEquals("",c3.getHeading());

	}
	@Test
	public void testGetHeadingAfterSet(){
		Column c = new Column("h1","i1");
		c.setHeading("abc");
		assertEquals("abc",c.getHeading());
		c.setHeading(null);
		assertEquals(null,c.getHeading());
		c.setHeading	("");
		assertEquals("",c.getHeading());

	}

}
