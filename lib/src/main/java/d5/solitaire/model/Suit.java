package d5.solitaire.model;

import lombok.Getter;
import lombok.NonNull;

public enum Suit {

	SPADE("♠", "B"), CLUB("♣", "B"), HEART("♡", "R"), DIAMOND("♢", "R");

	@Getter
	private String label;
	@Getter
	private String color;

	private Suit(@NonNull String label, @NonNull String color) {
		this.label = label;
		this.color = color;
	}
}
