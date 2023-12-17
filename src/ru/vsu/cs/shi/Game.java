package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;
import ru.vsu.cs.shi.board.Move;
import java.util.List;

public class Game {
    private final Board board;

    private final BoardConsoleRenderer renderer = new BoardConsoleRenderer();

    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(),
            new CheckmateGameStateChecker(),
            new DrawGameStateChecker()
    );

    public Game(Board board) {
        this.board = board;
    }

    public void gameLoop() {
        Color colorToMove = Color.WHITE;

        GameState state = determineGameState(board, colorToMove);

        System.out.println("Choose the game mode: against the player; against the computer");
        System.out.println("Commands for modes:");
        System.out.println("against the player: atp");
        System.out.println("against the computer: atc");
        System.out.println("Please enter modes");
        int mode = InputCoordinates.computerMode();
        while (mode == -1) {
            mode = InputCoordinates.computerMode();
            break;
        }

        while (state == GameState.ONGOING) {
            renderer.render(board);

            if (colorToMove == Color.WHITE) {
                System.out.println("White to move");
            } else {
                System.out.println("Black to move");
            }

            Move move = InputCoordinates.inputMove(board, colorToMove, renderer);

            // make move
            board.makeMove(move);

            // pass move
            colorToMove = colorToMove.opposite();

            // artificial intelligence
            if (colorToMove == Color.BlACK && mode == 1) {
                Move intelligenceMove = InputCoordinates.intelligenceMove(board, colorToMove, renderer);

                board.makeMove(intelligenceMove);

                colorToMove = colorToMove.opposite();
            }

            state = determineGameState(board, colorToMove);
        }

        renderer.render(board);
        System.out.println("Game ended with state = " + state);
    }

    private GameState determineGameState(Board board, Color color) {
        for (GameStateChecker checker : checkers) {
            GameState state = checker.check(board, color);

            if (state != GameState.ONGOING){
                return state;
            }
        }

        return GameState.ONGOING;
    }
}
