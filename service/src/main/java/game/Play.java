package game;


import game.util.Table;
import game.util.Player;
import org.apache.commons.lang3.StringUtils;
import game.AI.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Play {

    private static Table table;

    public static void main(String[] args) {
        System.out.println("Welcome to mini game!");
        System.out.println("RULES:");
        System.out.println("1. Choose number of sets of cards you want to play with. To ensure a speedy game, please pick a smaller number, like 1.");
        System.out.println("2. Choose number of chips you want to start with. To ensure a speedy game, please pick a smaller number, like $20.");
        System.out.println("3. For each round, both you and your opponent will be dealt with one card from the deck. Both of you need to bet on whether your card's face value is larger, or smaller, than your opponents'.");
        System.out.println("4. If both of your cards' face value are the same, the number of your chips will not change");
        System.out.println("5. Ace is 1, Jack is 11, Queen is 12, King is 13, the other cards equals their face value.");
        System.out.println("6. If one of you wins, the winner will win $5;");
        System.out.println("7. If both of you guess correctly the number of your chips will not change, but if both of you lose you will lose $5.");
        System.out.println("8. The game ends when one of the players has less than $5, or the cards run out.");
        System.out.println("9. The player with more chips wins.");
        Scanner keyboard = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        System.out.println("How many set of cards (52 cards per set) do you want to play?");
        int setOfCards = 0;
        while(setOfCards < 1) {
            System.out.println("Please enter a number larger than 0: ");
            setOfCards = keyboard.nextInt();
        }
        System.out.println("How many chips do you want to start with?");
        int numOfChips = 0;
        while(numOfChips < 5) {
            System.out.println("Please enter a number no less than 5: ");
            numOfChips = keyboard.nextInt();
        }
        Bot bot = new Bot(setOfCards);
        System.out.println("A player has entered the game.");
        System.out.println("Hi " + bot.getName() + ", welcome to the game!");
        names.add(bot.getName());
        System.out.println("Please enter your name: ");
        String playerName = null;
        while(StringUtils.isBlank(playerName)|| StringUtils.equalsIgnoreCase(bot.getName(), playerName)) {
            System.out.println("And of course, you can't be the second Haoran");
            playerName = keyboard.next();
        }
        System.out.println("Hi " + playerName + ", welcome to the game!");
        names.add(playerName);
        table = new Table(names, setOfCards, numOfChips);
        Player botPlayer = table.getPlayers().stream().filter(player -> StringUtils.equals(bot.getName(), player.getName())).findFirst().orElse(null);
        bot.setPlayer(botPlayer);

        Player winner = null;
        int numOfRounds = 1;
        System.out.println("Let's start the game!");
        System.out.println("-------------------------------------");
        System.out.println();
        while (!table.getDeck().isEmpty() && winner == null) {
            table.initiateNewRound();
            System.out.println("round: " + numOfRounds);
            System.out.println(table.getDeck().deckSize() + " cards remaining");
            numOfRounds++;
            table.dealOneCardForEachPlayer();
            table.getPlayers().stream().filter(player -> !StringUtils.equals(player.getName(), bot.getName())).collect(Collectors.toList()).forEach(player -> {
                System.out.println(player.getName() + ", your card is: " + player.getCard());
            });
            table.getPlayers().forEach(player -> {
                if (player == bot.getPlayer()) {
                    table.takeActionFromPlayer(player, bot.makeADecision());
                    System.out.println(bot.getName() + " has got the card and has made a decision.");
                } else {
                    String moveStr = null;
                    while (!"larger".equals(moveStr) && !"smaller".equals(moveStr)) {
                        System.out.println(player.getName() + ", do you bet your card smaller or larger than your opponent\'s card: ");
                        System.out.println("Type in 'larger' or 'smaller'");
                        moveStr = keyboard.next();
                        if ("larger".equals(moveStr)) {
                            table.takeActionFromPlayer(player, player.betOnLarger());
                        } else if ("smaller".equals(moveStr)) {
                            table.takeActionFromPlayer(player, player.betOnSmaller());
                        } else {
                            System.out.println("Invalid move.");
                        }
                    }
                }
            });
            List<Player> roundWinners = table.calculateResults();
            table.getPlayers().forEach(player -> {
                System.out.println(player.getName() + "'s card is: " + player.getCard() + " and " + table.getPlayerActions().get(player));
                bot.considerUsedCards(player.getCard());
            });
            StringBuilder sb = new StringBuilder();
            roundWinners.forEach(roundWinner -> {
                sb.append(roundWinner.getName());
                sb.append(", ");
            });
            if (roundWinners.isEmpty()) sb.append("No one ");
            sb.append("wins this round");
            System.out.println(sb.toString());
            System.out.println();
            table.getPlayers().forEach(player -> {
                System.out.println(player.getName() + " now has $" + player.getChips());
            });
            System.out.println("-------------------------------------");
            winner = table.checkResults();
        }
        System.out.println("Game over!");
        if (winner != null) {
            System.out.println(winner.getName() + " wins!");
        } else {
            Player player1 = table.getPlayers().get(0);
            Player player2 = table.getPlayers().get(1);
            if (Objects.equals(player1.getChips(), player2.getChips())) {
                System.out.println("It's a draw!");
                return;
            }
            String winnerName = player1.getChips() > player2.getChips() ? player1.getName() : player2.getName();
            System.out.println(winnerName + " wins!");
        }

    }
}
