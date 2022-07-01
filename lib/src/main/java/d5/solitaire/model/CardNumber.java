package d5.solitaire.model;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;

public enum CardNumber {

	ONE(1, "A"),
	TWO(2, "2"),
	THREE(3, "3"),
	FOUR(4, "4"),
	FIVE(5, "5"),
	SIX(6, "6"),
	SEVEN(7, "7"),
	EIGHT(8, "8"),
	NINE(9, "9"),
	TEM(10, "10"),
	ELEVEN(11, "J"), 
	TWELVE(12, "Q"),
	THIRTEEN(13, "K");

	@Getter
	private Integer number;
	@Getter
	private String label;

	private CardNumber(@NonNull Integer number, @NonNull String label) {
		this.number = number;
		this.label = label;
	}

	public static CardNumber of(@NonNull Integer number) {
		return Stream.of(values()).filter(numberLabel -> numberLabel.getNumber().equals(number)).findFirst().get();
	}
}
