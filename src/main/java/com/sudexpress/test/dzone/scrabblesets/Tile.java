package com.sudexpress.test.dzone.scrabblesets;

public class Tile {

	private final String letter;
	private int count;

	public Tile(final String letter, final int count) {
		this(letter);
		this.count = count;
	}

	public Tile(final String letter) {
		this.letter = letter;
	}

	public String getLetter() {
		return this.letter;
	}

	public int getCount() {
		return this.count;
	}

	public void pick() throws NotEnoughTilesException {
		if (this.count == 0) {
			throw new NotEnoughTilesException(this.letter);
		}
		this.count--;
	}

}
