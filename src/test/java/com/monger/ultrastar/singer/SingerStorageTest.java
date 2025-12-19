package com.monger.ultrastar.singer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SingerStorageTest {

    @Test
    public void addSingerToAnEmptyStorageCreatesAStorageWithOneSinget() {
        SingerStorage storage = new SingerStorage();
        Singer singer = new Singer("monger", Integer.MAX_VALUE);
        storage.addSinger( singer );
        List<Singer> singers = storage.findAll();
        assertFalse( singers.isEmpty());
        assertEquals( 1, singers.size());
        assertEquals( "monger", singers.get(0).getName());
    }

    @Test
    public void addingTheSameSingerTwiceToTheStoraeDontCreateRepeatedSingers() {
        SingerStorage storage = new SingerStorage();
        Singer singer = new Singer("monger", Integer.MAX_VALUE);
        storage.addSinger( singer );
        storage.addSinger( singer );
        List<Singer> singers = storage.findAll();
        assertFalse( singers.isEmpty());
        assertEquals( 1, singers.size());
        assertEquals( "monger", singers.get(0).getName());
    }

    @Test
    public void getSingerThrowsAnExcetionIfTheSinerDoesNotExists() {
        try {
            SingerStorage storage = new SingerStorage();
            storage.getSinger( "monger" );
            fail("SingerNotFoundException expected");
        } catch ( SingerNotFoundException e ) {
            assertEquals( "Cantante monger no enconrado", e.getMessage());
        }
    }
    
    @Test
    public void removeSingerThrowsAnExcetionIfTheSinerDoesNotExists() {
        try {
            SingerStorage storage = new SingerStorage();
            storage.removeSinger( "monger" );
            fail("SingerNotFoundException expected");
        } catch ( SingerNotFoundException e ) {
            assertEquals( "Cantante monger no enconrado", e.getMessage());
        }
    }
    
    @Test
    public void removeSingerRemoveTheSingerFromStorage() {
        SingerStorage storage = new SingerStorage();
        Singer singer1 = new Singer("monger1", Integer.MAX_VALUE);
        Singer singer2 = new Singer("monger2", Integer.MAX_VALUE);
        storage.addSinger( singer1 );
        storage.addSinger( singer2 );
        storage.removeSinger( "monger1" );
        List<Singer> singers = storage.findAll();
        assertFalse( singers.isEmpty() );
        assertEquals( 1, singers.size());
        assertEquals( "monger2", singers.get(0).getName());
    }
    
    @Test
    public void increaseSingersScoreAddsOneToTheScoreOfAllSingersThatAreNotMaxxed() {
        SingerStorage storage = new SingerStorage();
        Singer singer1 = new Singer("monger1", Integer.MAX_VALUE);
        Singer singer2 = new Singer("monger2", 10);
        storage.addSinger( singer1 );
        storage.addSinger( singer2 );
        storage.increaseSingersScore();
        assertEquals( Integer.MAX_VALUE, storage.getSinger( "monger1").getScore());
        assertEquals( 11, storage.getSinger( "monger2").getScore());
    }
}
