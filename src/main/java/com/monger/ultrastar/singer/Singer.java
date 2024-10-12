package com.monger.ultrastar.singer;

public record Singer( String name, int score ) implements Comparable<Singer> {

    public Singer( String name ) {
        this( name, Integer.MAX_VALUE );
    }

    @Override
    public int compareTo(Singer o) {
        if ( o == null ) {
            return 1;
        }
        return name.toLowerCase().compareTo( o.name().toLowerCase());
    }

}
