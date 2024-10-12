package com.monger.ultrastar.singer;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SingerStorage {

    private Set<Singer> singers;

    public SingerStorage() {
        singers = new TreeSet<Singer>();
    }

    public void addSinger( Singer singer ) {
        singers.add( singer );
    }

    public Singer getSinger( String name ) {
        Optional<Singer> singer =  singers.stream()
                .filter( a -> a.name().equalsIgnoreCase( name ))
                .findFirst();
        return singer.orElseThrow(
                () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name )));
    }

    public void addSinger( String singer ) {
        OptionalInt maxScore = singers.stream().mapToInt( Singer::score ).max();
        singers.add( new Singer( singer, maxScore.orElse( Integer.MAX_VALUE ) -1 ));
    }

    public List<Singer> findAll() {
        return singers.stream().toList();
    }
}
