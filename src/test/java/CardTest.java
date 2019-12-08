package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class CardTest{
	@Test
	public void testGetIDOnInit(){
		Card c = new Card("1");
		assertEquals("1",c.getID());
		Card c1 = new Card("");
		assertEquals("",c1.getID());
		Card c2 = new Card(null);
		assertNull(null,c2.getID());

	}
	@Test
	public void testGetTextOnInit(){
		Card c = new Card("1");
		assertEquals("",c.getText());
	}
	@Test
	public void testGetSetText(){
		Card c = new Card("1");
		assertEquals("",c.getText());
		c.editText("test");
		assertEquals("test",c.getText());
		c.editText(null);
		assertNull(c.getText());
		c.editText("");
		assertEquals("",c.getText());
	}
	@Test
	public void testGetSetID(){
		Card c = new Card("1");
		assertEquals("1",c.getID());
		c.setID(null);
		assertNull(c.getID());
		c.setID("-102");
		assertEquals("-102",c.getID());
		c.setID("");
		assertEquals("",c.getID());
		c.setID("10a2c98a");
		assertEquals("10a2c98a",c.getID());
	}





}
