package ru.vsu.cs.shi;

import ru.vsu.cs.shi.piece.CoordinatesShift;

import java.util.Objects;

public class Coordinates {
    public final File file;
    public final Integer rank;

    public Coordinates(File file, Integer rank) {
        this.file = file;
        this.rank = rank;
    }

    public Coordinates shift(CoordinatesShift shift){
        return new Coordinates(File.values()[this.file.ordinal() + shift.fileShift], this.rank + shift.rankShift);
    }
    public boolean canShift(CoordinatesShift shift){
        int f = file.ordinal() + shift.fileShift;
        int r = rank + shift.rankShift;
        if ((f < 0) || (f > 9)) return false;
        if ((r < 1) || (r > 10)) return false;

        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return file == that.file && rank.equals(that.rank);
    }

    @Override
    public int hashCode() {
        int result = file.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}
