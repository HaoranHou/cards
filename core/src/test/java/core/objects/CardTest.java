package core.objects;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CardTest {
    private Card card;

    @Test
    public void toStringTest() {
        card = new Card(Suit.Club, FaceValue.ACE);
        assertThat(card.toString(), equalTo(String.valueOf(FaceValue.ACE) + " of " + Suit.Club));
    }

    @Test
    public void equalsAndHashTest() {
        Set<Card> set = new HashSet<>();
        card = new Card(Suit.Club, FaceValue.ACE);
        set.add(card);
        card = new Card(Suit.Club, FaceValue.ACE);
        assertThat(set.contains(card), equalTo(Boolean.TRUE));
    }
}
