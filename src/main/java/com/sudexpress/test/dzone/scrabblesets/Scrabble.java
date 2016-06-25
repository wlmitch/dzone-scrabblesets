package com.sudexpress.test.dzone.scrabblesets;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Scrabble {

	final Map<String, Tile> tiles = new HashMap<>();

	public Scrabble() {
		this.init();
	}

	void init() {
		this.put("E", 12);
		this.put("A", 9);
		this.put("I", 9);
		this.put("O", 8);
		this.put("N", 6);
		this.put("R", 6);
		this.put("T", 6);
		this.put("L", 4);
		this.put("S", 4);
		this.put("U", 4);
		this.put("D", 4);
		this.put("G", 3);
		this.put("_", 2);
		this.put("B", 2);
		this.put("C", 2);
		this.put("M", 2);
		this.put("P", 2);
		this.put("F", 2);
		this.put("H", 2);
		this.put("V", 2);
		this.put("W", 2);
		this.put("Y", 2);
		this.put("K", 1);
		this.put("J", 1);
		this.put("X", 1);
		this.put("Q", 1);
		this.put("Z", 1);
	}

	void put(final String letter, final int count) {
		final Tile tile = new Tile(letter, count);
		this.tiles.put(letter, tile);
	}

	void pickAll(final String letters) throws NotEnoughTilesException {
		for (final String letter : letters.split("")) {
			this.pickOne(letter);
		}
	}

	void pickOne(final String letter) throws NotEnoughTilesException {
		this.tiles.get(letter).pick();
	}

	public String remainingLetters(final String letters) {
		try {
			this.pickAll(letters);
			final Map<Integer, List<Tile>> tilesByCounts = this.groupTilesByCounts();
			return String.join("\n", this.outputLines(tilesByCounts));
		} catch (final NotEnoughTilesException e) {
			return e.getMessage();
		}
	}

	Map<Integer, List<Tile>> groupTilesByCounts() {
		return this.tiles.values().stream().collect(Collectors.groupingBy(Tile::getCount));
	}

	List<String> outputLines(final Map<Integer, List<Tile>> tilesByCounts) {
		final Comparator<? super Entry<Integer, ?>> desc = (entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey());
		return tilesByCounts
			.entrySet()
			.stream()
			.sorted(desc)
			.map(tilesByCount -> this.outputLine(tilesByCount.getKey(), tilesByCount.getValue()))
			.collect(Collectors.toList());
	}

	String outputLine(final Integer count, final List<Tile> tiles) {
		return count + ": " + this.stringOfSorted(tiles);
	}

	String stringOfSorted(final List<Tile> tiles) {
		return String.join(", ", this.extractSortedLetters(tiles));
	}

	List<String> extractSortedLetters(final List<Tile> tiles) {
		return tiles.stream().map(Tile::getLetter).sorted().collect(Collectors.toList());
	}

}
