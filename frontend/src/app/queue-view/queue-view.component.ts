import { Component, inject, signal, HostListener } from '@angular/core';
import { ActivatedRouteSnapshot,  RouterStateSnapshot } from '@angular/router';
import { QueueService } from "./queue.service"
import  { Turn } from "./turn"
import { getEndpointUrl, CanComponentDeactivate, CanDeactivateType } from '../utils';

@Component({
  selector: 'app-queue-view',
  imports: [],
  templateUrl: './queue-view.component.html',
  styleUrl: './queue-view.component.css'
})
export class QueueViewComponent implements CanComponentDeactivate {
    eventEndPoint="/api/queue/events"

	queueService = inject( QueueService )
	currentTurn = signal<Turn>({
		singer1 : {name:"", score : 0}, singer2 : {name:"", score:0}, song : { title : "Get the mongers ready to sing", language : "", author: ""}, completed : false
	});
	turnQueue = signal<Turn[]>([]);
    evtSource : EventSource;
    id : number;
	
	constructor() {
        this.id = Math.floor(Math.random() * 100000001);
		this.refreshScreen();
        this.evtSource = new EventSource( getEndpointUrl( this.eventEndPoint ) + "/" + this.id );
        this.evtSource.onmessage = (e) => {
            this.refreshScreen();
        };
	}

    public async canDeactivate () {
        console.log( "Try to close EventSource");
        console.log( this.id );
        await this.evtSource.close();
        console.log( this.id );
        if ( this.id ) {
            console.log( 2 );
            this.queueService.unsubscribe( this.id );
        }
        return true;
    }

	private refreshScreen() {
		this.queueService.getCurrentTurn().then(( turn : Turn  ) => {
			console.log("Recived turn: ", turn );
			this.currentTurn.set( turn )
		});
		this.queueService.getQueue().then( ( queue : Turn[]) => {
			console.log("Recived queue: ", queue );
			this.turnQueue.set( queue );
		});
	}
	
	async nextTurn() {
		if ( this.turnQueue().length > 0 ) {			
			await this.queueService.next();
			this.refreshScreen();
		}
	}

	async delayTurn() {
		if ( this.turnQueue().length > 0 ) {
			await this.queueService.delay();
			this.refreshScreen();
		}
	}

    async removeTurn( turn : Turn ) {
        if ( turn ) {
            await this.queueService.remove( turn );
            this.refreshScreen();
        }
    }
}
