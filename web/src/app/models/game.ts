export class Game {
  constructor(public Key: string,
              public Turn: number,
              public GridSize: number,
              public Grid: string[],
              public CurrentPiece: string,
              public Winner?: string,
              public IsFinished: boolean = false) { }
}
