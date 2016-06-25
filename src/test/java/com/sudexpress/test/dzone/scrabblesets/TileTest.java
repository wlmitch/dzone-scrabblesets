package com.sudexpress.test.dzone.scrabblesets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TileTest {

	@Test(expected = NotEnoughTilesException.class)
	public void pick() throws Exception {
		final Tile tile = new Tile("a", 1);

		assertEquals(1, tile.getCount());

		tile.pick();

		assertEquals(0, tile.getCount());

		tile.pick();
	}

}
