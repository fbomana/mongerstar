package com.monger.ultrastart.singer;

import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("singer")
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
    public Singer get(@PathParam( "name" ) String name ) {
        return storage.getSinger( name );
    }

    @PostMapping("")
    public void addSinger(@RequestBody Singer singer ) {
        storage.addSinger( singer );
    }
}
