import { Injectable } from '@angular/core';

import { Song } from './song'
import { getEndpointUrl } from '../utils'

@Injectable({
  providedIn: 'root'
})
export class SongService {

  	allSongsEndPoint = "/song";
	allSongsAuthorsEndPoint = "/song/author";
	allSongsLangagesEndPoint = "/song/language";
	searchSongsByTitleEndPoint = "/song/title/";
	searchSongsByAuthorEndPoint = "/song/author/";
	searchSongsByLanguageEndPoint = "/song/language/";
  
	async getAllSongs(): Promise<Song[]> {
    	const data = await fetch( 
  		getEndpointUrl( this.allSongsEndPoint ),
  		{
  			method : "GET",
  			cache: "no-store"
  		});
    	return ( await data.json() ) ?? [];
	}
	
	async getAllAuthors(): Promise<string[]> {
		const data = await fetch( 
		getEndpointUrl( this.allSongsAuthorsEndPoint ),
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
	
	async getAllLanguages(): Promise<string[]> {
		const data = await fetch( 
		getEndpointUrl( this.allSongsLangagesEndPoint ),
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}

	async getSongsByTitle( title : string ): Promise<Song[]> {
		const data = await fetch( 
		getEndpointUrl( this.searchSongsByTitleEndPoint + title ),
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
	
	async getSongsByAuthor( author : string ): Promise<Song[]> {
		const data = await fetch( 
		getEndpointUrl( this.searchSongsByAuthorEndPoint + author ),
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
	
	async getSongsByLanguage( language : string ): Promise<Song[]> {
		const data = await fetch( 
		getEndpointUrl( this.searchSongsByLanguageEndPoint + language ),
		{
			method : "GET",
			cache: "no-store"
		});
		return ( await data.json() ) ?? [];
	}
}