package boardpackage;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
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

public class CardGuiTest extends ApplicationTest{
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
			BoardManager.get().removeBoard(BoardManager.get().getBoards().get(0));
		}
		BoardManager.get().setCurrentBoard(null);
		BoardManager.get().setCurrentColumn(null);
		BoardManager.get().setCurrentCard(null);
		BoardManager.get().getBoardReader().setPath(pathReader);
		BoardManager.get().getBoardWriter().setPath(pathWriter);

	}
	@Test
	public void addCardTest(){
		clickOn("#addCardBtn");
		int a = BoardManager.get().getCurrentColumn().getCards().size();
		System.out.println("add " + BoardManager.get().getCurrentColumn());
		clickOn("#saveButton");
		assertEquals(a+1,BoardManager.get().getCurrentColumn().getCards().size());
	}
	@Test
	public void deleteCardTest(){
		System.out.println("del1 " + BoardManager.get().getCurrentColumn());
		clickOn("Task01");
		System.out.println("del2 " + BoardManager.get().getCurrentColumn());
		int a = BoardManager.get().getCurrentBoard().getColumns().get(0).getCards().size();
		clickOn("#deleteCard1");
		assertEquals(a-1,BoardManager.get().getCurrentBoard().getColumns().get(0).getCards().size());

	}
	@Test
	public void editCardTest(){
		clickOn("Task01");
		clickOn("#deleteCard1");
		clickOn("Task02");
		clickOn("#cardTitle");
		clickOn("#cardTitle");
		write("123");
		clickOn("#saveAndClose");
		clickOn("123");
		clickOn("#cardTitle");
		clickOn("#cardTitle");
		write("1");
		clickOn("#storypoints");
		write("b");
		clickOn("#saveAndClose");
		clickOn("#okButton");
		clickOn("#cardTitle");
		write("1");
		clickOn("#storypoints");
		clickOn("#storypoints");
		write("1");
		clickOn("#saveAndClose");
		verifyThat("#cardDisplayText",hasText("11"));

	}
	

}

