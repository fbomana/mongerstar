package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.SingerNotFoundException;
import com.monger.ultrastar.singer.SingerStorage;
import com.monger.ultrastar.song.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QueueControllerTest {

    private SingerStorage storage;
    private UltrastarQueue queue;
    private QueueController controller;


    @BeforeEach
    public void setUp() {
        storage = new SingerStorage();
        queue = new UltrastarQueue( storage );
        controller = new QueueController( queue, storage );
    }

    @Test
    public void addTurn_failsIfSinger1IsNotInTheSingerStorage() {
        NewTurnRequest request = new NewTurnRequest("singer1", "singer2", new Song("title", "author", "language"));
        SingerNotFoundException re = assertThrows(SingerNotFoundException.class, ()->controller.addTurn( request ));
        assertEquals("Cantante singer1 no encontrado", re.getMessage());
    }

    @Test
    public void addTurn_failsIfSinger2IsNotInTheSingerStorage() {
        storage.addSinger( "singer1" );
        NewTurnRequest request = new NewTurnRequest("singer1", "singer2", new Song("title", "author", "language"));
        SingerNotFoundException re = assertThrows(SingerNotFoundException.class, ()->controller.addTurn( request ));
        assertEquals("Cantante singer2 no encontrado", re.getMessage());
    }

    @Test
    public void addTurn_increasesTheSizeOfTheQueueIfBothSingersAreInTheStorage() {
        storage.addSinger( "singer1" );
        storage.addSinger( "singer2" );
        int previous = queue.size();
        NewTurnRequest request = new NewTurnRequest("singer1", "singer2", new Song("title", "author", "language"));
        controller.addTurn( request );
        assertEquals( previous + 1, queue.size());
    }
}
