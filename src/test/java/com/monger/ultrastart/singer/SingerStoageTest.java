package com.monger.ultrastart.singer;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

public class SingerStoageTest {

    @Test
    public void addSingerToAnEmptyStorageCreatesAStorageWithOneSinget() {
        SingerStorage storage = new SingerStorage();
        Singer singer = new Singer("monger");
        storage.addSinger( singer );
        List<Singer> singers = storage.findAll();
        assertFalse( singers.isEmpty());
        assertEquals( 1, singers.size());
        assertEquals( "monger", singers.get(0).name());
    }

    @Test
    public void addingTheSameSingerTwiceToTheStoraeDontCreateRepeatedSingers() {
        SingerStorage storage = new SingerStorage();
        Singer singer = new Singer("monger");
        storage.addSinger( singer );
        storage.addSinger( singer );
        List<Singer> singers = storage.findAll();
        assertFalse( singers.isEmpty());
        assertEquals( 1, singers.size());
        assertEquals( "monger", singers.get(0).name());
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
}
