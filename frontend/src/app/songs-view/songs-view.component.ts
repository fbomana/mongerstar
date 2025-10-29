import { Component, inject, signal } from '@angular/core';
import { Song } from './song';
import { SongService } from './song.service'
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-songs-view',
  imports: [ReactiveFormsModule],
  templateUrl: './songs-view.component.html',
  styleUrl: './songs-view.component.css'
})
export class SongsViewComponent {
	songService = inject( SongService )
	songs = signal<Song[]>([]);
	searchSongsForm = new FormGroup({
		title : new FormControl("")
	});
	
	constructor() {
		this.readSongList( "", "" );
	}
	
	private readSongList( title : String, author: String ) {
	
		if ( title ) {
			this.songService.getSongsByTitle( title ).then((songsList: Song[]) => {
			    this.songs.set( songsList );
			 });
		}
		else if ( author ) {
			
		}
		else {
			this.songService.getAllSongs().then((songsList: Song[]) => {
			    this.songs.set( songsList );
	  	 	});
		 }
	}
	
	async searchSongs() {
		if ( this.searchSongsForm.value.title ) {
			this.readSongList( this.searchSongsForm.value.title.toString(), "" );
		}
	}
	
	async reset() {
		this.searchSongsForm.patchValue( { title : "" }); 
		this.readSongList("", "" );
	}
}
