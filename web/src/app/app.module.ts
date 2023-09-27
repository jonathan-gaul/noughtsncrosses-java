import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { GameSetupComponent } from './components/game-setup/game-setup.component';
import { GameService } from './services/game-service.service';
import { PlayGameComponent } from './components/play-game/play-game.component';

@NgModule({
  declarations: [
    AppComponent,
    GameSetupComponent,
    PlayGameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [GameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
