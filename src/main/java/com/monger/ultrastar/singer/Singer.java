package com.monger.ultrastar.singer;

public record Singer( String name ) implements Comparable<Singer> {

    @Override
    public int compareTo(Singer o) {
        if ( o == null ) {
            return 1;
        }
        return name.toLowerCase().compareTo( o.name().toLowerCase());
    }
}
