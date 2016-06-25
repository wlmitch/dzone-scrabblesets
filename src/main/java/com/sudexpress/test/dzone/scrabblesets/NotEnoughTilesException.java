package com.sudexpress.test.dzone.scrabblesets;

public class NotEnoughTilesException extends Exception {

	private static final long serialVersionUID = 2442036946923671415L;

	public NotEnoughTilesException(final String letter) {
		super(String.format("Invalid input. More %s's have been taken from the bag than possible.", letter));
	}

}
