package d5.solitaire.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CardNumberTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 })
	void testファクトリメソッド_正常系(Integer value) {
		CardNumber cardNumber = CardNumber.of(value);
		assertAll(
				() -> assertThat(cardNumber).isInstanceOf(CardNumber.class),
				() -> assertThat(cardNumber.getNumber()).isEqualTo(value)
			);
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 0, 14 })
	void testファクトリメソッド_無効な数字(Integer value) {
		assertThrows(NoSuchElementException.class, () -> CardNumber.of(value));
	}
	
	@Test
	void testファクトリメソッド_null() {
		assertThrows(NoSuchElementException.class, () -> CardNumber.of(null));
	}
}
