package boardpackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;

public class BoardReader{

    private Gson gson;

    public BoardReader(){
        gson = new Gson();
    }

    public Board getBoardVersion(String id, String version){
        try (FileReader file = new FileReader("./src/data.json")) {
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(id);

            String title = jo.get("title").toString();

            jo = (JSONObject) jo.get("versions");
            jo  = (JSONObject) jo.get(version);
            jo = (JSONObject) jo.get("columns");

            String columnsjson = jo.toJSONString();
            LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

            return new Board(id, title, columns);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
        }
        return null;
    }

    public ArrayList<Board> getAllBoards(){
        ArrayList<Board> boards = new ArrayList<>();
        try {
            JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("./src/data.json"));
            jo = (JSONObject) jo.get("boards");
            Set<String> allIds = (Set<String>) jo.keySet();
            for (String id : allIds) {
                jo = (JSONObject) jo.get(id);

                String title = jo.get("title").toString();

                jo = (JSONObject) jo.get("versions");
                Set<String> s = jo.keySet();
                String latestVersion = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt());

                jo = (JSONObject) jo.get(latestVersion);
                jo = (JSONObject) jo.get("columns");

                String columnsjson = jo.toJSONString();
                LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

                boards.add(new Board(id, title, columns));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return boards;
    }

    public Board getBoard(String id){
        try {
            JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("./src/main/resources/data/databoard.json"));
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(id);

            String title = jo.get("title").toString();

            jo = (JSONObject) jo.get("versions");
            Set<String> s = jo.keySet();
            String latestVersion = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt());

            jo  = (JSONObject) jo.get(latestVersion);
            jo = (JSONObject) jo.get("columns");

            String columnsjson = jo.toJSONString();
            LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

            new Board(id, title, columns);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}