package com.monger.ultrastar.queue;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

public class UltrastarQueueTest {

	Singer singer1 = new Singer("singer1");
	Singer singer2 = new Singer("Singer2");
	Singer singer3 = new Singer("Singer3");
	Singer singer4 = new Singer("Singer4");
	Song song = new Song("title", "author", "esp");
	Song song2 = new Song("title2", "author2", "esp");
	Song song3 = new Song("title3", "author3", "esp");
	Song song4 = new Song("title4", "author4", "esp");

	@Test
	public void addingATurnToAnEmptyQueueCreatesAQueueWithASingleNotCompleteTurn() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		assertEquals( 1, queue.size());
	}
	
	@Test
	public void addingATurnToAQueueWithOneUnsingedTurnIncreasesSizeTo2() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		assertEquals( 2, queue.size());
	}
	
	@Test
	public void nextTurnMarksTheCurrentSongAsCompletedAndReturnsTheNextUncompletedSong() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		Turn turn = queue.nextTurn();
		assertNotNull( turn );
		assertEquals( song2, turn.song());
		assertFalse( turn.completed() );
	}
	
	@Test
	public void nextTurnThrowsNoUnsingedSongsExceptionIfThereIsNoUnsingedSongsLeft() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		try {
			queue.nextTurn();
			queue.nextTurn();
			fail("NoUnsingedSongsException expected");
		}
		catch ( NoUnsingedSongsException e ) {
			assertEquals( "No more unsiged songs in queue", e.getMessage());
		}
	}
	
	@Test
	public void delayTurnMovesTheCurrentSongToTheNextAvailablePositionAnReturn() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		queue.add( singer1, singer2,  song3 );
		queue.nextTurn();
		Turn turn = queue.delayTurn();
		assertNotNull( turn );
		assertEquals( song3, turn.song());
		assertFalse( turn.completed() );
		turn = queue.nextTurn();
		assertNotNull( turn );
		assertEquals( song2, turn.song());
		assertFalse( turn.completed() );
	}
	
	@Test
	public void addWillSearchForASequenceOfTwoSongsThatShareAtLeastASingerAndInsertInTheMiddel() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		queue.add( singer1, singer2,  song3 );
		queue.add( singer3, singer4, song4 );
		Turn turn = queue.nextTurn();
		assertNotNull( turn );
		assertEquals( song4, turn.song());
		assertFalse( turn.completed() );
	}
	
	@Test
	public void removeSingerRemovesAllTheTurnsOfThatSingerThatAreNotCompleted() {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer2, song2 );
		queue.add( singer1, singer3,  song3 );
		queue.add( singer3, singer4, song4 );
		queue.add( singer1, singer2, song );
		queue.nextTurn();
		queue.nextTurn();
		queue.removeSinger( singer3 );
		assertEquals( 3, queue.size());
	}
	
	@Test
	public void removeSingerThrowsNoUnsingedSongsExceptionIfThereAreNoTurnsLeftUnsigedInQueue () {
		UltrastarQueue queue = new UltrastarQueue();
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer2, song2 );
		queue.add( singer1, singer3,  song3 );
		queue.add( singer3, singer4, song4 );
		queue.nextTurn();
		queue.nextTurn();
		try {
			queue.removeSinger( singer3 );
			fail("NoUnsingedSongsException expected");
		} catch ( NoUnsingedSongsException e ) {
			assertEquals( "No more unsiged songs in queue", e.getMessage());			
		}
	}
}
