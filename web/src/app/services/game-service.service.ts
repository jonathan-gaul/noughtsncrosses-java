import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from '../models/game';
import { Play } from '../models/play';
import { Observable, first, shareReplay, ReplaySubject, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  gameObservable?: Observable<Game>;
  gameSubject: ReplaySubject<Game>;
  gameSubscription?: Subscription;

  private gamesUrl: string;

  private endpointUrl(name: string): string {
    return `${this.gamesUrl}/${name}`;
  }

  constructor(private http: HttpClient) {
    this.gamesUrl = 'http://localhost:8080/games';
    this.gameSubject = new ReplaySubject(1);
  }

  /**
   * Create a new playable game.
   * @returns A Game representing the new game.
   */
  public create(): Observable<Game> {
    this.http.post<Game>(this.gamesUrl, {})
      .pipe(first())
      .subscribe(g => this.gameSubject?.next(g));

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
        .subscribe(g => this.gameSubject?.next(g));
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
    this.http.put<Game>(this.endpointUrl(key), new Play(r, c, piece))
      .pipe(first())
      .subscribe(g => this.gameSubject?.next(g));

    return this.gameSubject.asObservable();
  }
}
