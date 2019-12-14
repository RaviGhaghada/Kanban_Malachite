package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;

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
	public void getBoardIdsTest(){
		int size = BoardManager.get().getBoardReader().getAllBoardIds().size();
		assertEquals(0,size);
		Board b = BoardManager.get().populate();
		Board b_new = BoardManager.get().getBoardReader().getBoard(b.getId());
		ArrayList<String> ids2 = BoardManager.get().getBoardReader().getAllBoardIds();
		assertEquals(b.getId(), b_new.getId());
		assertEquals(true,ids2.contains(b.getId()));
		assertEquals(size+1, BoardManager.get().getBoardReader().getAllBoardIds().size());
	}
	@Test
	public void getAllBoardsTest(){
		int size = BoardManager.get().getBoardReader().getAllBoards().size();
		assertEquals(0,size);
		Board b = new Board("");
		assertEquals(size+1,BoardManager.get().getBoardReader().getAllBoards().size());
		assertEquals(b.getId(),BoardManager.get().getBoardReader().getAllBoards().get(0).getId());
		Board b1 = BoardManager.get().populate();
		assertEquals(b1.getId(),BoardManager.get().getBoardReader().getAllBoards().get(1).getId());
	}
	@Test
	public void isBoardCorrectTest(){
		Board b_old = BoardManager.get().populate();
		Board b_new = BoardManager.get().getBoardReader().getBoard(b_old.getId());
		assertEquals(b_old.getId(),b_new.getId());	
		assertEquals(b_old.getTitle(),b_new.getTitle());
		assertEquals(b_old.getColumns().size(),b_new.getColumns().size());
		for(int i = 0; i < b_old.getColumns().size();i++){
			Column col_old = b_old.getColumns().get(i);
			Column col_new = b_new.getColumns().get(i);
			assertEquals(col_old.getParentBoard().getId(),col_new.getParentBoard().getId());
			assertEquals(col_old.getId(),col_new.getId());
			assertEquals(col_old.getRole(),col_new.getRole());
			assertEquals(col_old.getTitle(),col_new.getTitle());
			assertEquals(col_old.getCards().size(),col_new.getCards().size());
			for(int j = 0;j < col_old.getCards().size();j++){
				Card card_old = col_old.getCards().get(j);
				Card card_new = col_new.getCards().get(j);
				assertEquals(card_old.getId(),card_new.getId());
				assertEquals(card_old.getTitle(),card_new.getTitle());
				assertEquals(card_old.getText(),card_new.getText());				
				assertEquals(card_old.getStoryPoints(),card_new.getStoryPoints());
				assertEquals(card_old.getParentColumn().getId(),card_new.getParentColumn().getId());
			}
		}	
	}
	@Test
	public void getIdTest(){
		Board b = BoardManager.get().populate();
		BoardManager.get().setCurrentBoard(b);
		BoardManager.get().setCurrentColumn(b.getColumns().get(0));
		BoardManager.get().setCurrentCard(b.getColumns().get(0).getCards().get(0));
		String boardId = BoardManager.get().getBoardReader().getNewBoardId();
		String colId = BoardManager.get().getBoardReader().getNewColId();
		String cardId = BoardManager.get().getBoardReader().getNewCardId();
		assertEquals(true,boardId.matches("\\d+"));
		assertEquals(true,colId.matches("\\d+"));
		assertEquals(true,cardId.matches("\\d+"));
		assertEquals(false,BoardManager.get().getBoardReader().getAllBoardIds().contains(boardId));
		Board b_new = BoardManager.get().getBoardReader().getBoard(b.getId());
		for(Column col:b_new.getColumns()){
			assertNotEquals(col.getId(),colId);
			for(Card card:col.getCards()){
				assertNotEquals(card.getId(),cardId);
			}
		}
	}

}
