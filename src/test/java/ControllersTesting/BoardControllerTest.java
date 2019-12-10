package controllers;
import app.Main;
import boardpackage.BoardManager;
import boardpackage.Column;
import boardpackage.Board;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import javafx.fxml.FXML;

import org.junit.Before;
import org.junit.Test;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;







public class BoardControllerTest{
	

	@Before
	public void setUp(){
        	Thread t = new Thread(new Runnable() {
            	@Override
            	public void run() {
                	new JFXPanel();
            	}
        	});
        	t.start(); 
	}

	@Test
	public void testInitialize(){

		int a = BoardManager.get().getCurrentBoard().getColumns().size();
		BoardController bc = new BoardController();
		bc.initialize();
		assertEquals(a,BoardManager.get().getCurrentBoard().getColumns().size());
		assertNotNull(BoardManager.get().getCurrentBoard());
		assertNotNull(BoardManager.get());
		for(Column col : BoardManager.get().getCurrentBoard().getColumns())
			assertNotNull(col);
	}
	@Test
	public void testAddColumn(){

		BoardController bc = new BoardController();
		int a = BoardManager.get().getCurrentBoard().getColumns().size();
		bc.addColumnAction();
		assertEquals(a+1,BoardManager.get().getCurrentBoard().getColumns().size());

	}
	@Test
	public void testDeleteBoardAction(){

		BoardController bc = new BoardController();
		int boards = BoardManager.get().getBoards().size();
		Board b = BoardManager.get().getCurrentBoard();
		
		bc.deleteBoardAction();
		assertEquals(false, BoardManager.get().getBoards().contains(b));
		assertEquals(boards-1,BoardManager.get().getBoards().size());
	
	}



}
