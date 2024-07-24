package chess;

import java.util.Collection;
import java.util.Set;

public class QueenMove {
    QueenMove() {

    }

    public Collection<ChessMove> checkMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = (Set<ChessMove>) new RookMove().checkMove(board, beginPos);
        moves.addAll(new BishopMove().checkMove(board, beginPos));
        return moves;
    }
}
