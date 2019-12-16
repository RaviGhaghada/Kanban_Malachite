package boardpackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class represents a card for a column
 * on a Kanban Board
 */
public class Card {

    private transient Column parentColumn;
    private String id;
    private String title;
    private String text = "";
    private int storypoints = 0;

    /**
     * Special constructor for a card
     * that is package private
     * It must only be used to create pre-existing cards being loaded
     * from a json file
     */
    Card (Column parentColumn, String id, String title, String text, int storypoints){
        this.parentColumn = parentColumn;
        this.parentColumn.addCard(this);

        this.id = id;
        this.title = title;
        this.text = text;
        this.storypoints = storypoints;
    }

    /**
     * Constructor for a card
     * It must have a non-null title
     * @param title title of the card
     */
    public Card(String title){
        this.title = title;

        parentColumn = BoardManager.get().getCurrentColumn();
        parentColumn.addCard(this);

        this.id = BoardManager.get().getBoardReader().getNewCardId();
        String info = String.format("Added new card %s (%s) to column %s (%s)",
                this.title, this.id, parentColumn.getTitle(), parentColumn.getId());
        BoardManager.get().getBoardWriter().append(info);
    }


    /**
     * Get the title of a column
     * @return title of a column
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of a card
     * @param title new title
     */
    public void setTitle(String title) {
        if (!("".equals(title) || Objects.equals(this.title, title))){
            String info = String.format("Changed card %s (%s) 's title to %s", this.getTitle(), this.getId(), title);
            this.title = title;
            BoardManager.get().getBoardWriter().append(info);
        }
    }

    /**
     * Set the text (body) to a given string
     * @param text new text
     */
    public void setText(String text){
        if (!Objects.equals(text, this.text)){
            String info = String.format("Changed card %s (%s) 's description to %s", this.getText(), this.getId(), text);
            this.text = text;
            BoardManager.get().getBoardWriter().append(info);
        }
    }

    /**
     * Get the text description
     * @return text
     */
    public String getText(){
        return text;
    }


    /**
     * Get the story points
     * @return String
     */
    public int getStoryPoints() {
        return storypoints;
    }

    /**
     * Set the story points of a card
     * @param storypoints story points of a card
     */
    public void setStoryPoints(int storypoints) {
        if (!Objects.equals(this.storypoints,storypoints)){
            String info = String.format("Changed card %s (%s) 's storypoints from %s to %s",
                    title, id, this.storypoints, storypoints);
            this.storypoints = storypoints;
            BoardManager.get().getBoardWriter().append(info);
        }
    }

    /**
     * Get the id of a card
     * @return
     */
    public String getId(){
        return id;
    }

    /**
     * Return parent column of the card
     * @return the parent column of the card
     */
    public Column getParentColumn(){
        return parentColumn;
    }

    /**
     * Set parent column of the card.
     * Package-private only.
     * @param parentColumn parent column
     */
    void setParentColumn(Column parentColumn) {
        this.parentColumn = parentColumn;
    }


    /**
     * Set the id of a card.
     * Ideally, it should be used by the logger.
     * Is package-private.
     * @param id id of card
     */
    void setId(String id){
        this.id = id;
    }

    public void delete(){
        this.parentColumn.removeCard(this);
        this.parentColumn = null;
        if (this.equals(BoardManager.get().getCurrentCard())){
            BoardManager.get().setCurrentCard(null);
        }
        String info = String.format("Deleted card %s (%s)", this.getTitle(), this.getId());
        BoardManager.get().getBoardWriter().append(info);
    }

    /**
     * Move a card
     * @param col destination column
     * @param index index within the column
     */
    public void move(Column col, int index) {
        if (col != null) {
            if (index >= 0 && index < col.getCards().size()) {
                parentColumn.removeCard(this);
                parentColumn = col;
                parentColumn.getCards().add(index, this);

                String info = String.format("Moved card %s (%s) to column %s (%s) at index %s", title, id, col.getTitle(), col.getId(), index);
                BoardManager.get().getBoardWriter().append(info);
            }
        }
    }

    /**
     * The date when this card was put into completed
     */
    public LocalDate getCompletionDate() {
        if (parentColumn.getRole().equals(Role.COMPLETED_WORK)) {
            ArrayList<String[]> versions = BoardManager.get().getAllBoardVersionsMeta();

            for (String[] version : versions) {
                Pattern p = Pattern.compile("Moved card \\w+ \\(" + id + "\\) to column \\w+ \\(" + parentColumn.getId() + "\\) \\w+");
                Matcher m = p.matcher(version[2]);
                if (m.find()) {
                    LocalDateTime ldt = LocalDateTime.parse(version[1]);
                    return LocalDate.from(ldt);
                }
            }
        }
        return null;
    }

    public LocalDate getCreationDate(){
        ArrayList<String[]> versions = BoardManager.get().getAllBoardVersionsMeta();

        for (String[] version : versions){
            Pattern p = Pattern.compile("Added new card \\w+ \\("+ id + "\\) to \\w+");
            Matcher m = p.matcher(version[2]);
            if (m.find()){
                return LocalDate.from(LocalDateTime.parse(version[1]));
            }
        }
        return null;
    }
}

