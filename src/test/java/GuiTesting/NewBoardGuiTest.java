import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.control.ListViewMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.*;
import boardpackage.*;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import app.Main;
import org.junit.Test;
import org.junit.Before;

public class NewBoardGuiTest extends ApplicationTest{
	@Before
	public void setUp() throws Exception{
		ApplicationTest.launch(Main.class);
		clickOn("#forwardButton");
		clickOn("#addBoard");
		write("unique_board_27182");
		clickOn("#saveButton");
		clickOn("unique_board_27182");
		clickOn("unique_board_27182");
	}
	@Override
	public void start(Stage s) throws Exception{
		s.show();
	}
	@Test
	public void addColumnTest(){

	}
	@Test
	public void editColumnTest(){
	
	}
	@Test
	public void deleteBoardTest(){

	}
	@Test
	public void goBackTest(){

	}



}
