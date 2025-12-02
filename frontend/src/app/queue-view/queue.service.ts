import { Injectable } from '@angular/core';

import { Turn } from "./turn"

@Injectable({
  providedIn: 'root'
})
export class QueueService {

  url = "http://localhost:8080/queue";

  constructor() { }

  async getQueue(): Promise<Turn[]> {
	console.log("Pidiendo la cola");
    const data = await fetch( 
		this.url,
		{
			method : "GET",
			cache: "no-store"
		}
  	);
    return (await data.json()) ?? [];
  }

  async getCurrentTurn(): Promise<Turn> {
	console.log("Pidiendo el turno actual");
    const data = await fetch( 
  	this.url + "/turn",
  	{
  		method : "GET",
  		cache: "no-store"
  	}
  );
    return (await data.json()) ?? [];
  }
}
