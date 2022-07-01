package d5.solitaire.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CardTest {
	
	@Test
	void testファクトリメソッド_正常系() {
		Card card1 = Card.of(CardNumber.ONE, Suit.HEART);
		Card card2 = Card.of(1, Suit.HEART);
		assertAll(
				() -> assertThat(card1).isInstanceOf(Card.class),
				() -> assertThat(card2).isInstanceOf(Card.class)
			);
	}
	
	@Test
	void testファクトリメソッド_null() {
		assertThrows(NullPointerException.class, () -> Card.of(1, null));
	}
	
	@ParameterizedTest
	@MethodSource("stream_Getter系")
	void test_Getter系(Card card, Integer number, String label, String suit, String color) {
		assertAll(
				() -> assertThat(card.number()).isEqualTo(number),
				() -> assertThat(card.label()).isEqualTo(label),
				() -> assertThat(card.suit()).isEqualTo(suit),
				() -> assertThat(card.color()).isEqualTo(color)
			);
	}
	
	static Stream<Arguments> stream_Getter系() {
	    return Stream.of(
	    	Arguments.of(Card.of(1, Suit.HEART), 1, "A", "♡", "R"),
	    	Arguments.of(Card.of(11, Suit.CLUB), 11, "J", "♣", "B"),
	        Arguments.of(Card.of(12, Suit.SPADE), 12, "Q", "♠", "B"),
	        Arguments.of(Card.of(13, Suit.DIAMOND), 13, "K", "♢", "R")
	    );
	}
	
	@ParameterizedTest
	@MethodSource("stream_IsNext")
	void testIsNext(Card card1, Card card2, boolean expected) {
		assertThat(card1.isNext(card2)).isEqualTo(expected);
	}
	
	static Stream<Arguments> stream_IsNext() {
	    return Stream.of(
	    	Arguments.of(Card.of(2, Suit.HEART), Card.of(1, Suit.CLUB), true),
	    	Arguments.of(Card.of(3, Suit.HEART), Card.of(3, Suit.CLUB), false),
	    	Arguments.of(Card.of(6, Suit.HEART), Card.of(4, Suit.CLUB), false),
	    	Arguments.of(Card.of(6, Suit.HEART), Card.of(7, Suit.CLUB), false)	
	    );
	}
	
	@ParameterizedTest
	@MethodSource("stream_IsAlternateColor")
	void testIsAlternateColor(Card card1, Card card2, boolean expected) {
		assertThat(card1.isAlternateColor(card2)).isEqualTo(expected);
	}
	
	static Stream<Arguments> stream_IsAlternateColor() {
	    return Stream.of(
	    	Arguments.of(Card.of(2, Suit.HEART), Card.of(1, Suit.CLUB), true),
	    	Arguments.of(Card.of(3, Suit.HEART), Card.of(3, Suit.DIAMOND), false),
	    	Arguments.of(Card.of(6, Suit.HEART), Card.of(4, Suit.SPADE), true),
	    	Arguments.of(Card.of(6, Suit.DIAMOND), Card.of(7, Suit.CLUB), true),
	    	Arguments.of(Card.of(6, Suit.DIAMOND), Card.of(7, Suit.SPADE), true),
	    	Arguments.of(Card.of(6, Suit.SPADE), Card.of(7, Suit.CLUB), false)
	    );
	}
}
