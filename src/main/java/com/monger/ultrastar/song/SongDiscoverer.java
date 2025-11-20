package com.monger.ultrastar.song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SongDiscoverer {

	private final SongStorage storage;
	private final Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	public SongDiscoverer( SongStorage storage ) {
		this.storage = storage;
	}
	
	public void discoverSongsOnFolder( String path ) {
		try {
			Stream<Path> txtFiles = Files.find( Path.of( path ), Integer.MAX_VALUE, ( filepath, attributes ) -> !attributes.isDirectory()  && filepath.getFileName().toString().toLowerCase().endsWith(".txt"));
			List<Song> songs = txtFiles.parallel().map( filepath -> getSongFromFile( filepath )).filter( song -> song != Song.emptySong ).toList();
			txtFiles.close();
			storage.addAllSongs( songs );
		}
		catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}
	
	private Song getSongFromFile( Path path ) {
		logger.info("Reading path: {}", path );
		String encoding = "UTF-8";
		try {
			encoding = UniversalDetector.detectCharset(path);
		}
		catch( IOException e ) {
			logger.error("Problem detecting encoding of file {}: ", path, e);
		}
		
		try ( FileReader fr = new FileReader( path.toFile(), Charset.forName( encoding )); BufferedReader bf = new BufferedReader( fr )){
			String line = null;
			String title = null;
			String author = null;
			String language = null;
			while( ( line = bf.readLine()) != null ) {
				String[] parts = fixLine( line ) .split(":");
				if ( parts[0].trim().equals("")) {
					break;
				}
				else if ( parts[0].trim().equalsIgnoreCase( "#TITLE" )) {
					title = parts[1];
				}
				else if ( parts[0].trim().equalsIgnoreCase( "#ARTIST" )) {
					author = parts[1];
				}
				else if ( parts[0].trim().equalsIgnoreCase( "#LANGUAGE" )) {
					language = parts[1];
				}
				if ( language != null && title != null && author != null ) {
					Song song = new Song( title, author, language );
					logger.info("Song: {}", song );
					return song;
				}
			}
		}
		catch ( IOException e ) {
			logger.error( "Error reading path: {}", path, e );
		}
		
		return Song.emptySong;
	}
	
	private String fixLine( String line ) {
		 return line.codePoints()
	        .filter(c -> {
	          switch (Character.getType(c)) {
	            case Character.CONTROL:
	            case Character.FORMAT:
	            case Character.PRIVATE_USE:
	            case Character.SURROGATE:
	            case Character.UNASSIGNED:
	              return false;
	            default:
	              return true;
	          }
	        })
	        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
}
