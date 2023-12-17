package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;
import ru.vsu.cs.shi.board.BoardFactory;
import ru.vsu.cs.shi.board.Move;
import ru.vsu.cs.shi.piece.King;
import ru.vsu.cs.shi.piece.Pawn;
import ru.vsu.cs.shi.piece.Piece;
import ru.vsu.cs.shi.piece.Queen;

import java.util.*;


public class InputCoordinates {

    private static final Scanner scanner = new Scanner(System.in);

    public static Integer computerMode() {
        String line = scanner.nextLine();
        if (Objects.equals(line, "atc")) {
            return 1;
        } else if (Objects.equals(line, "atp")) {
            return 0;
        } else {
            System.out.println("Invalid form");
            return -1;
        }
    }

    public static Coordinates input() {
        while (true) {
            System.out.println("Please enter coordinates (ex. a1)");

            String line = scanner.nextLine();

            if (line.length() != 2 && line.length() != 3) {
                System.out.println("Invalid form");
                continue;
            }

            char fileChar = line.charAt(0);
            char rankChar = line.charAt(1);
            char errorChar = line.charAt(1);
            if  (line.length() == 3){
                rankChar = line.charAt(2);
            }

            if  (line.length() == 3 && Character.getNumericValue(rankChar) != 0) {
                System.out.println("Invalid form");
                continue;
            }

            if (!Character.isDigit(errorChar)) {
                System.out.println("Invalid form");
                continue;
            }

            int error = Character.getNumericValue(errorChar);
            if  (line.length() == 3 && error != 1) {
                System.out.println("Invalid form");
                continue;
            }


            if (!Character.isLetter(fileChar)) {
                System.out.println("Invalid form");
                continue;
            }

            if (!Character.isDigit(rankChar)) {
                System.out.println("Invalid form");
                continue;
            }

            int rank = Character.getNumericValue(rankChar);
            if (line.length() == 3){
                rank = 10;
            }
            if (rank < 1 || rank > 10) {
                System.out.println("Invalid form");
                continue;
            }

            File file = File.fromChar(fileChar);
            if (file == null) {
                System.out.println("Invalid form");
                continue;
            }

            return new Coordinates(file, rank);
        }
    }

    public static Coordinates inputPieceCoordinatesForColor(Color color, Board board) {
       while (true) {
           System.out.println("Enter coordinates for piece to move");
           Coordinates coordinates = input();

           if (board.isSquareEmpty(coordinates)) {
               System.out.println("Empty square");
               continue;
           }

           Piece piece = board.getPiece(coordinates);

           if (piece.color != color) {
               System.out.println("Wrong color");
               continue;
           }

           Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

           if (availableMoveSquares.size() == 0) {
               System.out.println("Blocked piece");
               continue;
           }

           return coordinates;
       }
    }

    public static Move inputMove (Board board, Color color, BoardConsoleRenderer renderer) {
        while (true) {
            //input
            Coordinates sourceCoordinate = InputCoordinates.inputPieceCoordinatesForColor(
                    color, board
            );

            Piece piece = board.getPiece(sourceCoordinate);
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            renderer.render(board, piece);
            Coordinates targetCoordinates = InputCoordinates.inputAvailableSquare(availableMoveSquares);

            Move move = new Move(sourceCoordinate, targetCoordinates);

            if (validateIfKingInCheckAfterMove(board, color, move)) {
                System.out.println("Your king is under attack!");
                continue;
            }

            return move;
        }
    }

    public static Move intelligenceMove(Board board, Color color, BoardConsoleRenderer renderer) {
        List<Piece> pieces = board.getPiecesByColor(color);
        Random random = new Random();

        for (Piece piece : pieces) {
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            for (Coordinates targetCoordinates : availableMoveSquares) {
                Move move = new Move(piece.coordinates, targetCoordinates);

                if (board.getPiece(targetCoordinates) != null && board.getPiece(targetCoordinates).color != color) {
                    if (validateIfKingInCheckAfterMove(board, color, move)) {
                        return getKingMove(board,color,renderer);
                    }
                    renderer.render(board, piece);
                    return move;
                }
            }
        }

        Piece randomPiece = pieces.get(random.nextInt(pieces.size()));
        Set<Coordinates> randomMoveSquares = randomPiece.getAvailableMoveSquares(board);
        Coordinates randomTargetCoordinates = randomMoveSquares.stream().findAny().orElse(null);

        Move move = new Move(randomPiece.coordinates, randomTargetCoordinates);

        if (validateIfKingInCheckAfterMove(board, color, move)) {
            return getKingMove(board,color,renderer);
        }

        renderer.render(board, randomPiece);
        return move;
    }

    private static Move getKingMove (Board board, Color color, BoardConsoleRenderer renderer) {
        Piece king = board.getPiecesByColor(color).stream().filter(chessman -> chessman instanceof King).findFirst().get();
        Set<Coordinates> kingMoves = king.getAvailableMoveSquares(board);
        Coordinates randomTargetCoordinatesKing = kingMoves.stream().findAny().orElse(null);
        renderer.render(board, king);
        return new Move(king.coordinates, randomTargetCoordinatesKing);
    }

    private static boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = (new BoardFactory()).copy(board);
        copy.makeMove(move);

        // при условии, что король есть на доске
        Piece king = copy.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        return copy.isSquareAttackedByColor(king.coordinates, color.opposite());
    }

    public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates) {
        while (true) {
            System.out.println("Enter your move for selected piece");
            Coordinates input = input();

            if (!coordinates.contains(input)) {
                System.out.println("Non-available square");
                continue;
            }

            return input;
        }
    }
}
