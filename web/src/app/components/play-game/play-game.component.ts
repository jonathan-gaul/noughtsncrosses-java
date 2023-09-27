import { Component, OnInit, } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game-service.service';
import { Observable, of, Subscription } from 'rxjs';
import { Location } from '@angular/common';

@Component({
  selector: 'app-play-game',
  templateUrl: './play-game.component.html',
  styleUrls: ['./play-game.component.css']
})
export class PlayGameComponent implements OnInit {

  key?: string;
  piece?: string;

  gameSubscription?: Subscription;
  game$?: Observable<Game>;
  rows$?: Observable<number[]>;
  cols$?: Observable<number[]>;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService,
    private location: Location
    ) { }

  /**
   * Place a piece in the given cell.
   * @param r Row number of the cell.
   * @param c Column number of the cell.
   */
  play(r: number, c: number) {
    if (this.key !== undefined && this.piece !== undefined) {
      this.gameService.play(this.key, r, c, this.piece);
    }
  }


  /**
   * Start a new game.
   */
  start() {
    return this.gameService.create();
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {

      console.log(params);

      const key = params.get('key');
      const piece = params.get('piece');

      if (key === null || key == '') {
        this.game$ = this.start();
      }
      else {
        this.game$ = this.gameService.get(key);
      }

      this.gameSubscription = this.game$.subscribe(x => {
        this.key = x.Key;
        this.rows$ = of(Array.from(Array(x.GridSize), (_,i) => i));
        this.cols$ = of(Array.from(Array(x.GridSize), (_,i) => i));
        this.piece = x.CurrentPiece;
        this.location.replaceState('/play', 'key=' + this.key);
      });
    });
  }

  ngOnDestroy() {
    this.gameSubscription?.unsubscribe();
  }

}
