import { Injectable } from '@angular/core';

import { Singer } from './singer'
import { getEndpointUrl } from '../utils'

@Injectable({
  providedIn: 'root'
})
export class SingerService {

  allSingersEndPoint = "/singer";
  deleteSingerEndPoint = "/singer/";
  newSingerEndPoint = "/singer";

  constructor() { }

  async getAllSingers(): Promise<Singer[]> {
    const data = await fetch( 
		getEndpointUrl( this.allSingersEndPoint ),
		{
			method : "GET",
			cache: "no-store"
		}
  	);
    return (await data.json()) ?? [];
  }

  async deleteSinger( name : string ) : Promise<string>{
    const data = await fetch( 
		getEndpointUrl( this.deleteSingerEndPoint + name ),
		{method : "DELETE"} );
    if ( !data.ok ) {
      return await data.text();
    }
    return "";
  }

  async newSinger( name: string ) : Promise<string> {
    const singer = { "name" : name };
    const data = await fetch( 
		getEndpointUrl( this.newSingerEndPoint ),
		{ method : "POST", 
		  body: JSON.stringify( singer ), 
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
