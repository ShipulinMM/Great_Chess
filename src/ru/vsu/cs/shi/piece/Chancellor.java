package ru.vsu.cs.shi.piece;

import ru.vsu.cs.shi.Color;
import ru.vsu.cs.shi.Coordinates;

import java.util.Set;

public class Chancellor extends LongRangePiece implements IKnight, IRook{
    public Chancellor(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> moves = getKnightMoves();
        moves.addAll(getRookMoves());

        return moves;
    }
}
