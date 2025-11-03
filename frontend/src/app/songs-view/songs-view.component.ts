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
	authors = signal<String[]>( [] );
	languages = signal<String[]>( [] );
	searchSongsForm = new FormGroup({
		title : new FormControl(""),
		author : new FormControl(""),
		language : new FormControl("")
	});
	
	constructor() {
		this.readSongList( "", "", "" );
		this.readAuthors();
		this.readLanguages();
	}
	
	private readSongList( title : String, author: String, language : String ) {
	
		if ( title ) {
			this.songService.getSongsByTitle( title ).then((songsList: Song[]) => {
			    this.songs.set( songsList );
			 });
		}
		else if ( author ) {
			this.songService.getSongsByAuthor( author ).then((songsList: Song[]) => {
			    this.songs.set( songsList );
			 });
		}
		else if ( language ) {
			this.songService.getSongsByLanguage( language ).then((songsList: Song[]) => {
			    this.songs.set( songsList );
			 });			
		}
		else {
			this.songService.getAllSongs().then((songsList: Song[]) => {
			    this.songs.set( songsList );
	  	 	});
		 }
	}
	
	private readAuthors() {
		this.songService.getAllAuthors().then(( authorsList : String[]) => {
			this.authors.set( authorsList );
		});
	}
	
	private readLanguages() {
		this.songService.getAllLanguages().then(( languagesList : String[]) => {
			this.languages.set( languagesList );
		});
	}
	
	async searchSongs() {
		if ( this.searchSongsForm.value.title ) {
			this.readSongList( this.searchSongsForm.value.title.toString(), "", "" );
		}
		else if ( this.searchSongsForm.value.author ) {
			this.readSongList( "", this.searchSongsForm.value.author.toString(), "" );
		}
		else if ( this.searchSongsForm.value.language ) {
			this.readSongList( "", "", this.searchSongsForm.value.language.toString() );
		}
		else {
			this.readSongList( "", "", "" );
		}
	}
	
	async reset() {
		this.searchSongsForm.patchValue( { title : "", author:"", language:"" }); 
		this.readSongList( "", "", "" );
	}
}
