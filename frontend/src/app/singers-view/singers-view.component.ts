import { Component, inject, signal } from '@angular/core';
import { SingerService } from "./singer.service"
import { Singer } from "./singer"
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-singers-view',
  imports: [ReactiveFormsModule],
  templateUrl: './singers-view.component.html',
  styleUrl: './singers-view.component.css'
})
export class SingersViewComponent {
  singerService = inject( SingerService );
  singers = signal<Singer[]>([]);
  newSingerForm = new FormGroup({
    newSinger : new FormControl("")
  })

  constructor() {
    this.readSingersList();
  }

  private readSingersList() {
    this.singerService.getAllSingers().then((singersList: Singer[]) => {
      this.singers.set( singersList );
    });
  }

  async removeSinger( event: Event ) {
    const singerToDelete = ( event.target as HTMLInputElement ).value;
    await this.singerService.deleteSinger( singerToDelete );
    this.readSingersList();
  }

  async addSinger() {
    console.log("Add singer clicked " + this.newSingerForm.value.newSinger );
	if ( this.newSingerForm.value.newSinger ) {
    	await this.singerService.newSinger( this.newSingerForm.value.newSinger.toString() );
	}
    this.readSingersList();
  }
}
