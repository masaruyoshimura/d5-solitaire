package d5.solitaire.domain.model.game;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ゴールス。4つのゴールの集合。
 */
public class Goals {
    
    private final Map<Suit, Goal> goals; // ゴール4組

    public Goals(){
        goals = Suit.stream().collect(Collectors.toMap(Function.identity(), s -> new Goal()));
    }
    
    private Goals(Map<Suit, Goal> goals){
        this.goals = goals;
    }
    
    @Override
    public String toString() {
        return Suit.stream().map(s -> "ゴール" + s.toString() + ":" + goals.get(s).toString() + "\r\n")
                .collect(Collectors.joining());
    }
    
    public int size(Suit suit) {
        return goals.get(suit).size();
    }
    
    public Goals transfer(Card card) {
        var newGoals = new HashMap<>(this.goals); // TODO 手続き的だけどこの程度なら許容範囲？
        newGoals.put(card.suit(), goals.get(card.suit()).add(card));
        return new Goals(newGoals);
    }

    public boolean transferable(Card card) {
        return size(card.suit()) == card.rank().rank()-1;
    }
}
