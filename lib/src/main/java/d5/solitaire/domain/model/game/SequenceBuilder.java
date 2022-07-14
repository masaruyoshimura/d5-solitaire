package d5.solitaire.domain.model.game;

import java.util.List;

/**
 * 列生成器。List<Card>をSequenceに変換する。
 */
public class SequenceBuilder {

    public Sequence build(List<Card> cards) {
        return cards.isEmpty() ? Empty.INSTANCE : new NonEmpty(cards.get(0), build(cards.subList(1, cards.size())));
    }
}
