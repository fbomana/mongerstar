import { Injectable } from '@angular/core';

import { Turn } from "./turn";
import { getEndpointUrl } from '../utils';
import { Song } from "../songs-view/song";
import { Singer } from "../singers-view/singer"

@Injectable({
  providedIn: 'root'
})
export class QueueService {

  queueEndPoint="/queue";
  currentTurnEndPôint="/queue/turn";

  constructor() { }

  async getQueue(): Promise<Turn[]> {
    const data = await fetch( 
		getEndpointUrl( this.queueEndPoint ),
		{
			method : "GET",
			cache: "no-store"
		}
  	);
    return (await data.json()) ?? [];
  }

  async getCurrentTurn(): Promise<Turn> {
    const data = await fetch( 
  		getEndpointUrl( this.currentTurnEndPôint ),
  		{
  			method : "GET",
  			cache: "no-store"
  		}
  	);
    return (await data.json()) ?? [];
  }
  
  async submitNewTurn( song: Song, singer1 : Singer, singer2: Singer ) {
	const request = { 
		"song" : song,
		"singer1" : singer1,
		"singer2" : singer2 
	};
	const data = await fetch( 
		getEndpointUrl( this.currentTurnEndPôint ),
		{ method : "POST", 
		  body: JSON.stringify( request ), 
	      headers: {
			"Content-Type": "application/json",
		  },
		  cache: "no-store"
		});
	if ( !data.ok ) {
	  return await data.text();
	}
	return "";
  }
}
