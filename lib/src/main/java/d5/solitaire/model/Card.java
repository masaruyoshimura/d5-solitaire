package d5.solitaire.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Card {
	private final CardNumber number;

	private final Suit suit;

	private Card(@NonNull CardNumber number, @NonNull Suit suit) {
		super();
		this.number = number;
		this.suit = suit;
	}

	public static Card of(@NonNull CardNumber number, @NonNull Suit suit) {
		return new Card(number, suit);
	}

	/**
	 * @param number
	 * @param suit
	 * @return
	 */
	public static Card of(@NonNull Integer number, @NonNull Suit suit) {
		return new Card(CardNumber.of(number), suit);
	}

	/**
	 * トランプの数字を返す。
	 * 
	 * @return
	 */
	public Integer number() {
		return this.number.getNumber();
	}

	/**
	 * トランプの数字のラベルを返す。
	 * 
	 * @return
	 */
	public String label() {
		return this.number.getLabel();
	}

	/**
	 * トランプのマークを返す。
	 * 
	 * @return
	 */
	public String suit() {
		return this.suit.getLabel();
	}

	/**
	 * トランプのマークの色を返す。
	 * 
	 * @return
	 */
	public String color() {
		return this.suit.getColor();
	}

	/**
	 * 引数で渡されたカードの次の数字であるかを判断する。
	 * 
	 * @param card
	 * @return 次の数字である場合true、そうでない場合false
	 */
	public boolean isNext(Card card) {
		return this.number.getNumber() == card.number() + 1;
	}

	/**
	 * 引数で渡されたカードの色が交互であるかを判別する。
	 * 
	 * @param card
	 * @return 交互であった場合true、そうでない場合false
	 */
	public boolean isAlternateColor(Card card) {
		return !card.color().equals(this.suit.getColor());
	}
}
