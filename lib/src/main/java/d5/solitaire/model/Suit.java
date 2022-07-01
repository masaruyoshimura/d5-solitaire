package d5.solitaire.model;

import javax.annotation.Nonnull;

import lombok.Getter;

public enum Suit {

	SPADE("♠", "B"), CLUB("♣", "B"), HEART("♡", "R"), DIAMOND("♢", "R");

	@Getter
	private String label;
	@Getter
	private String color;

	private Suit(@Nonnull String label, @Nonnull String color) {
		this.label = label;
		this.color = color;
	}
}
