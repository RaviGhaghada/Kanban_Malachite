package boardpackage;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class BoardWriter{

    private Gson gson;

    private static  String filepath = "./src/main/resources/data/databoard.json";

    BoardWriter(){
        gson = new Gson();
    }
	

    void createBoard(Board board, String info){
        try (FileReader file = new FileReader(filepath)) {
            Object obj = new JSONParser().parse(file);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");

            Map value = new LinkedHashMap(3);
            value.put("info", info);
            value.put("date", LocalDateTime.now().toString());
            value.put("columns", gson.toJson(board.getColumns()));

            Map firstVersion = new LinkedHashMap(1);
            firstVersion.put("0", value);

            Map meta = new LinkedHashMap(1);
            meta.put("title", board.getTitle());
            meta.put("versions", firstVersion);
            meta.put("cardids", new JSONArray());
            meta.put("colids", new JSONArray());

            jo.put(board.getId(), meta);

            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
        }
    }

    void append(String info){
        Board board = BoardManager.get().getCurrentBoard();
        try (FileReader file = new FileReader(filepath)) {
            Object obj = new JSONParser().parse(file);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(board.getId());
            jo = (JSONObject) jo.get("versions");
            Set<String> s = jo.keySet();
            String maxkey = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt()+1);

            Map value = new LinkedHashMap(3);
            value.put("info", info);
            value.put("date", LocalDateTime.now().toString());
            value.put("columns", gson.toJson(board.getColumns()));

            jo.put(maxkey, value);

            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    void removeBoard(Board board){
        try (FileReader file = new FileReader(filepath)) {
            Object obj = new JSONParser().parse(file);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");
            jo.remove(board.getId());

            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



	/**
	*	for testing perpuses
	*/
	static void setPath(String path){
		filepath = path;
	}
	/**
	*	for testing perpuses
	*/
	static String getPath(){
		return filepath;
	}
}
