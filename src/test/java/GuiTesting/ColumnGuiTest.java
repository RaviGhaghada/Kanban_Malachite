package boardpackage;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;
import org.testfx.api.FxToolkit;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;	
import javafx.scene.input.KeyCodeCombination;

import app.Main;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.testfx.robot.TypeRobot.*;

public class ColumnGuiTest extends ApplicationTest{
	private static String pathReader;
	private static String pathWriter;
	@Before
	public void setUp() throws Exception{
		ApplicationTest.launch(Main.class);
		pathReader = BoardManager.get().getBoardReader().getPath();
		pathWriter = BoardManager.get().getBoardWriter().getPath();
		BoardManager.get().getBoardReader().setPath("./src/test/resources/test.json");
		BoardManager.get().getBoardWriter().setPath("./src/test/resources/test.json");
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().getBoards().get(0).delete();
		}
		BoardManager.get().populate();
		BoardManager.get().setCurrentColumn(null);
		BoardManager.get().setCurrentBoard(null);
		BoardManager.get().setCurrentCard(null);
		clickOn("#forwardButton");
		clickOn("Malachite");
		clickOn("Malachite");
	}
	@Override
	public void start(Stage s) throws Exception{
		s.show();
	}
	@After
	public void tearDown() throws Exception{
		FxToolkit.hideStage();
   		release(new KeyCode[]{});
   		release(new MouseButton[]{});
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().getBoards().get(0).delete();
		}
		BoardManager.get().setCurrentBoard(null);
		BoardManager.get().setCurrentColumn(null);
		BoardManager.get().setCurrentCard(null);
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);

	}
	@Test
	public void editTitleTest(){
		moveTo("Backlog").clickOn("#titleText");
		moveTo("Backlog").clickOn("#titleText");
		assertEquals("Backlog",BoardManager.get().getCurrentBoard().getColumns().get(0).getTitle());
		write("123");
		verifyThat(lookup("#titleText"),hasText("Backlog123"));
		clickOn("#melloPane");
		assertEquals("Backlog123",BoardManager.get().getCurrentBoard().getColumns().get(0).getTitle());
		clickOn("#backButton");
		
	}
	@Test
	public void deleteTest(){
		int a = BoardManager.get().getCurrentBoard().getColumns().size();
		clickOn("Backlog");
		clickOn("#deleteColumn");
		assertEquals(a-1,BoardManager.get().getCurrentBoard().getColumns().size());
		clickOn("#backButton");
	}

	


}

