package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KnightMove {

    KnightMove() {

    }

    public Collection<ChessMove> checkMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        IterateMoves iterate = new IterateMoves();
        iterate.moveIterate(moves, beginPos, board, 2,-1);
        iterate.moveIterate(moves, beginPos, board, 2,1);
        iterate.moveIterate(moves, beginPos, board, -2,1);
        iterate.moveIterate(moves, beginPos, board, -2,-1);
        iterate.moveIterate(moves, beginPos, board, 1,2);
        iterate.moveIterate(moves, beginPos, board, -1,2);
        iterate.moveIterate(moves, beginPos, board, 1,-2);
        iterate.moveIterate(moves, beginPos, board, -1,-2);
        return moves;
    }
}
