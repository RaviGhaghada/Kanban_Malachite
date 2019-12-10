package boardpackage;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class BoardWriter{

    private Gson gson;

    public BoardWriter(){
        gson = new Gson();
    }

    public void insert(Board board, String version, String versionInfo){
        try {
            Object obj = new JSONParser().parse(new FileReader("./src/data.json"));
            JSONObject ptr = (JSONObject) obj;
            //ptr = ptr.get("boards");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getBoardJson(Board board){

        String jsonString = gson.toJson(board);
        return jsonString;
    }
}