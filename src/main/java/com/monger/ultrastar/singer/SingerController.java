package com.monger.ultrastar.singer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/singer")
public class SingerController {

    private final SingerStorage storage;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    
    public SingerController( @Autowired SingerStorage storage ) {
        this.storage = storage;
    }

    @GetMapping("")
    public List<Singer> getAll() {
    	List<Singer> singers = storage.findAll();
        logger.info("------- Singers: {}", singers );
    	return singers;
    }

    @GetMapping("/{name}")
    public Singer get(@PathVariable("name") String name ) {
        return storage.getSinger( name );
    }

    @PostMapping("")
    public void addSinger(@RequestBody Singer singer ) {
        storage.addSinger( singer );
    }
    
    @DeleteMapping("/{name}")
    public void removeSinger(@PathVariable("name") String name ) {
    	try {
    		storage.removeSinger(name);
    	}
    	catch( SingerNotFoundException e ) {
    		 throw new ResponseStatusException( HttpStatus.NOT_FOUND, e.getMessage(), e );
    	}
    }
}
