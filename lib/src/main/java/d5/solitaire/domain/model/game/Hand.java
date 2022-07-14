package d5.solitaire.domain.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * 手札
 */
public class Hand {
    
    private final List<Card> hand;
    
    private final int index; // 手札のindex
    
    public Hand(List<Card> set) {
        this.hand = set;
        this.index = 0;
    }
    
    private Hand(List<Card> set, int index) {
        this.hand = set;
        this.index = index;
    }
    
    public Card card() {
        return hand.isEmpty() ? null : hand.get(index);
    }
    
    @Override
    public String toString() {
        return "手札トップ:" + (card() == null ? "無し" : (card().toString()));
    }
    
    public Hand remove() { // FIXME 手続き的
        var newSet = new ArrayList<>(hand);
        var index = this.index;
        newSet.remove(index);
        if (index >= hand.size()) {
            index--;
        }
        return new Hand(newSet, index);
    }
    
    public Hand roll() { // FIXME 手続き的
        var index = this.index;
        index++;
        if (index >= hand.size()) {
            index = 0;
        }
        return new Hand(hand, index);
    }
}
