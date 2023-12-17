package ru.vsu.cs.shi.piece;

import ru.vsu.cs.shi.Color;
import ru.vsu.cs.shi.Coordinates;

import java.util.Set;

public class Maharajah extends LongRangePiece implements IRook, IKnight, IBishop{
    public Maharajah(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> moves = getKnightMoves();
        moves.addAll(getRookMoves());
        moves.addAll(getBishopMoves());

        return moves;
    }
}
