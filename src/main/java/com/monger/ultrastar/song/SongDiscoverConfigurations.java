package com.monger.ultrastar.song;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SongDiscoverConfigurations {
	private final Logger logger = LoggerFactory.getLogger( SongDiscoverConfigurations.class );
	private static final String CONFIGURATION_FILE="discover.properties";
	private static final String PATH_PROPERTY = "discover.path";
	
	public List<String> paths;
	
	@PostConstruct
	public void loadConfigurationFile() {
		String currentDir = System.getProperty( "user.dir" );
		logger.info("Reading configuration file: {}", currentDir + "/" + CONFIGURATION_FILE);
		try ( FileReader fr = new FileReader( currentDir + "/" + CONFIGURATION_FILE)){
			Properties properties = new Properties();
			properties.load( fr );
			paths = Arrays.asList( properties.getProperty( PATH_PROPERTY ));
			logger.info( "Paths to discover: {}", paths );
		}
		catch ( IOException e ) {
			logger.error( "Error reading the configuration file: ", e );
			paths = Collections.emptyList();
		}
	}
}
