package d5.solitaire.domain.model.game;

import java.util.stream.Stream;

/**
 * スート。カードのマーク。
 */
public enum Suit {
    SPADE("♠", Color.BLACK), 
    CLOVER("♣", Color.BLACK), 
    HEART("♡", Color.RED), 
    DIAMOND("♢", Color.RED);
    
    String mark;
    
    Color color;
    
    private Suit(String mark, Color color) {
        this.mark = mark;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return mark;
    }

    boolean isAdaptable(Suit suit) {
        return color.isAdaptable(suit.color);
    }
    
    public static Stream<Suit> stream(){
        return Stream.of(Suit.values());
    }
}
