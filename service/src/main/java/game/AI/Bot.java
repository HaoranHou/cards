package game.AI;

import game.util.Move;
import objects.Card;
import objects.Deck;
import game.util.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Bot {
    private final String name = "Haoran";

    private int cardsLeft;

    private HashMap<Integer, Integer> cardsSmallerThanValue;

    private Map<Card, Integer> cardValueMap;

    @Setter
    Player player;


    public Bot(int setOfCards) {
        cardsLeft = 52 * setOfCards;
        cardsSmallerThanValue = new HashMap<>();
        buildCardMaps();

        for (int i = 1; i <= 13; i++) {
            cardsSmallerThanValue.put(i, 4 * setOfCards * i);
        }
    }

    //Given the input card, update the number of cards smaller than itself in the deck.
    public void considerUsedCards(Card card) {
        int cardValue = cardValueMap.get(card);
        cardsLeft--;
        for (int i = cardValue; i <= 13; i++) {
            cardsSmallerThanValue.put(i, cardsSmallerThanValue.get(i) - 1);
        }
    }

    //Check the number of cards smaller or larger than the card in hand in the deck.
    //If more smaller cards bet on larger, and vice versa.
    public Move makeADecision() {
        int cardValue = cardValueMap.get(this.player.getCard());
        int smallerCards = cardsSmallerThanValue.get(cardValue);
        return smallerCards > (cardsLeft - smallerCards) ? Move.BET_ON_LARGER: Move.BET_ON_SMALLER;
    }

    private void buildCardMaps() {
        Deck reference = new Deck();
        cardValueMap = new HashMap<>();
        Card refCard;
        while (!reference.isEmpty()) {
            refCard = reference.dealOneCard();
            switch (refCard.getFaceValue()) {
                case ACE:
                    cardValueMap.put(refCard, 1);
                    break;
                case TWO:
                    cardValueMap.put(refCard, 2);
                    break;
                case THREE:
                    cardValueMap.put(refCard, 3);
                    break;
                case FOUR:
                    cardValueMap.put(refCard, 4);
                    break;
                case FIVE:
                    cardValueMap.put(refCard, 5);
                    break;
                case SIX:
                    cardValueMap.put(refCard, 6);
                    break;
                case SEVEN:
                    cardValueMap.put(refCard, 7);
                    break;
                case EIGHT:
                    cardValueMap.put(refCard, 8);
                    break;
                case NINE:
                    cardValueMap.put(refCard, 9);
                    break;
                case TEN:
                    cardValueMap.put(refCard, 10);
                    break;
                case JACK:
                    cardValueMap.put(refCard, 11);
                    break;
                case QUEEN:
                    cardValueMap.put(refCard, 12);
                    break;
                case KING:
                    cardValueMap.put(refCard, 13);
                    break;
                default:
                    cardValueMap.put(refCard, 0);
                    break;
            }
        }
    }
}
