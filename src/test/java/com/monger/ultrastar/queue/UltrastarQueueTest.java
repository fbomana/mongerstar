package com.monger.ultrastar.queue;


import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.singer.SingerStorage;
import com.monger.ultrastar.song.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UltrastarQueueTest {

	private final Singer singer1 = new Singer("singer1", 1);
	private final Singer singer2 = new Singer("Singer2", 2);
	private final Singer singer3 = new Singer("Singer3", 3);
	private final Singer singer4 = new Singer("Singer4", 4);
	private final Song song = new Song("title", "author", "esp");
	private final Song song2 = new Song("title2", "author2", "esp");
	private final  Song song3 = new Song("title3", "author3", "esp");
	private final Song song4 = new Song("title4", "author4", "esp");
	private SingerStorage singerStorage;
	
	@BeforeEach
	public void populateStorage() {
		singerStorage = new SingerStorage();
		singerStorage.addSinger( singer1 );
		singerStorage.addSinger( singer2 );
		singerStorage.addSinger( singer3 );
		singerStorage.addSinger( singer4 );
	}

	@Test
	public void addingATurnToAnEmptyQueueCreatesAQueueWithASingleNotCompleteTurn() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage);
		queue.add( singer1, singer2, song );
		assertEquals( 1, queue.size());
	}
	
	@Test
	public void addingATurnToAQueueWithOneUnsingedTurnIncreasesSizeTo2() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		assertEquals( 2, queue.size());
	}
	
	@Test
	public void addingATurnWhithGreaterPriorityPutsTheTurnInTheFirstSpotOfTheQueue() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer4, song2 );
		assertEquals( 2, queue.size());
		Turn turn = queue.nextTurn();

		assertEquals( singer3, turn.singer1() );
		assertEquals( singer4, turn.singer2() );
		assertEquals( song2, turn.song());
	}
	
	@Test
	public void addingSeveralTurnsWithTheSamePriorityPutsThemInFIFOOrder() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer4, song );
		queue.add( singer3, singer4, song2 );
		queue.add( singer3, singer4, song3 );
		assertEquals( 4, queue.size());
		List<Turn> turns = queue.getTurns();
		Turn turn = turns.get(0);
		assertEquals( singer3, turn.singer1());
		assertEquals( singer4, turn.singer2() );
		assertEquals( song, turn.song());
		turn = turns.get(1);
		assertEquals( singer3, turn.singer1());
		assertEquals( singer4, turn.singer2() );
		assertEquals( song2, turn.song());
		turn = turns.get(2);
		assertEquals( singer3, turn.singer1());
		assertEquals( singer4, turn.singer2() );
		assertEquals( song3, turn.song());
		turn = turns.get(3);
		assertEquals( singer1, turn.singer1());
		assertEquals( singer2, turn.singer2() );
		assertEquals( song, turn.song());
	}
	
	@Test
	public void nextTurnRemovesTheFirstSongFromTheQueueAndAdsItToThehistoricThenRecalculatesTheQueueStatus() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer3, singer4, song );
		queue.add( singer3, singer4, song2 );
		queue.add( singer1, singer2, song );
		Turn turn = queue.nextTurn();
		assertEquals( singer3, turn.singer1() );
		assertEquals( singer4, turn.singer2());
		assertEquals( song, turn.song() );

		turn = queue.nextTurn();
		assertEquals( singer1, turn.singer1() );
		assertEquals( singer2, turn.singer2());
		assertEquals( song, turn.song() );
	}

	
	@Test
	public void nextTurnThrowsNoSongsInQueueExceptionIfThereIsNoUnsingedSongsLeft() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer1, singer2, song2 );
		try {
			queue.nextTurn();
			queue.nextTurn();
			queue.nextTurn();
			fail("NoUnsingedSongsException expected");
		}
		catch ( NoSongsInQueueException e ) {
			assertEquals( "No more unsiged songs in queue", e.getMessage());
		}
	}
	
	@Test
	public void delayTurnChangesCurrentTurnForFirstTurnOnTheQueue() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer4, song2 );
		Turn currentTurn = queue.nextTurn();
		assertEquals( singer3, currentTurn.singer1() );
		assertEquals( singer4, currentTurn.singer2() );
		assertEquals( song2, currentTurn.song());
		Turn firstTurnInQueue = queue.getTurns().get(0);
		assertEquals( singer1, firstTurnInQueue.singer1() );
		assertEquals( singer2, firstTurnInQueue.singer2() );
		assertEquals( song, firstTurnInQueue.song());
		queue.delayTurn();
		currentTurn = queue.getCurrentTurn();
		assertEquals( singer1, currentTurn.singer1() );
		assertEquals( singer2, currentTurn.singer2() );
		assertEquals( song, currentTurn.song());
		firstTurnInQueue = queue.getTurns().get(0);
		assertEquals( singer3, firstTurnInQueue.singer1() );
		assertEquals( singer4, firstTurnInQueue.singer2() );
		assertEquals( song2, firstTurnInQueue.song());

	}


	@Test
	public void removeSingerRemovesAllTheTurnsOfThatSingerThatAreNotCompleted() {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		queue.add( singer1, singer2, song );
		queue.add( singer3, singer2, song2 );
		queue.add( singer1, singer3,  song3 );
		queue.add( singer3, singer4, song4 );
		queue.add( singer1, singer2, song2 );
		queue.removeSinger( singer3 );
		assertEquals( 2, queue.size());
	}
	
	@Test
	public void removeSingerThrowsNoUnsingedSongsExceptionIfThereAreNoTurnsLeftUnsigedInQueue () {
		UltrastarQueue queue = new UltrastarQueue( singerStorage );
		try {
			queue.removeSinger( singer3 );
			fail("NoSongsInQueueException expected");
		} catch ( NoSongsInQueueException e ) {
			assertEquals( "No more unsiged songs in queue", e.getMessage());			
		}
	}
}
