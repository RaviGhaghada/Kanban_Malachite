package boardpackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for the Mello BoardReader.
 * Reads the stored information in JSON file about the board.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
class BoardReader{

    private Gson gson;

    private static  String filepath = "./src/main/resources/data/databoard.json";

    /**
     * Constructor that sets the Gson
     */
    BoardReader(){
        gson = new Gson();
        setupFile();
    }

    /**
     * Sets up file to be read
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
     * Returns the Board object for the version requested.
     */
    Board getBoardVersion(String version){
        try (FileReader file = new FileReader(filepath)) {
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            jo = (JSONObject) jo.get("boards");
            String id = BoardManager.get().getCurrentBoard().getId();
            jo = (JSONObject) jo.get(id);

            String title = jo.get("title").toString();

            jo = (JSONObject) jo.get("versions");
            jo  = (JSONObject) jo.get(version);

            JSONArray columnsjson = (JSONArray) jo.get("columns");
            LinkedList<Column> columns = gson.fromJson(columnsjson.toJSONString(), new TypeToken<LinkedList<Column>>(){}.getType());

            Board board = new Board(id, title, columns);

            for (Column col : board.getColumns()) {
                for (Card card : col.getCards()) {
                    card.setParentColumn(col);
                }
                col.setParentBoard(board);
            }

            return board;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the identifying attributes (ID) for all boards
     * that exist.
     */
    ArrayList<String> getAllBoardIds(){
        ArrayList<String> ids = new ArrayList<>();
        try (FileReader file = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            jo = (JSONObject) jo.get("boards");
            Set<String> allIds = (Set<String>) jo.keySet();
            ids.addAll(allIds);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * Returns the Board objects for all boards
     * that exist.
     */
    public ArrayList<Board> getAllBoards(){
        ArrayList<Board> boards = new ArrayList<>();
        try (FileReader file = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            JSONObject head = (JSONObject) jo.get("boards");
            if (head.size() != 0) {
                Set<String> allIds = (Set<String>) head.keySet();
                for (String id : allIds) {
                    jo = head;
                    jo = (JSONObject) jo.get(id);
                    JSONObject temp = jo;
                    String title = temp.get("title").toString();

                    jo = (JSONObject) jo.get("versions");
                    Set<String> s = jo.keySet();
                    String latestVersion = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt());

                    jo = (JSONObject) jo.get(latestVersion);
                    JSONArray columnsjson = (JSONArray) jo.get("columns");

                    LinkedList<Column> columns = gson.fromJson(columnsjson.toJSONString(), new TypeToken<LinkedList<Column>>() {
                    }.getType());

                    Board board = new Board(id, title, columns);

                    for (Column col : board.getColumns()) {
                        for (Card card : col.getCards()) {
                            card.setParentColumn(col);
                        }
                        col.setParentBoard(board);
                    }
                    boards.add(board);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return boards;
    }

    /**
     * Returns the requested Board object given an identifying attribute.
     * @param id represents the unique identifier associated with the board
     * @return Board object
     */
    Board getBoard(String id){
        try (FileReader file = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(id);

            String title = jo.get("title").toString();

            jo = (JSONObject) jo.get("versions");
            Set<String> s = jo.keySet();
            String latestVersion = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt());

            jo  = (JSONObject) jo.get(latestVersion);

            JSONArray columnsjson = (JSONArray) jo.get("columns");
            LinkedList<Column> columns = gson.fromJson(columnsjson.toJSONString(), new TypeToken<LinkedList<Column>>(){}.getType());

            Board board = new Board(id, title, columns);

            for (Column col : board.getColumns()){
                for (Card card : col.getCards()){
                    card.setParentColumn(col);
                }
                col.setParentBoard(board);
            }

            return board;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the new ID of the board after it has been reassigned.
     * @return string ID
     */
    String getNewBoardId(){
        String newid = "";
        try (FileReader fileReader = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(fileReader);
            jo = (JSONObject) jo.get("boards");
            Set <String> s = jo.keySet();
            newid = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().orElse(0)+1);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return newid;
    }

    /**
     * Gets the new ID of the card after it has been reassigned.
     * @return string ID
     */
    String getNewCardId(){
        String newid = "";
        try (FileReader fileReader = new FileReader(filepath)){
            Object obj = (JSONObject) new JSONParser().parse(fileReader);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");
            JSONObject head = (JSONObject) jo.get(BoardManager.get().getCurrentBoard().getId());
            jo = head;
            JSONArray cardids = (JSONArray) jo.get("cardids");
            if (cardids.size() == 0) {
                newid = "0";
            }
            else {
                newid = (String) cardids.get(cardids.size() - 1);
                newid = String.valueOf(Integer.valueOf(newid) + 1);
            }
            cardids.add(newid);
            head.replace("cardids", cardids);
            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();
            
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return newid;
    }

    /**
     * Gets the new ID of the column after it has been reassigned.
     * @return string ID
     */
    String getNewColId(){
        String newid = "";
        try (FileReader fileReader = new FileReader(filepath)){
            Object obj = (JSONObject) new JSONParser().parse(fileReader);
            JSONObject jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("boards");
            JSONObject head = (JSONObject) jo.get(BoardManager.get().getCurrentBoard().getId());
            jo = head;
            JSONArray colids = (JSONArray) jo.get("colids");
            if (colids.size()==0){
                newid = "0";
            }
            else {
                newid = (String) colids.get(colids.size() - 1);
                newid = String.valueOf(Integer.valueOf(newid) + 1);
            }
            colids.add(newid);
            head.replace("colids", colids);
            PrintWriter pw = new PrintWriter(filepath);
            pw.write(((JSONObject) obj).toJSONString());

            pw.flush();
            pw.close();

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return newid;

    }

    /**
     * Retrieves all versions of the board
     * @return ArrayList of board versions
     */
    ArrayList<String[]> getAllVersionsMeta(){
        ArrayList<String[]> versions = new ArrayList<>();
        try (FileReader fileReader = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(fileReader);
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(BoardManager.get().getCurrentBoard().getId());
            jo = (JSONObject) jo.get("versions");
            Set<String> vkeys = jo.keySet();
            for (String vno : vkeys){
                JSONObject version = (JSONObject) jo.get(vno);
                String[] value = new String[] {vno, (String) version.get("date"), (String) version.get("info")};
                versions.add(value);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (versions.size() > 1){
            versions.sort(Comparator.comparingInt(o -> Integer.parseInt(o[0])));
        }
        return versions;
    }


	/**
	* Test to set the file path.
	*/
	static void setPath(String path){
		filepath = path;
		setupFile();

	}

    /**
     * Test to get the file path as set previously.
     */
	static String getPath(){
		return filepath;
	}


}
