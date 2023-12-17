package ru.vsu.cs.shi;

import ru.vsu.cs.shi.board.Board;
import ru.vsu.cs.shi.board.BoardFactory;

public class Main {
    public static void main(String[] args) {
        Board board = (new BoardFactory()).fromFEN(
                "rnbqkmabnr/ppppccpppp/4pp4/10/10/10/10/4PP4/PPPPCCPPPP/RNBAMKQBNR w" // default
                //"10/5PPP2/k9/10/10/10/10/K9/5ppp2/10 w"  // transformation
                //"10/10/10/10/10/10/8p1/4k2pP1/4p2P2/4K4N w KQkq - 0 1" // pat
                //"K2R3r2/7r2/10/2p1p5/2pkp5/2ppp5/10/10/10/10 w KQkq - 0 1" // mat
                //"10/k2p2N3/10/4q1r3/2P7/4R1n3/K9/10/10/10 w KQkq - 0 1" // long range piece move (q)
                //"5p1p2/k2Pp1P3/5P4/K2P6/10/10/10/10/pppppppppp/10 w KQkq - 0 1" // pawn move
                //"4r5/6k3/10/2q7/10/3KN2R2/3N6/10/10/10 w KQkq - 0 1" // king move
                //"10/10/10/kP8/10/10/10/10/10/9K w" // draw
        );

        Game game = new Game(board);
        game.gameLoop();
    }
}