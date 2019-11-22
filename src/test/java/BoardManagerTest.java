package app;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
public class BoardManagerTest{

@Test
public void testAddBoard(){
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
public void testSet_Get_RemoveCurrentBoard(){
BoardManager bm = new BoardManager();
String thisShoudlpass = "";
try{
bm.addBoard("b1","1");
bm.addBoard("b2","2");
bm.addBoard("b3","3");
}
catch(DuplicateNameException e){
	thisShoudlpass = e.getMessage();
}
assertEquals("",thisShoudlpass);
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
String testNotExistent="";
try{
bm.setCurrentBoard("b4");
}
catch(UnknownBoardException e){
	testNotExistent = e.getMessage();
}
assertEquals("Attempt to set current to inexistent board.",testNotExistent);
assertNull(bm.getCurrentBoard());
String test="";
try{
bm.setCurrentBoard("b3");
}
catch(UnknownBoardException e){
	test = e.getMessage();
}
assertEquals("",test);
assertEquals("b3",bm.getCurrentBoard().getId());
bm.removeBoard();
assertNull(bm.getCurrentBoard());
String c="";
try{
bm.setCurrentBoard("b3");
}
catch(UnknownBoardException e){
	c = e.getMessage();
}
assertEquals("Attempt to set current to inexistent board.",c);

String t1="";
try{
bm.addBoard("b3","");
}
catch(DuplicateNameException e){
	t1 = e.getMessage();
}
assertEquals("",t1);
}


}
