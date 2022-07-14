package d5.solitaire.domain.model.game;

import java.util.List;

import io.vavr.Tuple2;

/**
 * カードの集合。
 * 持ち札ではなく、生成後は取り分けるのみ。
 */
public class Cards {
    
    private final List<Card> cards;
    
    public Cards(List<Card> cards) {
        this.cards = cards;
    }
    
    public Tuple2<Cards, List<Card>> remove(int num) {
        return new Tuple2<>(new Cards(cards.subList(num, cards.size())), cards.subList(0, num));
    }

    public List<Card> all() {
        return cards;
    }
}
