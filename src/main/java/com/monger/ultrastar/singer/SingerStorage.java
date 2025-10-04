package com.monger.ultrastar.singer;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
                    .filter( a -> a.name().equalsIgnoreCase( name ))
                    .findFirst();
            return singer.orElseThrow(
                    () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name )));		
		}
    }
    
    public void removeSinger( String name ) {
    	synchronized (singers) {
	        Optional<Singer> singer =  singers.stream()
	                .filter( a -> a.name().equalsIgnoreCase( name ))
	                .findFirst();
	        singers.remove( singer.orElseThrow(
	                () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name ))));
    	}
    }

    public List<Singer> findAll() {
    	synchronized (singers) {
    		return singers.stream().toList();
    	}
    }
}
