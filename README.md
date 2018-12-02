Deck of cards (and a mini game)
==================================  
@author haoran.hou

## Functions
* Create a deck of 52 cards (or more sets if you want) (```Deck.deck(int numOfSets)```)
* Randomly shuffle the said deck (```Deck.shuffle()```)
* Deal one card from the deck at a time (```Deck.dealOneCard()```)
* All code directly related to the assignment is in the ```core module```.  

## Shuffle Algorithm
 Implemented as described in https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 Starting from the bottom card of the deck, randomly pick one card above it, and switch the two cards. 
 Then move on to the one card above it.    

## Environment
* Git
* JDK 1.8
* Gradle 4.*

## Command
* Run tests: 
```gradle test```
* Build jar file
```gradle clean uberjar --refresh-dependencies```
* Run minigame (already built): 
```java -jar build/libs/cards-0.0.1.jar```

## Game Rules
1. Choose number of sets of cards you want to play with. To ensure a speedy game, please pick a smaller number, like 1.
2. Choose number of chips you want to start with. To ensure a speedy game, please pick a smaller number, like $20.
3. For each round, both you and your opponent will be dealt with one card from the deck. Both of you need to bet on whether your card's face value is larger, or smaller, than your opponents'.
4. If both of your cards' face value are the same, the number of your chips will not change.
5. Ace is 1, Jack is 11, Queen is 12, King is 13, the other cards equals their face value.
6. If one of you wins, the winner will win $5;
7. If both of you guess correctly the number of your chips will not change, but if both of you lose you will lose $5.
8. The game ends when one of the players has less than $5, or the cards run out.
9. The player with more chips wins.
