package com.sudexpress.test.dzone.scrabblesets;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ScrabbleTest extends AbstractTest<Scrabble> {

	@Test
	public void test1() {
		Assert.assertEquals("10: E\n"
				+ "7: A, I\n"
				+ "6: N, O\n"
				+ "5: T\n"
				+ "4: D, L, R\n"
				+ "3: S, U\n"
				+ "2: B, C, F, G, M, V, Y\n"
				+ "1: H, J, K, P, W, X, Z, _\n"
				+ "0: Q", this.test.remainingLetters("PQAREIOURSTHGWIOAE_"));
	}

	@Test
	public void test2() {
		Assert.assertEquals("11: E\n"
				+ "9: A, I\n"
				+ "6: R\n"
				+ "5: N, O\n"
				+ "4: D, S, T, U\n"
				+ "3: G, L\n"
				+ "2: B, C, H, M, P, V, W, Y, _\n"
				+ "1: K, X\n"
				+ "0: F, J, Q, Z", this.test.remainingLetters("LQTOONOEFFJZT"));
	}

	@Test
	public void test3() {
		Assert.assertEquals("Invalid input. More X's have been taken from the bag than possible.", this.test.remainingLetters("AXHDRUIOR_XHJZUQEE"));
	}

	@Test
	public void put() throws Exception {
		final Scrabble scrabble = spy(Scrabble.class);

		assertFalse(scrabble.tiles.containsKey("a"));

		scrabble.put("a", 10);

		assertTrue(scrabble.tiles.containsKey("a"));

		final Tile tile = scrabble.tiles.get("a");
		assertNotNull(tile);
		assertEquals("a", tile.getLetter());
		assertEquals(10, tile.getCount());
	}

	@Test
	public void remainingLetters() throws Exception {
		final Map<Integer, List<Tile>> tilesByCounts = new HashMap<>();
		doReturn(tilesByCounts).when(this.test).groupTilesByCounts();

		this.test.remainingLetters("AB");

		verify(this.test).pickAll("AB");
		verify(this.test).groupTilesByCounts();
		verify(this.test).outputLines(tilesByCounts);
	}

	@Test
	public void remainingLettersWithError() throws Exception {
		final NotEnoughTilesException exception = new NotEnoughTilesException("a");
		doThrow(exception).when(this.test).pickAll("a");

		assertEquals(exception.getMessage(), this.test.remainingLetters("a"));
	}

	@Test
	public void pickAll() throws Exception {
		doNothing().when(this.test).pickOne(anyString());

		this.test.pickAll("AB");

		verify(this.test).pickOne("A");
		verify(this.test).pickOne("B");
	}

	@Test(expected = NotEnoughTilesException.class)
	public void pickAllWithError() throws Exception {
		doThrow(new NotEnoughTilesException("A")).when(this.test).pickOne("A");

		this.test.pickAll("A");
	}

	@Test
	public void pickOne() throws Exception {
		final Tile tile = mock(Tile.class);
		this.test.tiles.put("A", tile);

		this.test.pickOne("A");

		verify(tile).pick();
	}

	@Test(expected = NotEnoughTilesException.class)
	public void pickOneWithError() throws Exception {
		final Tile tile = mock(Tile.class);
		this.test.tiles.put("A", tile);
		doThrow(new NotEnoughTilesException("A")).when(tile).pick();

		this.test.pickOne("A");
	}

	@Test
	public void groupTilesByCounts() throws Exception {
		this.test.tiles.clear();
		this.test.tiles.put("a", new Tile("a", 1));
		this.test.tiles.put("b", new Tile("b", 1));
		this.test.tiles.put("c", new Tile("c", 2));

		final Map<Integer, List<Tile>> tilesByCounts = this.test.groupTilesByCounts();

		// Always an object
		assertNotNull(tilesByCounts);
		// 2 Tiles with count 1
		assertTrue(tilesByCounts.containsKey(1));
		assertEquals(2, tilesByCounts.get(1).size());
		// 1 Tile with count 1
		assertTrue(tilesByCounts.containsKey(2));
		assertEquals(1, tilesByCounts.get(2).size());
	}

	@Test
	public void outputLines() throws Exception {
		// Preconditions
		final Map<Integer, List<Tile>> tilesByCounts = new HashMap<>();

		final List<Tile> tiles1 = new ArrayList<>();
		tilesByCounts.put(1, tiles1);
		doReturn("line1").when(this.test).outputLine(1, tiles1);

		final List<Tile> tiles2 = new ArrayList<>();
		tilesByCounts.put(2, tiles2);
		doReturn("line2").when(this.test).outputLine(2, tiles2);

		// Test
		final List<String> outputLines = this.test.outputLines(tilesByCounts);

		// Verify the output
		assertNotNull(outputLines);
		assertEquals(2, outputLines.size());
		assertEquals("line2", outputLines.get(0));
		assertEquals("line1", outputLines.get(1));
	}

	@Test
	public void outputLine() throws Exception {
		final List<Tile> tiles = new ArrayList<>();
		doReturn("letters").when(this.test).stringOfSorted(tiles);

		assertEquals("1: letters", this.test.outputLine(1, tiles));
	}

	@Test
	public void stringOfSorted() throws Exception {
		final List<Tile> tiles = new ArrayList<>();
		doReturn(asList("A", "B", "C")).when(this.test).extractSortedLetters(tiles);

		assertEquals("A, B, C", this.test.stringOfSorted(tiles));
	}

	@Test
	public void extractSortedLetters() throws Exception {
		final List<Tile> tiles = asList(new Tile("c"), new Tile("a"), new Tile("b"));

		final List<String> letters = this.test.extractSortedLetters(tiles);

		assertNotNull(letters);
		assertEquals(3, letters.size());
		assertEquals("a", letters.get(0));
		assertEquals("b", letters.get(1));
		assertEquals("c", letters.get(2));
	}

}
