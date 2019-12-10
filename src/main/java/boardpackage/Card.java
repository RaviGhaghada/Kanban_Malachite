package boardpackage;

import java.util.Objects;

/**
 * This class represents a card for a column
 * on a Kanban Board
 */
public class Card {

    private transient Column parentColumn;
    private String id;
    private String title;
    private String text = "";
    private String storypoints = "";

    /**
     * Special constructor for a card
     * that is package private
     * It must only be used to create pre-existing cards being loaded
     * from a json file
     */
    Card (Column parentColumn, String id, String title, String text, String storypoints){
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
            BoardManager.get().getBoardWriter().append(info);
            this.title = title;
        }
    }

    /**
     * Set the text (body) to a given string
     * @param text new text
     */
    public void setText(String text){
        if (!Objects.equals(text, this.text)){
            this.text = text;
            String info = String.format("Changed card %s (%s) 's description to %s", this.getText(), this.getId(), text);
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
    public String getStoryPoints() {
        return storypoints;
    }

    /**
     * Set the story points of a card
     * @param storypoints story points of a card
     */
    public void setStoryPoints(String storypoints) {
        if (!Objects.equals(this.storypoints,storypoints)){
            this.storypoints = storypoints;
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
}