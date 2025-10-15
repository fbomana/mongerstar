package com.monger.ultrastar.singer;

public class Singer implements Comparable<Singer> {

	private final String name;
	private int score;
	
    public Singer( String name ) {
        this( name, Integer.MAX_VALUE );
    }
    
    
    public Singer( String name, int score ) {
        this.name = name;
        this.score = score;
    }
    
    public void resetScore() {
    	score = 0;
    }
    
    public void increaseScore() {
    	if ( score < Integer.MAX_VALUE ) {
    		score += 1;
    	}
    }
    
    public String getName() {
    	return name;
    }

    public int getScore() {
    	return score;
    }


    @Override
    public int compareTo(Singer o) {
        if ( o == null ) {
            return 1;
        }
        return name.toLowerCase().compareTo( o.name.toLowerCase());
    }

}
