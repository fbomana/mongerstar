package com.monger.ultrastar.singer;

import java.util.*;

import org.springframework.stereotype.Service;

@Service()
public class SingerStorage {

    private final Set<Singer> singers;
    
    public SingerStorage() {
		singers = new TreeSet<Singer>();
    }

    public void addSinger( Singer singer ) {
    	synchronized (singers) {
    		singers.add( singer );
    	}
    }

    public Singer getSinger( String name ) {
    	synchronized (singers) {
            Optional<Singer> singer =  singers.stream()
                    .filter( a -> a.getName().equalsIgnoreCase( name ))
                    .findFirst();
            return singer.orElseThrow(
                    () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name )));		
		}
    }
    
    public void removeSinger( String name ) {
    	synchronized (singers) {
	        Optional<Singer> singer =  singers.stream()
	                .filter( a -> a.getName().equalsIgnoreCase( name ))
	                .findFirst();
	        singers.remove( singer.orElseThrow(
	                () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name ))));
    	}
    }

    public void addSinger( String singer ) {
        OptionalInt maxScore = singers.stream().mapToInt( Singer::getScore ).max();
        singers.add( new Singer( singer, maxScore.orElse( Integer.MAX_VALUE ) -1 ));
    }

    public List<Singer> findAll() {
    	synchronized (singers) {
    		return singers.stream().toList();
    	}
    }
    
    public void increaseSingersScore() {
    	for ( Singer singer : singers ) {
    		singer.increaseScore();
    	}
    }
}
