package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;
import ru.vsu.cs.shi.piece.Piece;

import java.util.Set;

import static java.util.Collections.emptySet;

public class BoardConsoleRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
    public static final String ANSI_HIGHLIGHTED_SQUARE_BACKGROUND = "\u001B[42m";
    public void render(Board board, Piece pieceToMove) {
        Set<Coordinates> availableMoveSquares = emptySet();
        if (pieceToMove != null) {
            availableMoveSquares = pieceToMove.getAvailableMoveSquares(board);
        }

        for (int rank = 11; rank >= 0; rank--) {
            String line = "";

            if (rank == 10) {
                line += rank;
            } else if (rank <= 9 && rank >= 1){
                line += " " + rank;
            }
            for (File file : File.values()) {
                if (rank <= 10 && rank >= 1) {
                    Coordinates coordinates = new Coordinates(file, rank);
                    boolean isHighlight = availableMoveSquares.contains(coordinates);

                    if (board.isSquareEmpty(coordinates)) {
                        line += getSpriteForEmptySquare(coordinates, isHighlight);
                    } else {
                        line += getPieceSprite(board.getPiece(coordinates), isHighlight);
                    }
                } else {
                    if (file == File.A){
                        line += "   " + file;
                    } else {
                        line += "  " + file;
                    }
                }
            }

            line += ANSI_RESET;
            if (rank <= 10 && rank >= 1){
                line += rank;
            }
            System.out.println(line);
        }
    }

    public void render(Board board) {
        render(board, null);
    }

    private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlighted){
        // format = background color + font color + text
        String result = sprite;

        if (pieceColor == Color.WHITE){
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }

        if (isHighlighted){
            result = ANSI_HIGHLIGHTED_SQUARE_BACKGROUND + result;
        } else if(isSquareDark){
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }

        return result;
    }


    private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighlight) {
        return colorizeSprite("   ", Color.WHITE, Board.isSquareDark(coordinates), isHighlight);
    }

    private String selectUnicodeSpriteForPiece(Piece piece){
        switch (piece.getClass().getSimpleName()){
            case "Pawn":
                return "P";
                //return "♙";

            case "Knight":
                return "N";
                //return "♘";

            case "Bishop":
                return "B";
                //return "♗";

            case "Rook":
                return "R";
                //return "♖";

            case "King":
                return "K";
                //return "♔";

            case "Queen":
                return "Q";
                //return "♕";

            case "Maharajah":
                return "M";

            case "Archbishop":
                return "A";

            case "Chancellor":
                return "C";
        }
        return "";
    }
    private String getPieceSprite(Piece piece, boolean isHighlight) {
        return colorizeSprite(
                " " + selectUnicodeSpriteForPiece(piece) + " ", piece.color, Board.isSquareDark(piece.coordinates),
                isHighlight
        );
    }
}
