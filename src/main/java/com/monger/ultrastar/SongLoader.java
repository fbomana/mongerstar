package com.monger.ultrastar;

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
	private final SongDiscoverConfigurations configuration;
	
	public SongLoader( SongDiscoverer discoverer, SongDiscoverConfigurations configuration ) {
		this.discoverer = discoverer;
		this.configuration = configuration;
	}
	
    @Override 
    public void onApplicationEvent( ContextRefreshedEvent event ) {
    	logger.info("Evento recibido: {}", event );
    	long nanos = System.nanoTime();
    	
    	for ( String path : configuration.paths ) {
    		discoverer.discoverSongsOnFolder( path );	
    	}
    	
    	nanos = System.nanoTime() - nanos;
    	logger.info("All songs loaded in: " + ( nanos / 1000000.0) + " ms");
    }
}



