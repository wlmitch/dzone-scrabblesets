package com.sudexpress.test.dzone.scrabblesets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NotEnoughTilesExceptionTest {

	@Test
	public void NotEnoughTilesException() throws Exception {
		final NotEnoughTilesException exception = new NotEnoughTilesException("A");

		assertThat(exception.getMessage(), is("Invalid input. More A's have been taken from the bag than possible."));
	}

}
