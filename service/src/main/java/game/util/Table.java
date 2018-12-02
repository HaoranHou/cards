package game.util;

import objects.Card;
import objects.Deck;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Getter
public class Table {

    private List<Player> players;

    private Map<Card, Integer> cardValueMap;

    private Deck deck;

    private Queue<Player> playerOrders;

    private Map<Player, Move> playerActions;

    private int chipsOnTable;

    private int defaultBet;

    public Table(List<String> names, int setOfCards, int startChip) {
        this.players = names.stream()
                .map(name -> new Player(name, startChip))
                .collect(Collectors.toList());
        deck = new Deck(setOfCards);
        deck.shuffle();
        buildCardMaps();
        playerActions = new HashMap<>();
        defaultBet = 5;
    }

    public void initiateNewRound() {
        playerOrders = new LinkedList<>();
        playerOrders.addAll(players);
        playerActions.clear();
        players.forEach(Player::removeCard);
        chipsOnTable = 0;
    }

    public void takeActionFromPlayer(Player player, Move move) {
        player.loses(defaultBet);
        chipsOnTable += defaultBet;
        playerActions.put(player, move);
    }

    public List<Player> calculateResults() {
        Player player1 = playerOrders.poll();
        int player1Score = cardValueMap.get(player1.getCard());
        Move player1Move = playerActions.get(player1);

        Player player2 = playerOrders.poll();
        int player2Score = cardValueMap.get(player2.getCard());
        Move player2Move = playerActions.get(player2);

        List<Player> winners = new LinkedList<>();
        if (player1Score == player2Score) {
            winners.add(player1);
            winners.add(player2);
        } else if (player1Score > player2Score) {
            if (player1Move.equals(Move.BET_ON_LARGER)) winners.add(player1);
            if (player2Move.equals(Move.BET_ON_SMALLER)) winners.add(player2);
        } else {
            if (player1Move.equals(Move.BET_ON_SMALLER)) winners.add(player1);
            if (player2Move.equals(Move.BET_ON_LARGER)) winners.add(player2);
        }

        if (winners.size() > 0) {
            int winnersShare = this.chipsOnTable / winners.size();
            winners.forEach(winner -> winner.wins(winnersShare));
        }
        return winners;
    }

    public Player checkResults() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        if(player1.isBroke() && player2.isBroke()) {
            return null;
        }

        if (player1.isBroke()) {
            return player2;
        }

        if (player2.isBroke()) {
            return player1;
        }
        return null;
    }

    public void dealOneCardForEachPlayer() {
        players.forEach(player -> {
            player.receiveOneCard(deck.dealOneCard());
        });
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
