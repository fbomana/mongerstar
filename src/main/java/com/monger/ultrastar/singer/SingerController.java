package com.monger.ultrastar.singer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/singer")
public class SingerController {

    private final SingerStorage storage;

    public SingerController( SingerStorage storage ) {
        this.storage = storage;
    }

    @GetMapping("")
    public List<Singer> getAll() {
        return this.storage.findAll();
    }

    @GetMapping("/{name}")
    public Singer get(@PathVariable("name") String name ) {
        return storage.getSinger( name );
    }

    @PostMapping("")
    public void addSinger(@RequestBody Singer singer ) {
        storage.addSinger( singer );
    }
}
