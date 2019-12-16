package boardpackage;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Class for the Mello BoardWriter.
 * Writes any changes to the boards to the storage in JSON.
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
class BoardWriter{

    private final JSONParser jsonParse;
    private final Gson gson;

    private static  String filepath = "./src/main/resources/data/databoard.json";

    /**
     * Constructor to initialise GSON and JSON.
     */
    BoardWriter(){
        gson = new Gson(); setupFile();
        jsonParse = new JSONParser();
    }

    /**
     * Sets up file to be read and written to.
     */
    private static void setupFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            if (br.readLine() == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("boards", new JSONObject());

                PrintWriter pw = new PrintWriter(filepath);
                pw.write(jsonObject.toJSONString());
                pw.flush();
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a board in the JSON file when a board is created
     * by the user on the user side.
     * @param board
     * @param info
     */
    void createBoard(Board board, String info){
        try (FileReader file = new FileReader(filepath)) {
            Object obj = new JSONParser().parse(file);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");

            Map value = new LinkedHashMap(3);
            value.put("info", info);
            value.put("date", LocalDateTime.now().toString());
            value.put("columns", jsonParse.parse(gson.toJson(board.getColumns())));

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

    /**
     * Adds information, such as a change to a board, to the JSON file.
     * @param info
     */
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
            value.put("columns", jsonParse.parse(gson.toJson(board.getColumns())));

            jo.put(maxkey, value);

            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes board from JSON when the board is deleted by the user on the user side.
     * @param board
     */
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
	*	Created to test setting of path.
	*/
	static void setPath(String path){
		filepath = path; setupFile();
	}

    /**
     *	Created to test getting of previously set path.
     */
	static String getPath(){
		return filepath;
	}
	
}
