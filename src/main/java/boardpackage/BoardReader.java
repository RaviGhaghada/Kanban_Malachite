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

class BoardReader{

    private Gson gson;

    private static  String filepath = "./src/main/resources/data/databoard.json";

    BoardReader(){
        gson = new Gson();
    }

    Board getBoardVersion(String version){
        try (FileReader file = new FileReader(filepath)) {
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            jo = (JSONObject) jo.get("boards");
            String id = BoardManager.get().getCurrentBoard().getId();
            jo = (JSONObject) jo.get(id);

            String title = jo.get("title").toString();

            jo = (JSONObject) jo.get("versions");
            jo  = (JSONObject) jo.get(version);

            String columnsjson = (String) jo.get("columns");
            LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

            return new Board(id, title, columns);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    public ArrayList<Board> getAllBoards(){
        ArrayList<Board> boards = new ArrayList<>();
        try (FileReader file = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(file);
            JSONObject head = (JSONObject) jo.get("boards");
            Set<String> allIds = (Set<String>) jo.keySet();
            for (String id : allIds) {
                jo = head;
                jo = (JSONObject) jo.get(id);
                JSONObject temp = (JSONObject) jo.clone();
                String title = temp.get("title").toString();

                jo = (JSONObject) jo.get("versions");
                Set<String> s = jo.keySet();
                String latestVersion = String.valueOf(s.stream().mapToInt(Integer::parseInt).max().getAsInt());

                jo = (JSONObject) jo.get(latestVersion);
                String columnsjson = (String) jo.get("columns");

                LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

                boards.add(new Board(id, title, columns));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return boards;
    }

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

            String columnsjson = (String) jo.get("columns");
            LinkedList<Column> columns = gson.fromJson(columnsjson, new TypeToken<LinkedList<Column>>(){}.getType());

            return new Board(id, title, columns);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        return versions;
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

    LocalDateTime getCardCreationDate(String id) {
        try (FileReader fileReader = new FileReader(filepath)){
            JSONObject jo = (JSONObject) new JSONParser().parse(fileReader);
            jo = (JSONObject) jo.get("boards");
            jo = (JSONObject) jo.get(BoardManager.get().getCurrentBoard().getId());
            jo = (JSONObject) jo.get("versions");
            Set<String> vkeys = jo.keySet();
            for (String vno : vkeys){
                JSONObject version = (JSONObject) jo.get(vno);
                String info = (String) version.get("info");
                Pattern p = Pattern.compile("Added new card \\w+ \\("+ id + "\\) to \\w+");
                Matcher m = p.matcher(info);
                if (m.find()){
                    String datetime = (String) version.get("date");
                    return LocalDateTime.parse(datetime);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
