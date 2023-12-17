package ru.vsu.cs.shi.board;

import ru.vsu.cs.shi.Color;
import ru.vsu.cs.shi.Coordinates;
import ru.vsu.cs.shi.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Board {
    public final String startingFen;
    private HashMap<Coordinates, Piece> pieces = new HashMap<>();

    public List<Move> moves = new ArrayList<>();

    public Board(String startingFen) {
        this.startingFen = startingFen;
    }

    public void setPiece(Coordinates coordinates, Piece piece){
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }

    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);

        removePiece(move.from);
        setPiece(move.to, piece);

        if (validateIfPawnTransformationToQueen(piece.color, move) ) {
            removePiece(move.from);
            setPiece(move.to, new Queen(piece.color, move.from));
        }

        moves.add(move);
    }

    private boolean validateIfPawnTransformationToQueen(Color color, Move move) {
        int count = 0;
        if (getPiecesByColor(color).stream().anyMatch(piece -> piece instanceof Pawn)) {
            Piece pawn = getPiecesByColor(color).stream().filter(piece -> piece instanceof Pawn).findFirst().get();
            while (pawn.coordinates != move.to && count < 10){
                pawn = getPiecesByColor(color).stream().filter(piece -> piece instanceof Pawn).findAny().get();
                count++;
            }
            return (pawn.coordinates.rank == 9 && color == Color.WHITE) || (pawn.coordinates.rank == 2 && color == Color.BlACK) ;
        } else {
            return false;
        }
    }

    public boolean isSquareEmpty(Coordinates coordinates){
        return !pieces.containsKey(coordinates);
    }

    public Piece getPiece(Coordinates coordinates){
        return pieces.get(coordinates);
    }

    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    public List<Piece> getPiecesByColor(Color color) {
        List<Piece> result = new ArrayList<>();

        for (Piece piece : pieces.values()) {
            if (piece.color == color) {
                result.add(piece);
            }
        }
        return result;
    }


    public boolean isSquareAttackedByColor(Coordinates coordinates, Color color) {
        List<Piece> pieces = getPiecesByColor(color);

        for (Piece piece : pieces) {
            Set<Coordinates> attackedSquares = piece.getAttackedSquares(this);

            if (attackedSquares.contains(coordinates)) {
                return true;
            }
        }

        return false;
    }
}
