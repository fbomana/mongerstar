import { Component, inject, signal } from '@angular/core';
import { Song } from './song';
import { SongService } from './song.service'

@Component({
  selector: 'app-songs-view',
  imports: [],
  templateUrl: './songs-view.component.html',
  styleUrl: './songs-view.component.css'
})
export class SongsViewComponent {
	songService = inject( SongService )
	songs = signal<Song[]>([]);
	
	constructor() {
		this.readSongList();
	}
	
	private readSongList() {
	  this.songService.getAllSongs().then((songsList: Song[]) => {
	    this.songs.set( songsList );
	  });
	}
}
