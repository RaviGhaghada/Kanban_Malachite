package boardpackage;

import com.google.gson.Gson;
import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class BoardWriter{

    private Gson gson;

    public BoardWriter(){
        gson = new Gson();
    }

    public void convertToJSON(ArrayList<Board> boards){

       try {
            String jsonString = gson.toJson(boards);

            FileWriter file = new FileWriter("./src/main/resources/data/databoard.json");
            File directory = new File("./src/main/resources/data/databoard.json");
            System.out.println(directory.getAbsolutePath());
            file.write(jsonString);
            file.close();
        //    System.out.println(jsonString);
            //System.out.println(file.getName());
        } catch (IOException e) {
            System.out.println("exception " + e.getMessage());
            e.printStackTrace();
        }
    }
}