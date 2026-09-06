package com.monger.ultrastar.queue;

import static org.junit.jupiter.api.Assertions.*;

import com.monger.ultrastar.singer.Singer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class TurnTest {

    @Test
    public void aTurnScoreIsTheSumOfItsSingersScore() {
        Turn turn = new Turn ( new Singer( "a", 2 ), new Singer("b", 3 ), null, false, LocalDateTime.now() );
        assertEquals( 5, turn.calculateScore() );
    }

    @Test
    public void theTurnWithTheGreatestScoreGoesFirst() {
        List<Turn> list = Arrays.asList(
            new Turn ( new Singer( "a", 2 ), new Singer("b", 3 ), null, false, LocalDateTime.now() ),
            new Turn ( new Singer( "a", 5 ), new Singer("b", 3 ), null, false, LocalDateTime.now() )
        );
        Collections.sort( list );

        assertEquals( 8, list.get(0).calculateScore());
    }

    @Test
    public void ifTwoTurnsHaveTheSameScoreTheOldestGoesFirst() {
        List<Turn> list = Arrays.asList(
            new Turn ( new Singer( "a", 5 ), new Singer("b", 3 ), null, false, LocalDateTime.now() ),
            new Turn ( new Singer( "c", 5 ), new Singer("b", 3 ), null, false, LocalDateTime.now().minusSeconds( 10 ) )
        );
        Collections.sort( list );

        assertEquals( "c", list.get(0).singer1().getName());
    }

}
