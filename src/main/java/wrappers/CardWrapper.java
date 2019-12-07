package wrappers;

import boardpackage.Card;
import javafx.scene.layout.HBox;

/**
 * This class represents a card for a column
 * on a Kanban Board
 */
public class CardWrapper extends HBox {
    private Card card = null;

    public CardWrapper(){
        super();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


}