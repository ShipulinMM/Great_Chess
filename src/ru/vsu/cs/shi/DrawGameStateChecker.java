package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;
import ru.vsu.cs.shi.piece.Piece;

import java.util.List;
import java.util.Set;

public class DrawGameStateChecker extends GameStateChecker{

    @Override
    public GameState check(Board board, Color color) {
        List<Piece> WhitePieces = board.getPiecesByColor(Color.WHITE);
        List<Piece> BlackPieces = board.getPiecesByColor(Color.BlACK);

        if (WhitePieces.size() > 1 || BlackPieces.size() > 1) {
            return GameState.ONGOING;
        }

        return GameState.DRAW;
    }
}