package com.sudexpress.test.dzone.scrabblesets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TileTest {

	@Test(expected = NotEnoughTilesException.class)
	public void pick() throws Exception {
		final Tile tile = new Tile("a", 1);

		assertThat(tile.getCount(), is(1));

		tile.pick();

		assertThat(tile.getCount(), is(0));

		tile.pick();
	}

}
