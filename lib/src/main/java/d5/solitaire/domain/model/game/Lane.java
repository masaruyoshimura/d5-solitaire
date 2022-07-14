package d5.solitaire.domain.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

/**
 * レーン。
 */
public class Lane {

    private final List<Card> cards;
    
    private final Sequence sequence;
    
    // FIXME private
    public Lane(List<Tuple2<Card, Boolean>> lane) {
        cards = lane.stream().filter(t -> !t._2).map(t -> t._1).collect(Collectors.toList());
        sequence = new SequenceBuilder().build(lane.stream().filter(t -> t._2).map(t -> t._1).collect(Collectors.toList()));
    }
    
    public Lane(List<Card> cards, Sequence sequence) {
        this.cards = new ArrayList<>(cards);
        this.sequence = sequence == Empty.INSTANCE && this.cards.size() > 0 ?
                new NonEmpty(this.cards.remove(cards.size()-1), Empty.INSTANCE) : sequence;
    }
    
    public static Lane of(List<Card> lane) {
        return new Lane(Stream.ofAll(lane).zipWithIndex().map(t -> new Tuple2<>(t._1, t._2 == lane.size()-1)).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("★".repeat(cards.size()));
        ret.append(sequence.toString());
        return ret.toString();
    }

    public Tuple2<Lane, Lane> adapt(Lane other) {
        var result = sequence.adapt(other.sequence); // FIXME レーンが空列であることと、列の末尾が空列であることは違う（ロジックとして別のロジックを持つ）のでは？
        if(result != null) { // FIXME 操作不可の場合にnullを返したり、nullチェックしたり、をなんとかしたい
            return new Tuple2<>(new Lane(other.cards, other.sequence.remove(result)),new Lane(cards, sequence.append(result)));
        }
        return null;
    }
    
    public Card last() {
        return sequence.last();
    }
    
    public Lane removeLast() {
        return new Lane(cards, sequence.removeLast());
    }
    
    public Lane moveFromHandToLane(Card card){
        var result = sequence.moveFromHandToLane(card);
        if(result != null) {
            return new Lane(cards, result);
        }
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Lane)) return false;
        Lane other = (Lane)obj;
        return other.cards.equals(cards) && other.sequence.equals(sequence);
    }
}
