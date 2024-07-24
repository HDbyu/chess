package chess;

import java.util.*;

public class BishopMove{
    public BishopMove() {

    }
    public Collection<ChessMove> checkMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        IterateMoves iterate = new IterateMoves();
        iterate.movesIterate(moves, beginPos, board, -1,-1);
        iterate.movesIterate(moves, beginPos, board, 1,-1);
        iterate.movesIterate(moves, beginPos, board, -1,1);
        iterate.movesIterate(moves, beginPos, board, 1,1);
        return moves;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
