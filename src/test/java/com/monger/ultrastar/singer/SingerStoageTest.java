package com.monger.ultrastar.singer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

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
