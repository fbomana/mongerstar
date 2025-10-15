package com.monger.ultrastar.singer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SingerTest {

    @Test
    public void addASingerByNameOnAnEmptyStorageGivesThemTheMaxScore() {
        SingerStorage storage = new SingerStorage();
        storage.addSinger("a");
        Singer singer = storage.getSinger( "a" );
        assertEquals( Integer.MAX_VALUE -1 , singer.getScore());
    }

    @Test public void addSingerByNameOnAListWithUsersGiveThemTheGreatestScoreOfAllUsersMinusOne(){
        SingerStorage storage = new SingerStorage();
        storage.addSinger(new Singer( "a", 5 ));
        storage.addSinger(new Singer( "b", 3 ));
        storage.addSinger( "c" );
        Singer singer = storage.getSinger( "c" );
        assertEquals( 4 , singer.getScore());
    }
}
