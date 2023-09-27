import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameSetupComponent } from './components/game-setup/game-setup.component';
import { PlayGameComponent } from './components/play-game/play-game.component';

const routes: Routes = [
  { path: 'play', component: PlayGameComponent }
  // { path: '', component: GameSetupComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
