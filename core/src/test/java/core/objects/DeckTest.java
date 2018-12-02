package core.objects;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class DeckTest {

    private Deck deck;

    //Unique set of cards
    @Test
    public void testOneDeckWithoutShuffle() {
        deck = new Deck();
        assertThat(deck.deckSize(), equalTo(52));
        Set<Card> set = new HashSet<>();
        while (!deck.isEmpty()) {
            Card card = deck.dealOneCard();
            assertThat(set, not(IsCollectionContaining.hasItem(card)));
            set.add(card);
        }
    }

    @Test
    public void testTwoDecks() {
        deck = new Deck(2);
        assertThat(deck.deckSize(), equalTo(104));
    }

    //Get 5 random numbers from (1, 52) as indexes.
    //Create and shuffle a deck 5200 times, check the card at the index from each deck and save the frequencies of the card.
    //If the deck is truly randomly shuffled, the frequencies of each card that appear at the index should be normally
    //distributed with mean equals to 100.
    @Test
    public void testShuffle() {
        Set<Integer> indexes = new HashSet<>();
        while (indexes.size() < 5) {
            Integer randomIndex = new Random().nextInt(52);
            indexes.add(randomIndex);
        }
        Map<Integer, Map<Card, Integer>> cardFrequencies = new HashMap<>();
        for (int i = 0; i < 5200; i++) {
            deck = new Deck();
            deck.shuffle();
            Card cardAtIndex;
            for (int j = 0; j < 52; j++) {
                cardAtIndex = deck.dealOneCard();
                if (indexes.contains(j)) {
                    Map<Card, Integer> frequenciesAtIndex = cardFrequencies.getOrDefault(j, new HashMap<>());
                    frequenciesAtIndex.put(cardAtIndex, frequenciesAtIndex.getOrDefault(cardAtIndex, 0) + 1);
                    cardFrequencies.put(j, frequenciesAtIndex);
                }
            }
        }
        for (Map.Entry<Integer, Map<Card, Integer>> entries : cardFrequencies.entrySet()) {
            assertThat(entries.getValue().size(), equalTo(52));
            for (Map.Entry<Card, Integer> frequencies : entries.getValue().entrySet()) {
                assertThat(Math.abs(frequencies.getValue()-100), is(lessThan(40)));
            }
        }
    }
}
