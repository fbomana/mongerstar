import { Component, inject, signal } from '@angular/core';
import { QueueService } from "./queue.service"
import  { Turn } from "./turn"

@Component({
  selector: 'app-queue-view',
  imports: [],
  templateUrl: './queue-view.component.html',
  styleUrl: './queue-view.component.css'
})
export class QueueViewComponent {
	queueService = inject( QueueService )
	currentTurn = signal<Turn>({
		singer1 : {name:""}, singer2 : {name:""}, song : { title : "Get the mongers ready to sing", language : "", author: ""}, completed : false
	});
	turnQueue = signal<Turn[]>([]);
	
	constructor() {
		this.refreshScreen();
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
}
