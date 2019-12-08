package boardpackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
public class BoardManagerTest{


	@Test
	public void testAddBoardNormal(){
		BoardManager bm = new BoardManager();
		String t="";
		try{
			bm.addBoard("b1","1");
		}
		catch(DuplicateNameException e){
			t = e.getMessage();
		}
		assertEquals("", t);

		}

	@Test
	public void testAddBoardDuplicate(){
		BoardManager bm = new BoardManager();
		String t="";
		try{
			bm.addBoard("b1","1");
		}
		catch(DuplicateNameException e){
			t = e.getMessage();
		}
		assertEquals("", t);
		try{
			bm.addBoard("b1","1");
		}
		catch(DuplicateNameException e){
			t = e.getMessage();
		}
		assertEquals("Board of same name already exists.", t);
	}

	@Test
	public void testAddGeneral(){
		BoardManager bm = new BoardManager();
		String thisShoudlpass = "";
		try{
		bm.addBoard("b2","2");
			bm.addBoard("b3","3");
			bm.addBoard(null,null);
		}
		catch(DuplicateNameException e){
			thisShoudlpass = e.getMessage();
		}	
		assertEquals("",thisShoudlpass);


}

	@Test
	public void testGetCurrentBoardNull(){
		BoardManager bm = new BoardManager();
		try{
			bm.addBoard("b1","1");
			bm.addBoard("b2","2");
			bm.addBoard("b3","3");
		}
		catch(DuplicateNameException e){
			//this shoudlnt happen, is tested by previous tests
		}
		assertNull(bm.getCurrentBoard());
	}

	@Test
	public void testSetCurrentBoardNull(){
		BoardManager bm = new BoardManager();
		assertNull(bm.getCurrentBoard());
		String testNull="";
		try{
			bm.setCurrentBoard(null);
		}
		catch(UnknownBoardException e){
			testNull = e.getMessage();
		}
		assertEquals("Attempt to set current to inexistent board.",testNull);
		assertNull(bm.getCurrentBoard());
	}
	@Test
	public void testSetCurrentBoardNonExistant(){
		BoardManager bm = new BoardManager();
		String testNotExistent="";
		try{
			bm.setCurrentBoard("b4");
		}
		catch(UnknownBoardException e){
			testNotExistent = e.getMessage();
		}
		assertEquals("Attempt to set current to inexistent board.",testNotExistent);
		assertNull(bm.getCurrentBoard());
	}
	@Test
	public void testSetCurrentBoardProper(){
		BoardManager bm = new BoardManager();
		try{
			bm.addBoard("b3","3");
		}
		catch(DuplicateNameException e){
			//this shoudlnt happen, is tested by previous tests
		}
		String test="";
		try{
			bm.setCurrentBoard("b3");
		}
		catch(UnknownBoardException e){
			test = e.getMessage();
		}
		assertEquals("",test);
		assertEquals("b3",bm.getCurrentBoard().getId());

	}
	@Test
	public void testRemoveBoardwithCurrent(){
		BoardManager bm = new BoardManager();
		try{
			bm.addBoard("b3","3");
		}
		catch(DuplicateNameException e){
			//this shoudlnt happen, is tested by previous tests
		}
		try{
			bm.setCurrentBoard("b3");
		}
		catch(UnknownBoardException e){
			//again this is tested before
		}
		bm.removeBoard();
		assertNull(bm.getCurrentBoard());
		String test="";
		try{
			bm.setCurrentBoard("b3");
		}
		catch(UnknownBoardException e){
			test = e.getMessage();
		}
		assertEquals("Attempt to set current to inexistent board.",test);
		String test2="";
		try{
			bm.addBoard("b3","");
		}
		catch(DuplicateNameException e){
			test2 = e.getMessage();
		}
		assertEquals("",test2);

	}
	@Test
	public void testRemoveBoardwithooutCurrent(){
		BoardManager bm = new BoardManager();
		assertNull(bm.getCurrentBoard());
		bm.removeBoard();
		assertNull(bm.getCurrentBoard());
	}



}
