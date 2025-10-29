import { Injectable } from '@angular/core';

import { Song } from './song'

@Injectable({
  providedIn: 'root'
})
export class SongService {

  url = "http://localhost:8080/song";
  
	async getAllSongs(): Promise<Song[]> {
    	const data = await fetch( 
  		this.url,
  		{
  			method : "GET",
  			cache: "no-store"
  		});
    	return ( await data.json() ) ?? [];
	}
	
	async getSongsByTitle( title : String ): Promise<Song[]> {
		const data = await fetch( 
		this.url + "/title/" + title,
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
}