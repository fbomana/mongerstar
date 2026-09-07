package com.monger.ultrastar.singer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SingerTest {

    @Test
    public void addASingerByNameOnAnEmptyStorageGivesThemTheDefaultScore() {
        SingerStorage storage = new SingerStorage();
        storage.addSinger("a");
        Singer singer = storage.getSinger( "a" );
        assertEquals( 0 , singer.getScore());
    }

    @Test public void addSingerByNameOnAListWithUsersGiveThemTheGreatestScoreOfAllUsers(){
        SingerStorage storage = new SingerStorage();
        storage.addSinger(new Singer( "a", 5 ));
        storage.addSinger(new Singer( "b", 3 ));
        storage.addSinger( "c" );
        Singer singer = storage.getSinger( "c" );
        assertEquals( 5 , singer.getScore());
    }
}
