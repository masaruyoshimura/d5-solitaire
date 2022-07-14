package d5.solitaire.domain.model.game;

/**
 * カードのある列。LinkedList。
 */
public class NonEmpty implements Sequence{
    
    private final Card card;
    
    // card より下
    private final Sequence sequence;

    public NonEmpty(Card card, Sequence sequence) {
        this.card = card;
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return card.toString()+sequence.toString();
    }
    
    @Override
    public Sequence remove(Sequence sublist) {
        return sublist == this ? Empty.INSTANCE : new NonEmpty(card, sequence.remove(sublist));
    }

    @Override
    public Sequence append(Sequence sublist) {
        return new NonEmpty(card, sequence.append(sublist));
    }
    
    @Override // 空列以外に移動できるのは色違い連番
    public Sequence adapt(Sequence other) {
        if( sequence != Empty.INSTANCE ) return sequence.adapt(other);
        return other.adaptableSubsequnece(card);
    }
    
    @Override
    public boolean canMoveToEmpty() {
        return card.isKing();
    }
    
    @Override
    public Sequence adaptableSubsequnece(Card other) {
        return other.isAdaptable(card) ? this : sequence.adaptableSubsequnece(other);
    }
    
    @Override
    public Card last() {
        return sequence == Empty.INSTANCE ? card : sequence.last();
    }
    
    @Override
    public Sequence removeLast() {
        return sequence == Empty.INSTANCE ? Empty.INSTANCE : new NonEmpty(card, sequence.removeLast());
    }
    
    @Override
    public Sequence append(Card card) {
        return new NonEmpty(this.card, sequence.append(card));
    }
    
    @Override
    public Sequence moveFromHandToLane(Card card) {
        var lane2Last = last();
        if (lane2Last.isAdaptable(card)) {
            return append(card);
        }
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof NonEmpty)) return false;
        NonEmpty other = (NonEmpty)obj;
        return other.card.equals(card) && other.sequence.equals(sequence);
    }
}
