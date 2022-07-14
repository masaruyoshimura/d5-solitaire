package d5.solitaire.domain.model.game;

/**
 * 空列。LinkedListの終端。
 */
public class Empty implements Sequence {

    public static final Sequence INSTANCE = new Empty();
    
    private Empty() {}
    
    @Override
    public String toString() {
        return "";
    }

    @Override
    public Sequence remove(Sequence sublist) {
        throw new IllegalArgumentException("Empty cannot remove anything. :"+sublist);
    }
    
    @Override
    public Sequence append(Sequence sublist) {
        return sublist;
    }
    
    @Override // 空列に移動できるのは Kはじまり
    public Sequence adapt(Sequence other) {
        return other.canMoveToEmpty() ? other : null; 
    }
    
    @Override
    public boolean canMoveToEmpty() {
        return false;
    }
    
    @Override
    public Sequence adaptableSubsequnece(Card card) {
        return null;
    }
    
    @Override
    public Card last() {
        return null;
    }
    
    @Override
    public Sequence removeLast() {
        throw new UnsupportedOperationException("anything remove last from empty.");
    }
    
    @Override
    public Sequence append(Card card) {
        return new NonEmpty(card, Empty.INSTANCE);
    }
    
    @Override // FIXME Laneの空列とSequenceの空列を分けたい。Sequenceの終端をEmptyじゃなくて最後のCardを持つLeafにすればよい？
    public Sequence moveFromHandToLane(Card card) {
        return card.isKing() ? append(card) : null;
    }
}
