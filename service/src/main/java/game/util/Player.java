package game.util;


import objects.Card;
import lombok.Getter;


public class Player {
    @Getter
    private final String name;

    @Getter
    private Card card;

    @Getter
    private Integer chips;

    public Player(String name, Integer startingChip) {
        this.name = name;
        this.chips = startingChip;
    }

    public void receiveOneCard(Card card) {
        this.card = card;
    }

    public void removeCard() {
        card = null;
    }

    public void wins(Integer chips) {
        this.chips += chips;
    }

    public void loses(Integer chips) {
        this.chips -= chips;
    }

    public boolean isBroke() {
        return this.chips < 5;
    }

    public Move betOnSmaller() {
        return Move.BET_ON_SMALLER;
    }

    public Move betOnLarger() {
        return Move.BET_ON_LARGER;
    }
}
