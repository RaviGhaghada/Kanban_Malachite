package boardpackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardReader{

    private Gson gson;

    public BoardReader(){
        gson = new Gson();
    }

    public ArrayList<Board> loadBoardsFromJSON(){
        try {
            File file = new File("./src/main/resources/data/databoard.json");
            BufferedReader br = new BufferedReader(new FileReader(file));

            // TODO: remove this segment
            String st;
            String jsonString = "";
            while ((st = br.readLine()) != null) {
                jsonString += st;
            }

            //ArrayList<Board> alboard = new ArrayList<>();
            ArrayList<Board> boards = gson.fromJson(jsonString, new TypeToken<ArrayList<Board>>(){}.getType());
            System.out.println("HOLA " + gson.toJson(boards));


            return boards;


        } catch (IOException e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<Board>();
    }
}