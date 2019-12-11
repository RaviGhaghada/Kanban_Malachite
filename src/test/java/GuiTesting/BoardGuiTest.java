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

import app.Main;
import org.junit.Test;
import org.junit.Before;

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
	@Override
	public void start(Stage s) throws Exception{
		s.show();
	}
	@Test
	public void addColumnTest(){

	}
	@Test
	public void deleteBoardTest(){

	}
	@Test
	public void goBackTest(){

	}



}


