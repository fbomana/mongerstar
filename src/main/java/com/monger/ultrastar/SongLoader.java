package com.monger.ultrastar;

import com.monger.ultrastar.song.SongStorage;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.monger.ultrastar.song.SongDiscoverConfigurations;
import com.monger.ultrastar.song.SongDiscoverer;

@Component
public class SongLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger( SongLoader.class );
	private final SongDiscoverer discoverer;
	private final SongStorage storage;
	private final SongDiscoverConfigurations configuration;

	
	public SongLoader(SongDiscoverer discoverer, SongDiscoverConfigurations configuration, SongStorage storage ) {
		this.discoverer = discoverer;
		this.configuration = configuration;
		this.storage = storage;
	}
	
    @Override 
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event ) {
    	long nanos = System.nanoTime();
    	
    	for ( String path : configuration.paths ) {
    		discoverer.discoverSongsOnFolder( path );	
    	}
    	
    	nanos = System.nanoTime() - nanos;
    	logger.info("{} songs loaded in: {}  ms",  storage.getAllSongs().size(), nanos / 1000000.0 );
    }
}



