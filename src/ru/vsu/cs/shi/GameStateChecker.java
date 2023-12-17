package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;

public abstract class GameStateChecker {
   public abstract GameState check(Board board, Color color);

}
