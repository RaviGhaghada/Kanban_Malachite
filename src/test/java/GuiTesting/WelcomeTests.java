package boardpackage;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.control.ListViewMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.*;
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

public class WelcomeTests extends ApplicationTest{
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
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
		}
		BoardManager.get().setCurrentColumn(null);
		BoardManager.get().setCurrentBoard(null);
		BoardManager.get().setCurrentCard(null);
		BoardManager.get().populate();

		
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
	public void welcomeTest(){
        	verifyThat("#forwardButton", hasText("GET STARTED"));

	}
	@Test
	public void forwardButtonTest(){
		int a = BoardManager.get().getBoards().size();
		clickOn("#forwardButton");
		verifyThat("#boardListView",hasItems(a));
        	verifyThat("#backButton", hasText("go back"));
	}


}

