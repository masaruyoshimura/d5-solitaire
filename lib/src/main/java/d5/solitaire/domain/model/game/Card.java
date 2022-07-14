package d5.solitaire.domain.model.game;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * カード
 */
public class Card {

    private final Rank rank;

    private final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    public Rank rank() {
        return rank;
    }
    
    public Suit suit() {
        return suit;
    }
    
    @Override
    public String toString() {
        return suit.toString() + rank.toString();
    }

    public boolean isKing() {
        return rank.isKing();
    }

    public boolean isAdaptable(Card card) {
        return rank.isAdaptable(card.rank) && suit.isAdaptable(card.suit);
    }

    // flyweight
    private static Map<Suit, Map<Rank, Card>> cardsCache = Suit.stream().collect(Collectors.toMap(Function.identity(),
            s -> Rank.stream().collect(Collectors.toMap(Function.identity(), r -> new Card(r, s)))));

    public static Card of(Rank rank, Suit suit) {
        return cardsCache.get(suit).get(rank);
    }
}
