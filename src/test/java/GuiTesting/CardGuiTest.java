/**
package boardpackage;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.control.ListViewMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.*;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import app.Main;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.testfx.robot.TypeRobot.*;

public class CardGuiTest extends ApplicationTest{
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
	@Test
	public void addCardTest(){
		clickOn("#addCardBtn");
		int a = BoardManager.get().getCurrentColumn().getCards().size();
		clickOn("#saveButton");
		assertEquals(a+1,BoardManager.get().getCurrentColumn().getCards().size());
	}
	@Test
	public void deleteCardTest(){
		clickOn("Task01");
		int a = BoardManager.get().getCurrentColumn().getCards().size();
		clickOn("#deleteCard1");
		assertEquals(a-1,BoardManager.get().getCurrentColumn().getCards().size());

	}
	@Test
	public void editCardTest(){
		clickOn("Task02");
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
		write("1");
		clickOn("#saveAndClose");
		verifyThat("#cardDisplayText",hasText("1"));

	}
	

}
*/
