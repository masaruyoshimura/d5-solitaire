package d5.solitaire.domain.model.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import io.vavr.Tuple2;

/**
 * カードを初期配置に並べるアレンジャー
 */
public class CardsArranger {
    
    public Tuple2<Lanes, Hand> arrange(Random random) {
        return arrange(new Cards(Suit.stream().flatMap(s -> Rank.stream().map(r -> Card.of(r, s)))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.shuffle(list, random);
                    return list;
                }))));
    }

    public Tuple2<Lanes, Hand> arrange(Cards cards) { // FIXME 手続き的
        HashMap<LaneIndex, Lane> lanes = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            var ret = cards.remove(i);
            lanes.put(LaneIndex.of(i-1), Lane.of(ret._2));
            cards = ret._1;
        }
        return new Tuple2<>(new Lanes(lanes), new Hand(cards.all()));
    }
}
