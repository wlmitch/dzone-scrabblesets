package com.sudexpress.test.dzone.scrabblesets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NotEnoughTilesExceptionTest {

	@Test
	public void NotEnoughTilesException() throws Exception {
		final NotEnoughTilesException exception = new NotEnoughTilesException("A");

		assertEquals("Invalid input. More A's have been taken from the bag than possible.", exception.getMessage());
	}

}
