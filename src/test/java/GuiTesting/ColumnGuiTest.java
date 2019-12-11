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
	@Before
	public void setUp() throws Exception{
		ApplicationTest.launch(Main.class);
		while(BoardManager.get().getBoards().size()>0){
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
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

	}
	@Test
	public void editTitleTest(){

		moveTo("Backlog").clickOn("#titleText");
		write("123");
		verifyThat(lookup("#titleText"),hasText("Backlog123"));
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

