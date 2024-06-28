package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class QueenMove {
    QueenMove() {

    }

    public Collection<ChessMove> CheckMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = (Set<ChessMove>) new RookMove().CheckMove(board, beginPos);
        moves.addAll(new BishopMove().CheckMove(board, beginPos));
        return moves;
    }
}
