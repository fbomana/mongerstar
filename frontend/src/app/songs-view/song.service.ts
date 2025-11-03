import { Injectable } from '@angular/core';

import { Song } from './song'

@Injectable({
  providedIn: 'root'
})
export class SongService {

  url = "http://192.168.1.3:8080/song";
  
	async getAllSongs(): Promise<Song[]> {
    	const data = await fetch( 
  		this.url,
  		{
  			method : "GET",
  			cache: "no-store"
  		});
    	return ( await data.json() ) ?? [];
	}
	
	async getAllAuthors(): Promise<String[]> {
		const data = await fetch( 
		this.url + "/author",
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
	
	async getAllLanguages(): Promise<String[]> {
		const data = await fetch( 
		this.url + "/language",
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
	
	async getSongsByAuthor( author : String ): Promise<Song[]> {
		const data = await fetch( 
		this.url + "/author/" + author,
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
	
	async getSongsByLanguage( language : String ): Promise<Song[]> {
		const data = await fetch( 
		this.url + "/language/" + language,
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
}