package boardpackage;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.control.ListViewMatchers.*;
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

public class BoardGuiTest extends ApplicationTest{
	
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
	@After
	public void tearDown() throws Exception{
		FxToolkit.hideStage();
   		release(new KeyCode[]{});
   		release(new MouseButton[]{});

	}
	@Override
	public void start(Stage s) throws Exception{
		s.show();
	}
	@Test
	public void addColumnTest(){
		int a = BoardManager.get().getCurrentBoard().getColumns().size();
		clickOn("#addButton");
		clickOn("#saveButton");
		clickOn("#okButton");
		clickOn("#cancelButton");
		assertEquals(a,BoardManager.get().getCurrentBoard().getColumns().size());
		clickOn("#addButton");
		write("col");
		clickOn("#saveButton");
		assertEquals(a+1,BoardManager.get().getCurrentBoard().getColumns().size());
		clickOn("#backButton");

	}
	@Test
	public void deleteBoardTest(){
		int a = BoardManager.get().getBoards().size();
		clickOn("#deleteButton");
		verifyThat("#boardListView",hasItems(a-1));
		assertEquals(a-1,BoardManager.get().getBoards().size());
		clickOn("#backButton");
	}
	@Test
	public void goBackTest(){
		clickOn("#backButton");
		verifyThat("#boardListView",hasItems(BoardManager.get().getBoards().size()));
	}



}


