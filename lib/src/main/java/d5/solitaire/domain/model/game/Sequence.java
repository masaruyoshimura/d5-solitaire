package d5.solitaire.domain.model.game;

/**
 * 列。LinkedList。
 */
public interface Sequence {

    Sequence remove(Sequence sublist);

    Sequence append(Sequence sublist);

    Sequence adapt(Sequence sequence);

    boolean canMoveToEmpty();

    Sequence adaptableSubsequnece(Card card);

    Card last();

    Sequence removeLast();

    Sequence append(Card card);

    Sequence moveFromHandToLane(Card card);
}
