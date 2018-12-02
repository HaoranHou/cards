package objects;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        cards.addAll(buildOneSetOfCards());
    }

    public Deck(int setsOfCards) {
        for (int numOfSets = 0; numOfSets < setsOfCards; numOfSets++) {
            cards.addAll(buildOneSetOfCards());
        }
    }

    public int deckSize() {
        return cards.size();
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(this.cards);
    }

    public Card dealOneCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    /**
     * The modern version of the Fisher–Yates shuffle.
     * [...]The algorithm described by Durstenfeld differs from that given by Fisher and Yates in a small but significant way.
     * [...] Durstenfeld's solution is to move the "struck" numbers to the end of the list by swapping them with the last unstruck number at each iteration.
     * This reduces the algorithm's time complexity to O(n), compared to O(n2) for the naïve implementation.
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
     */
    public void shuffle() {
        int size = cards.size();
        for (int index = size - 1; index > 0; index--) {
            int indexToSwitch = new Random().nextInt(index + 1);
            switchCards(indexToSwitch, index);
        }
    }

    private List<Card> buildOneSetOfCards() {
        List<Card> oneSetOfCards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (FaceValue value : FaceValue.values()) {
                oneSetOfCards.add(new Card(suit, value));
            }
        }

        return oneSetOfCards;
    }

    private void switchCards(int leftIndex, int rightIndex) {
        Card cardToSwitch = cards.get(leftIndex);
        cards.set(leftIndex, cards.get(rightIndex));
        cards.set(rightIndex, cardToSwitch);
    }
}
