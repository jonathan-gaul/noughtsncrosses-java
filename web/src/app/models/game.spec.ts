import { Game } from './game';

describe('Game', () => {
  it('should create an instance', () => {
    expect(new Game("test-key", 1, 3, [ "XOO", "XXO", "OOX" ], "O", undefined, false)).toBeTruthy();
  });
});
