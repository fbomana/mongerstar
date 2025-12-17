import { Injectable } from '@angular/core';

import { Turn } from "./turn"
import { getEndpointUrl } from '../utils'

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
}
