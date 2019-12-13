package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class logTest{
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
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
		}
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);
	}
	@Test
	public void addBoard(){

	}

}
