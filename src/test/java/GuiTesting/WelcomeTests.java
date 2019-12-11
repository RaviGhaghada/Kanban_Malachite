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

public class WelcomeTests extends ApplicationTest{
	@Before
	public void setUp() throws Exception{
		ApplicationTest.launch(Main.class);
	}
	@Override
	public void start(Stage s) throws Exception{
		s.show();
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
