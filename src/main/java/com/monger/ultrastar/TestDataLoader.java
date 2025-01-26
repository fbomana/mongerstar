package com.monger.ultrastar;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monger.ultrastar.queue.Turn;
import com.monger.ultrastar.queue.UltrastarQueue;
import com.monger.ultrastar.singer.SingerStorage;
import com.monger.ultrastar.song.SongStorage;

@Component
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger( TestDataLoader.class );

	private SingerStorage singerStorage;
	private UltrastarQueue mongerQueue;
	private SongStorage songStorage;
	
    public TestDataLoader(SingerStorage singerStorage, UltrastarQueue mongerQueue, SongStorage songStorage) {
		this.singerStorage = singerStorage;
		this.mongerQueue = mongerQueue;
		this.songStorage = songStorage;
	}

	@Override 
    public void onApplicationEvent( ContextRefreshedEvent event ) {
    	logger.info("Evento recibido: {}", event );
    	logger.debug("Reading test data");
    	long nanos = System.nanoTime();
    	try {
    		File file = ResourceUtils.getFile("classpath:testQueue.json");
    		ObjectMapper objectMapper = new ObjectMapper();
    		UltrastarQueue queue = objectMapper.readValue(file, UltrastarQueue.class);
    		for ( Turn turn : queue.getTurns() ) {
    			singerStorage.addSinger( turn.singer1());
    			singerStorage.addSinger( turn.singer2());
    			songStorage.addSong( turn.song());
    			mongerQueue.add( turn.singer1(), turn.singer2(), turn.song() );
    		}
    		mongerQueue.nextTurn();
    	}
    	catch ( Exception e ) {
    		logger.error("Error reading test data", e);
    	}
    	nanos = System.nanoTime() - nanos;
    	logger.info("All test data loaded in: " + ( nanos / 1000000.0) + " ms");
    }
}



