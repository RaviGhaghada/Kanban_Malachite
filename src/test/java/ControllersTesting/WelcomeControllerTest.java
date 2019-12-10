package controllers;
import boardpackage.BoardManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class WelcomeControllerTest{

	@Test
	public void testInitialize(){
		WelcomeController wc = new WelcomeController();
		wc.initialize();
		assertNotNull(BoardManager.get());

	}


}
