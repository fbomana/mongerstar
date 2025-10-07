import { Injectable } from '@angular/core';

import { Singer } from './singer'

@Injectable({
  providedIn: 'root'
})
export class SingerService {

  url = "/singer";

  constructor() { }

  async getAllSingers(): Promise<Singer[]> {
    const data = await fetch( 
		this.url,
		{
			method : "GET",
			cache: "no-store"
		}
  );
    return (await data.json()) ?? [];
  }

  async deleteSinger( name : string ) : Promise<string>{
    const data = await fetch( this.url + "/" + name, {method : "DELETE"} );
    if ( !data.ok ) {
      return await data.text();
    }
    return "";
  }

  async newSinger( name: string ) : Promise<string> {
    const singer = { "name" : name };
    const data = await fetch( this.url, 
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
