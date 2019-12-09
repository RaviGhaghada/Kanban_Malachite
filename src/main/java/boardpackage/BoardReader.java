package boardpackage;

import com.google.gson.Gson;
import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class BoardReader{

    Gson gson = new Gson();

    public BoardReader(){}

    public void convertFromJSON(){


       try {
           File file = new File("./src/main/resources/data/databoard.json"); 
  
  BufferedReader br = new BufferedReader(new FileReader(file)); 
  
  String st; 
  String jsonString = "";
  while ((st = br.readLine()) != null) {
   jsonString += st; 
  } 

  System.out.println(jsonString);
  
  Board[] boards = gson.fromJson(jsonString, Board[].class);
  System.out.println(boards[0].getColumns().get(0).getCards());
    } catch (IOException e) {
        System.out.println("exception " + e.getMessage());
        e.printStackTrace();
    }
    }
}