package d5.solitaire.domain.model.game;

/**
 * カードの色
 */
public enum Color {
    
    BLACK, RED;

    boolean isAdaptable(Color color) {
        return this != color;
    }
}
