import { Routes } from '@angular/router';
import { QueueViewComponent } from './queue-view/queue-view.component';
import { SongsViewComponent } from './songs-view/songs-view.component';
import { SingersViewComponent } from './singers-view/singers-view.component';
import { canDeactivateGuard }  from './utils'




export const routes: Routes = [
    {path:'',redirectTo:'queue', pathMatch: 'full' },
    {
        path : "queue",
        component : QueueViewComponent,
        title : "MongerStar turns queue",
        canDeactivate: [canDeactivateGuard],
    }, 
    {
        path : "songs",
        component : SongsViewComponent,
        title : "MongerStar songs library"
    }, 
    {
        path : "singers",
        component : SingersViewComponent,
        title : "MongerStar singer management"
    },
    
];
