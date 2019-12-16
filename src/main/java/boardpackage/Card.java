package boardpackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for the Mello Card.
 * Each card has a parent Column, id, title, text and Story points
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
public class Card {

    private transient Column parentColumn; // Column that the card belongs to
    private String id; // Identifying attribute of card
    private String title; // Title assigned to card by user
    private String text = ""; // Text within card, set by user
    private int storypoints = 0; // The story points associated with the card

    /**
     * Constructor for Card to be used to
     * create pre-existing cards being loaded
     * from a JSON file.
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
     * Card constructor
     * Title is necessary as input (not null)
     * @param title
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
     * Returns the title of a Card
     * @return card title
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
     * @return Identifying value for card
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
     * @param id id of card
     */
    void setId(String id){
        this.id = id;
    }

    /**
     * Method used to delete the card from the board manager.
     * Automatically deleted from the parent column
     */
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
     * Move a card from one column to another
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
     * The date when this card was put into completed role column
     * @return Date of completion
     */
    LocalDate getCompletionDate(ArrayList<String[]> versions) {
        if (parentColumn.getRole().equals(Role.COMPLETED_WORK)) {

            for (String[] version : versions) {
                Pattern p = Pattern.compile("Moved card [\\w ]+ \\(" + id + "\\) to [\\w ]+ \\(" + parentColumn.getId() + "\\) [\\w ]+");
                Matcher m = p.matcher(version[2]);
                if (m.find()) {
                    LocalDateTime ldt = LocalDateTime.parse(version[1]);
                    return LocalDate.from(ldt);
                }
            }
        }
        return LocalDate.now();
    }

    /**
     * The date when this card was created by the user
     * @return Date of creation
     */
    LocalDate getCreationDate(ArrayList<String[]> versions){
        for (String[] version : versions){
            Pattern p = Pattern.compile("Added new card [\\w ]+ \\("+ id + "\\) to [\\w ]+");
            Matcher m = p.matcher(version[2]);
            if (m.find()){
                return LocalDate.from(LocalDateTime.parse(version[1]));
            }
        }
        return LocalDate.now();
    }
}

