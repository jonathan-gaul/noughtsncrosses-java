import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from '../models/game';
import { Play } from '../models/play';
import { Observable, first, shareReplay, ReplaySubject, Subscription } from 'rxjs';
import { StompService } from './stomp-service.service';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  gameObservable?: Observable<Game>;
  gameSubject: ReplaySubject<Game>;
  gameSubscription?: Subscription;

  private gamesUrl: string;

  private endpointUrl(name?: string): string {
    return `${this.gamesUrl}/${name}`;
  }

  constructor(private http: HttpClient, private stompService: StompService) {
    this.gamesUrl = 'http://localhost:8080/games';
    this.gameSubject = new ReplaySubject(1);
  }

  /**
   * Watch a game for changes.
   * @param key The game's key.
   */
  private watch(key: string) {
    this.stompService.watch('/games/changes/' + key)
      .subscribe(m => {
        this.gameSubject?.next(JSON.parse(m.body));
      });
  }

  /**
   * Create a new playable game.
   * @returns A Game representing the new game.
   */
  public create(key?: string): Observable<Game> {
    this.http.post<Game>(this.endpointUrl(key), {})
      .pipe(first())
      .subscribe(g => {
        // Publish the updated game.
        this.gameSubject?.next(g);
        // Also subscribe for updates to the game.
        this.watch(g.Key);
      });

    return this.gameSubject.asObservable();
  }

  /**
   * Gets a game by its key.
   * @param key The game key of the game to retrieve.
   * @returns An Observable Game.
   */
  public get(key: string, refresh: boolean = false): Observable<Game> {
    if (refresh || !this.gameObservable) {
      this.http.get<Game>(this.endpointUrl(key))
        .pipe(first())
        .subscribe(g => {
          this.gameSubject?.next(g);
          // Connect to WebSocket to get changes.
          this.watch(g.Key);
        });
    }

    return this.gameSubject.asObservable();
  }

  /**
   * Makes a play in a game.
   * @param key The game key of the game to play.
   * @param r The row index of the grid cell to play in.
   * @param c The column index of the grid cell to play in.
   * @param piece The piece to play.
   * @returns The updated Game.
   */
  public play(key: string, r: number, c: number, piece: string): Observable<Game> {
    console.log('play request');
    this.http.put<Game>(this.endpointUrl(key), new Play(r, c, piece))
      .pipe(first())
      .subscribe(g => {
        console.log('play response');
        this.gameSubject?.next(g);
      });

    return this.gameSubject.asObservable();
  }
}
