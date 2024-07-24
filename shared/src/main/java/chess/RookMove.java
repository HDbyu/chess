package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RookMove {
    RookMove() {

    }
    public Collection<ChessMove> checkMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        IterateMoves iterate = new IterateMoves();
        iterate.movesIterate(moves, beginPos, board, 1,0);
        iterate.movesIterate(moves, beginPos, board, -1,0);
        iterate.movesIterate(moves, beginPos, board, 0,1);
        iterate.movesIterate(moves, beginPos, board, 0,-1);
        return moves;
    }
}
