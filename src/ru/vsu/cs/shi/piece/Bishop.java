package ru.vsu.cs.shi.piece;

import ru.vsu.cs.shi.Color;
import ru.vsu.cs.shi.Coordinates;

import java.util.Set;

public class Bishop extends LongRangePiece implements IBishop{
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getBishopMoves();
    }
}
