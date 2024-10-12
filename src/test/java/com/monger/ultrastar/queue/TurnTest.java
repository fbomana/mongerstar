package com.monger.ultrastar.queue;

import static org.junit.jupiter.api.Assertions.*;

import com.monger.ultrastar.singer.Singer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class TurnTest {

    @Test
    public void aTurnScoreIsTheSumOfItsSingersScore() {
        Turn turn = new Turn ( new Singer( "a", 2 ), new Singer("b", 3 ), null, false );
        assertEquals( 5, turn.calculateScore() );
    }

    @Test
    public void theTurnWithTheGreatestScoreGoesFirst() {
        List<Turn> list = Arrays.asList(
            new Turn ( new Singer( "a", 2 ), new Singer("b", 3 ), null, false ),
            new Turn ( new Singer( "a", 5 ), new Singer("b", 3 ), null, false )
        );
        Collections.sort( list );

        assertEquals( 8, list.get(0).calculateScore());
    }
}
