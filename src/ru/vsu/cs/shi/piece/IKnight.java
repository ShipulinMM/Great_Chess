package ru.vsu.cs.shi.piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface IKnight {
    default Set<CoordinatesShift> getKnightMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 2),
                new CoordinatesShift(2, 1),

                new CoordinatesShift(2, -1),
                new CoordinatesShift(1, -2),

                new CoordinatesShift(-2, -1),
                new CoordinatesShift(-1, -2),

                new CoordinatesShift(-1, 2),
                new CoordinatesShift(-2, 1)
        ));
    }
}
