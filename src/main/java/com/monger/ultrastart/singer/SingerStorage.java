package com.monger.ultrastart.singer;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                .filter( a -> a.name().equals( name ))
                .findFirst();
        return singer.orElseThrow(
                () -> new SingerNotFoundException( String.format("Cantante %s no enconrado", name )));
    }

    public List<Singer> findAll() {
        return singers.stream().toList();
    }
}
