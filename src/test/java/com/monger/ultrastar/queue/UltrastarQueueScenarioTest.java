package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.SingerStorage;
import com.monger.ultrastar.song.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UltrastarQueueScenarioTest {

    @Test
    public void scenario1() {
        SingerStorage singers = new SingerStorage();
        UltrastarQueue queue = new UltrastarQueue( singers );
        QueueController controller = new QueueController( queue, singers );
        Song song1 = new Song( "song1", "author", "español");
        Song song2 = new Song( "song2", "author", "español");
        Song song3 = new Song( "song3", "author", "español");
        Song song4 = new Song( "song4", "author", "español");
        Song song5 = new Song( "song5", "author", "español");
        Song song6 = new Song( "song6", "author", "español");
        Song song7 = new Song( "song7", "author", "español");
        Song song8 = new Song( "song8", "author", "español");
        Song song9 = new Song( "song9", "author", "español");

        // Add two singers and a song
        singers.addSinger( "singer1" );
        singers.addSinger( "singer2" );
        controller.addTurn( new NewTurnRequest( "singer1", "singer2", song1 ));
        printState( queue );

        // Add two new singers and a song
        singers.addSinger( "singer3" );
        singers.addSinger( "singer4" );
        controller.addTurn(new NewTurnRequest( "singer3", "singer4", song2 ));
        printState( queue );

        // Sing the first Song
        controller.nextTurn();
        printState( queue );

        // Add new singer and song
        singers.addSinger( "singer5" );
        controller.addTurn(new NewTurnRequest( "singer5", "singer1", song3 ));
        printState( queue );

        // Add new singer and song
        singers.addSinger( "singer6" );
        controller.addTurn( new NewTurnRequest( "singer6", "singer1", song4 ));
        printState( queue );

        // Add new singer and song
        singers.addSinger( "singer7" );
        controller.addTurn(new NewTurnRequest( "singer7", "singer2", song5 ));
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Add two new singers and a song
        singers.addSinger( "singer8" );
        singers.addSinger( "singer9" );
        controller.addTurn( new NewTurnRequest("singer8", "singer9", song6 ));
        printState( queue );

        // Add new Song
        controller.addTurn( new NewTurnRequest("singer4", "singer2", song7 ));
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // singer8 and singer9 disappear -> delay turn
        controller.delayTurn();
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Add new Song
        controller.addTurn( new NewTurnRequest("singer6", "singer1", song8 ));
        printState( queue );

        // Add new Song
        controller.addTurn( new NewTurnRequest("singer1", "singer2", song9 ));
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        // Sing a song
        controller.nextTurn();
        printState( queue );

        List<Turn> history = queue.getTurnHistory();
        List<String> songs = history.stream().map( Turn::song ).map( Song::title ).toList();
        List<String> expected = List.of("song1","song2","song3","song5","song6","song4","song7","song8");
        assertEquals( expected, songs );

    }

    public void printState( UltrastarQueue queue ) {
        queue.getTurnHistory().forEach((t)->System.out.println("h " + t));
        System.out.println( "->" + queue.getCurrentTurn() );
        queue.getTurns().forEach((t)->System.out.println("  " + t));
        System.out.println("-----------------------");
    }
}
