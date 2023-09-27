import { Component } from '@angular/core';
import { Game } from '../../models/game';
import { GameService } from '../../services/game-service.service';

@Component({
  selector: 'game-setup',
  templateUrl: './game-setup.component.html',
  styleUrls: ['./game-setup.component.css']
})
export class GameSetupComponent {

  constructor(private gameService: GameService) {
  }

  ngOnInit() {
  }

}
