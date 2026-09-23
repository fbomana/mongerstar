import { Component, inject, signal } from '@angular/core';
import { ProposalsService } from "./proposals.service"
import { Proposal } from "./proposal"
import { Singer } from "../singers-view/singer"
import { SingerService } from "../singers-view/singer.service"
import { QueueService } from "../queue-view/queue.service"
import { Song } from "../songs-view/song";
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-proposals-view',
  imports: [ReactiveFormsModule],
  templateUrl: './proposals-view.component.html',
  styleUrl: './proposals-view.component.css'
})
export class ProposalsViewComponent {
    service : ProposalsService = inject( ProposalsService );
    singerService : SingerService = inject( SingerService );
    queueService : QueueService = inject( QueueService );
    router = inject( Router );
    proposals = signal<Proposal[]>([]);
    singers = signal<Singer[]>([]);
    newTurnClasses = signal<string>("newturn invisible");
    errorMessage = signal<string>("");

    newTurnForm = new FormGroup({
        song : new FormControl(""),
        singer1: new FormControl(""),
        singer2: new FormControl("")
    });

    selectedSong : Song | null= null;

    constructor() {
        this.getProposals();
    }

    async removeProposal( proposal : Proposal ) {
        console.log("Clicked on remove proposal", proposal );
        await this.service.removeProposal( proposal );
        await this.getProposals();
    }

    async getProposals() {
        this.service.getProposals().then( ( proposals : Proposal[]) => {
            console.log("Recived proposals:", proposals );
            this.proposals.set( proposals );
        });
    }

    async getSingers() {
        this.singerService.getAllSingers().then( ( singers : Singer[]) => {
            console.log("Recived singers: ", singers );
            this.singers.set( singers );
        });
    }

	async createNewTurn( song : Song, singer1 : Singer  ) {
		console.log("New Turn: " + song.title );
		this.errorMessage.set("");
		this.selectedSong = song;
		this.newTurnClasses.set("newturn visible")
		this.newTurnForm.controls["song"].setValue( song.title );
		this.newTurnForm.controls["singer1"].setValue( singer1.name );
		this.getSingers()
	}

	async closeNewTurn() {

		this.newTurnForm.reset();
		this.newTurnClasses.set("newturn invisible")
		this.errorMessage.set("");
		this.selectedSong = null;
	}

	async submitNewTurn() {
		this.errorMessage.set("");
		let queue : boolean = true
		if ( this.selectedSong ) {
			if ( this.newTurnForm.value.singer1 == this.newTurnForm.value.singer2 ) {
				this.errorMessage.set("Error: you can't select the same singer twice");
				return;
			}
			const singers = this.singers();
			const s1 : Singer | undefined = singers.find( (s) => s.name == this.newTurnForm.value.singer1 );
			const s2 : Singer | undefined = singers.find( (s) => s.name == this.newTurnForm.value.singer2 );
			queue = s2 != undefined
			if ( s1 && s2 ) {
				await this.queueService.submitNewTurn( this.selectedSong, s1, s2 );
			}
		}
        this.closeNewTurn();
        this.router.navigate(["/queue"]);
    }
}
