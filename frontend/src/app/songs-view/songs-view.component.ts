import { Component, inject, signal } from '@angular/core';
import { Song } from './song';
import { SongService } from './song.service'
import { Singer } from "../singers-view/singer";
import { SingerService } from "../singers-view/singer.service";
import { QueueService } from "../queue-view/queue.service"
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-songs-view',
  imports: [ReactiveFormsModule],
  templateUrl: './songs-view.component.html',
  styleUrl: './songs-view.component.css'
})
export class SongsViewComponent {
	songService = inject( SongService );
	singerService = inject ( SingerService );
	queueService = inject( QueueService );
	router = inject( Router );
	songs = signal<Song[]>([]);
	authors = signal<string[]>( [] );
	languages = signal<string[]>( [] );
	newTurnClasses = signal<string>("newturn invisible");
	activeSingers = signal<Singer[]>( [] );
	errorMessage = signal<string>("");
	searchSongsForm = new FormGroup({
		title : new FormControl(""),
		author : new FormControl(""),
		language : new FormControl("")
	});
	selectedSong : Song | null = null;
	
	newTurnForm = new FormGroup({
		song : new FormControl(""),
		singer1: new FormControl(""),
		singer2: new FormControl("")
	});
	
	constructor() {
		this.readSongList( "", "", "" );
		this.readAuthors();
		this.readLanguages();
	}
	
	private readSongList( title : string, author: string, language : string ) {
	
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
		this.songService.getAllAuthors().then(( authorsList : string[]) => {
			this.authors.set( authorsList );
		});
	}
	
	private readLanguages() {
		this.songService.getAllLanguages().then(( languagesList : string[]) => {
			this.languages.set( languagesList );
		});
	}
	
	private readSingers() {
		this.singerService.getAllSingers().then((singersList: Singer[]) => {
		    this.activeSingers.set( singersList );
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
	
	async createNewTurn( song : Song ) {
		console.log("New Turn: " + song.title );
		this.errorMessage.set("");
		this.selectedSong = song;
		this.readSingers();
		this.newTurnClasses.set("newturn visible")
		this.newTurnForm.controls["song"].setValue( song.title );
	}
	
	async closeNewTurn() {
		
		this.newTurnForm.reset();
		this.newTurnClasses.set("newturn invisible")
		this.errorMessage.set("");
		this.selectedSong = null;
	}
	
	async submitNewTurn() {
		this.errorMessage.set("");
		if ( this.selectedSong ) {
			if ( this.newTurnForm.value.singer1 == this.newTurnForm.value.singer2 ) {
				this.errorMessage.set("Error: you can't select the same singer twice");
				return;
			}
			const singers = this.activeSingers();
			const s1 : Singer | undefined = singers.find( (s) => s.name == this.newTurnForm.value.singer1 );
			const s2 : Singer | undefined = singers.find( (s) => s.name == this.newTurnForm.value.singer2 );
			if ( s1 && s2 ) {
				await this.queueService.submitNewTurn( this.selectedSong, s1, s2 );
			}
		}
		
		this.closeNewTurn();
		this.router.navigate(["/queue"]);
	}
}
