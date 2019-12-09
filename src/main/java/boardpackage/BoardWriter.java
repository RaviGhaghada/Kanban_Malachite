package boardpackage;

import com.google.gson.Gson;
import java.io.*;
import java.io.FileWriter;

public class BoardWriter{

    Gson gson = new Gson();

    Board board = new Board("000001", "Board1");
    Column column = new Column (board, "00000", "jfdkbgj", "rhjbfjrf");

    public BoardWriter(){}

    public void convertToJSON(){

        BoardManager.get().populate();
      // BoardManager.get().getBoards();

       try {
        String jsonString = gson.toJson(BoardManager.get().getBoards());
        
    FileWriter file = new FileWriter("./src/main/resources/data/databoard.json");
    File directory = new File("./src/main/resources/data/databoard.json");
    System.out.println(directory.getAbsolutePath());
        file.write(jsonString);
        file.close();
        System.out.println(jsonString);
        //System.out.println(file.getName());
    } catch (IOException e) {
        System.out.println("exception " + e.getMessage());
        e.printStackTrace();
    }
    }
}