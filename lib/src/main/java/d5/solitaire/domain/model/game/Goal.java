package d5.solitaire.domain.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ゴール。スートごとのゴール。
 */
public class Goal {

    private final List<Card> goal; // FIXME card 全部持つ必要ない
    
    public Goal(){
        goal = List.of();
    }

    private Goal(List<Card> goal){
        this.goal = Collections.unmodifiableList(goal);
    }

    @Override
    public String toString() {
        return goal.size() == 0 ? "-" : ""+goal.size();
    }
    
    public int size() {
        return goal.size();
    }
    
    public Goal add(Card card) {
        var newGoal = new ArrayList<>(goal); // TODO リストに一つ足すだけなのに手続き的に書くのがダルい。そもそもlistで全部持つ必要ない。
        newGoal.add(card);
        return new Goal(newGoal);
    }
}
